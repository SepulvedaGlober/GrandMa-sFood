package project.grandmasfood.application.mapper.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.grandmasfood.application.dto.product.ProductResponseDto;
import project.grandmasfood.domain.models.Product;

@Mapper(componentModel = "spring")
public interface IProductResponseMapper {

    /**
     * Transforms a product into a product response dto
     *
     * @param product the product to transform
     * @return the product response dto
     */

    @Mapping(source = "uuid", target = "uuid")
    ProductResponseDto toProductResponse(Product product);
}
