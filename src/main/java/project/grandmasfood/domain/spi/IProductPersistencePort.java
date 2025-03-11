package project.grandmasfood.domain.spi;

import project.grandmasfood.domain.models.Product;

import java.util.List;
import java.util.Optional;

public interface IProductPersistencePort {
    void createProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long idProduct);
    Optional<Product> getProductByFantasyName(String fantasyName);
    List<Product> searchProductsByFantasyName(String keyword);
}
