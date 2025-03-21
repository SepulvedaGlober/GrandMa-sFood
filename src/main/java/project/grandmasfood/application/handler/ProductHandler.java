package project.grandmasfood.application.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.grandmasfood.application.dto.product.ProductRequestDto;
import project.grandmasfood.application.dto.product.ProductResponseDto;
import project.grandmasfood.application.handler.interfaces.IProductHandler;
import project.grandmasfood.application.mapper.product.IProductRequestMapper;
import project.grandmasfood.application.mapper.product.IProductResponseMapper;
import project.grandmasfood.domain.api.IProductServicePort;
import project.grandmasfood.domain.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;


    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productRequestMapper.toProduct(productRequestDto);
        productServicePort.createProduct(product);
        return productResponseMapper.toProductResponse(product);
    }

    @Override
    public void updateProduct(UUID uuid, ProductRequestDto productRequestDto) {
        Product product = productRequestMapper.toProduct(productRequestDto);
        product.setUuid(uuid);
        productServicePort.updateProduct(product);
    }

    @Override
    public void deleteProduct(UUID uuid) {
        productServicePort.deleteProduct(uuid);
    }

    @Override
    public Optional<ProductResponseDto> getProductById(Long idProduct) {
        return productServicePort.getProductById(idProduct)
                .map(productResponseMapper::toProductResponse);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productServicePort.getAllProducts().stream()
                .map(productResponseMapper::toProductResponse)
                .toList();
    }

    @Override
    public Optional<ProductResponseDto> getProductByUuid(UUID uuid) {
        return productServicePort.getProductByUuid(uuid)
                .map(productResponseMapper::toProductResponse);
    }

    @Override
    public List<ProductResponseDto> searchProductsByFantasyName(String keyword) {
        List<Product> products = productServicePort.searchProductsByFantasyName(keyword);
        return products.stream()
                .map(productResponseMapper::toProductResponse)
                .toList();
    }
}
