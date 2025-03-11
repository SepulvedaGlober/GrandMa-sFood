package project.grandmasfood.infrastructure.jpa.mapper.product;

import org.mapstruct.Mapper;
import project.grandmasfood.domain.models.Product;
import project.grandmasfood.infrastructure.jpa.entities.ProductEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductEntityMapper {

    ProductEntity toProductEntity(Product product);


    Product toProduct(ProductEntity productEntity);


    List<Product> toProductList(List<ProductEntity> productEntities);
}
