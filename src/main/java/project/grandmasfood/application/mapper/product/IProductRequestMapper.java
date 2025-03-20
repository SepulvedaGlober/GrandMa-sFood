package project.grandmasfood.application.mapper.product;

import org.mapstruct.Mapper;
import project.grandmasfood.application.dto.product.ProductRequestDto;
import project.grandmasfood.domain.models.Product;

@Mapper(componentModel = "spring")
public interface IProductRequestMapper {

    /**
     * Transforms a ProductRequestDto into a Product
     *
     * @param productRequestDto ProductRequestDto
     * @return Product
     */
    Product toProduct(ProductRequestDto productRequestDto);

}
