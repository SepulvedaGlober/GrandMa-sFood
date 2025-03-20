package project.grandmasfood.infrastructure.jpa.mapper.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.grandmasfood.domain.models.Product;
import project.grandmasfood.infrastructure.jpa.entities.ProductEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductEntityMapper {

    /**
     * Maps a ProductEntity to a Product
     *
     * @param product the ProductEntity to map
     * @return the Product mapped
     */
    @Mapping(source = "uuid", target = "uuid")
    ProductEntity toProductEntity(Product product);


    /**
     * Maps a Product to a ProductEntity
     *
     * @param productEntity the Product to map
     * @return the ProductEntity mapped
     */

    @Mapping(source = "uuid", target = "uuid")
    Product toProduct(ProductEntity productEntity);


    /**
     * Maps a list of ProductEntity to a list of Product
     *
     * @param productEntities the list of ProductEntity to map
     * @return the list of Product mapped
     */
    List<Product> toProductList(List<ProductEntity> productEntities);
}
