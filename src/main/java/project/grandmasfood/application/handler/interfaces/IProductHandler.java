package project.grandmasfood.application.handler.interfaces;

import project.grandmasfood.application.dto.product.ProductRequestDto;
import project.grandmasfood.application.dto.product.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface IProductHandler {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);
    void updateProduct(Long idProduct, ProductRequestDto productRequestDto);
    void deleteProduct(Long idProduct);
    Optional<ProductResponseDto> getProductById(Long idProduct);
    List<ProductResponseDto> getAllProducts();
    List<ProductResponseDto> searchProductsByFantasyName(String keyword);
}
