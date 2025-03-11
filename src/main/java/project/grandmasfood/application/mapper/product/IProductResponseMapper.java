package project.grandmasfood.application.mapper.product;

import org.mapstruct.Mapper;
import project.grandmasfood.application.dto.product.ProductResponseDto;
import project.grandmasfood.domain.models.Product;

@Mapper(componentModel = "spring")
public interface IProductResponseMapper {

    ProductResponseDto toProductResponse(Product product);
}
