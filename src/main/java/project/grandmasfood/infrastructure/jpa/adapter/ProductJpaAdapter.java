package project.grandmasfood.infrastructure.jpa.adapter;

import lombok.RequiredArgsConstructor;
import project.grandmasfood.domain.exceptions.DuplicateFantasyNameException;
import project.grandmasfood.domain.exceptions.NoDataFoundException;
import project.grandmasfood.domain.exceptions.ProductNotFoundException;
import project.grandmasfood.domain.models.Product;
import project.grandmasfood.domain.spi.IProductPersistencePort;
import project.grandmasfood.infrastructure.jpa.entities.ProductEntity;
import project.grandmasfood.infrastructure.jpa.mapper.product.IProductEntityMapper;
import project.grandmasfood.infrastructure.jpa.repository.IProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static project.grandmasfood.utils.ErrorMessages.*;

@RequiredArgsConstructor
public class ProductJpaAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;
    private final IProductEntityMapper productEntityMapper;


    @Override
    public void createProduct(Product product) {
        if(productRepository.findByFantasyName(product.getFantasyName()).isPresent()){
            throw new DuplicateFantasyNameException(DUPLICATE_FANTASY_NAME_EXCEPTION);
        }
        ProductEntity productEntity = productEntityMapper.toProductEntity(product);
        productRepository.save(productEntity);

    }

    @Override
    public void updateProduct(Product product) {
        ProductEntity existingProduct = productRepository.findByUuid(product.getUuid())
                .orElseThrow(() -> new ProductNotFoundException(NO_ID_PRODUCT_FOUND_EXCEPTION));

        existingProduct.setFantasyName(product.getFantasyName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setAvailable(product.isAvailable());

        productRepository.save(existingProduct);

    }

    @Override
    public void deleteProduct(Product product) {
        ProductEntity existingProduct = productRepository.findByUuid(product.getUuid())
                .orElseThrow(() -> new ProductNotFoundException(NO_ID_PRODUCT_FOUND_EXCEPTION));
        productRepository.delete(existingProduct);

    }

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        if(productEntities.isEmpty()){
            throw new NoDataFoundException(NO_DATA_FOUND_EXCEPTION);
        }
        return productEntityMapper.toProductList(productEntities);
    }

    @Override
    public Optional<Product> getProductById(Long idProduct) {
        return productRepository.findById(idProduct)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public Optional<Product> getProductByFantasyName(String fantasyName) {
        return productRepository.findByFantasyName(fantasyName)
                .map(productEntityMapper::toProduct);
    }

    @Override
    public Optional<Product> getProductByUuid(UUID uuid) {
        Optional<Product> product = productRepository.findByUuid(uuid)
                .map(productEntityMapper::toProduct);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(NO_ID_PRODUCT_FOUND_EXCEPTION);
        }
        return product;
    }

    @Override
    public List<Product> searchProductsByFantasyName(String keyword) {
        List<ProductEntity> productEntities = productRepository.findByFantasyNameContainingIgnoreCase(keyword);
        if(productEntities.isEmpty()){
            throw new NoDataFoundException(NO_DATA_FOUND_EXCEPTION);
        }
        return productEntities.stream()
                .map(productEntityMapper::toProduct)
                .toList();
    }
}
