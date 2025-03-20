package project.grandmasfood.domain.usecase;


import lombok.RequiredArgsConstructor;
import project.grandmasfood.domain.api.IProductServicePort;
import project.grandmasfood.domain.exceptions.*;
import project.grandmasfood.domain.models.Product;
import project.grandmasfood.domain.spi.IProductPersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static project.grandmasfood.utils.Constants.MAX_PRODUCT_DESCRIPTION_LENGTH;
import static project.grandmasfood.utils.Constants.MAX_PRODUCT_NAME_LENGTH;
import static project.grandmasfood.utils.ErrorMessages.*;

@RequiredArgsConstructor
public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;

    @Override
    public void createProduct(Product product) {
        String upperCase = product.getFantasyName().toUpperCase();
        product.setFantasyName(upperCase);
        Optional<Product> existingProduct = productPersistencePort.getProductByFantasyName(product.getFantasyName());

        if (existingProduct.isPresent()) {
            throw new DuplicateFantasyNameException(DUPLICATE_FANTASY_NAME_EXCEPTION);
        }
        if(product.getFantasyName().length() > MAX_PRODUCT_NAME_LENGTH){
            throw new InvalidProductDataNameException(INVALID_PRODUCT_DATA_NAME_EXCEPTION);
        }
        if(product.getDescription().length() > MAX_PRODUCT_DESCRIPTION_LENGTH){
            throw new InvalidProductDataDescriptionException(INVALID_PRODUCT_DATA_DESCRIPTION_EXCEPTION);
        }
        if(product.getPrice() < 0 || !isValidPrice(product.getPrice())){
            throw new InvalidProductDataPriceException(INVALID_PRODUCT_DATA_PRICE_EXCEPTION);
        }
        product.setUuid(UUID.randomUUID());
        productPersistencePort.createProduct(product);

    }

    @Override
    public void updateProduct(Product product) {
        UUID uuid = product.getUuid();
        Product existingProduct = productPersistencePort.getProductByUuid(uuid)
                .orElseThrow(() -> new ProductNotFoundException(
                        NO_ID_PRODUCT_FOUND_EXCEPTION));

        if(!existingProduct.getFantasyName().equalsIgnoreCase(product.getFantasyName())){
            Optional<Product> sameName = productPersistencePort.getProductByFantasyName(product.getFantasyName());
            if(sameName.isPresent()){
                throw new DuplicateFantasyNameException(DUPLICATE_FANTASY_NAME_EXCEPTION);
            }
        }
        product.setUuid(existingProduct.getUuid());
        productPersistencePort.updateProduct(product);

    }

    @Override
    public void deleteProduct(UUID uuid) {
        Product product = productPersistencePort.getProductByUuid(uuid)
                .orElseThrow(() -> new ProductNotFoundException(
                        NO_ID_PRODUCT_FOUND_EXCEPTION
                ));
        productPersistencePort.deleteProduct(product);

    }

    @Override
    public List<Product> getAllProducts() {
        return productPersistencePort.getAllProducts();
    }

    @Override
    public Optional<Product> getProductById(Long idProduct) {
        if(productPersistencePort.getProductById(idProduct).isEmpty()){
            throw new ProductNotFoundException(NO_ID_PRODUCT_FOUND_EXCEPTION);
        }
        return productPersistencePort.getProductById(idProduct);
    }

    @Override
    public Optional<Product> getProductByUuid(UUID uuid) {
        if(productPersistencePort.getProductByUuid(uuid).isEmpty()){
            throw new ProductNotFoundException(NO_ID_PRODUCT_FOUND_EXCEPTION);
        }
        return productPersistencePort.getProductByUuid(uuid);
    }

    @Override
    public List<Product> searchProductsByFantasyName(String keyword) {
        if(keyword == null || keyword.isEmpty()){
            throw new InvalidProductDataException(NO_KEYWORD);
        }
        List<Product> products = productPersistencePort.searchProductsByFantasyName(keyword);
        if(products.isEmpty()){
            throw new NoDataFoundException(NO_DATA_FOUND_EXCEPTION);
        }
        return productPersistencePort.searchProductsByFantasyName(keyword);
    }

    private boolean isValidPrice(float price) {
        String priceString = String.valueOf(price);
        int decimalIndex = priceString.indexOf('.');
        if (decimalIndex == -1) {
            return true;
        }
        int decimalPlaces = priceString.length() - decimalIndex - 1;
        return decimalPlaces <= 2;
    }
}
