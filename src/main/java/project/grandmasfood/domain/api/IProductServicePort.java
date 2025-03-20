package project.grandmasfood.domain.api;

import project.grandmasfood.domain.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductServicePort {
    void createProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(UUID uuid);
    List<Product> getAllProducts();
    Optional<Product> getProductByUuid(UUID uuid);
    Optional<Product> getProductById(Long idProduct);
    List<Product> searchProductsByFantasyName(String keyword);
}
