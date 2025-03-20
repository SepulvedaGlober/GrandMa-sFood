package project.grandmasfood.application.handler.interfaces;

import project.grandmasfood.application.dto.product.ProductRequestDto;
import project.grandmasfood.application.dto.product.ProductResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductHandler {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    void updateProduct(UUID uuid, ProductRequestDto productRequestDto);
    void deleteProduct(UUID uuid);
    Optional<ProductResponseDto> getProductById(Long idProduct);
    Optional<ProductResponseDto> getProductByUuid(UUID uuid);
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> searchProductsByFantasyName(String keyword);
}
