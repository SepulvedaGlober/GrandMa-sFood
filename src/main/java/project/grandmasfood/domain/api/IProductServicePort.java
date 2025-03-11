package project.grandmasfood.domain.api;

import project.grandmasfood.domain.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductServicePort {
    void createProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Long idProduct);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long idProduct);
    List<Product> searchProductsByFantasyName(String keyword);
}
