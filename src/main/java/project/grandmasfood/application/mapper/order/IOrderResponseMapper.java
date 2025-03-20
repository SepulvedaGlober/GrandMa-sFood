package project.grandmasfood.application.mapper.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.grandmasfood.application.dto.order.OrderResponseDto;
import project.grandmasfood.domain.models.Order;

@Mapper(componentModel = "spring")
public interface IOrderResponseMapper {
    /**
     * Transforms an Order object into an OrderResponseDto object
     *
     * @param order Order object
     * @return OrderResponseDto object
     */
    @Mapping(source = "productUuid", target = "productUuid")
    OrderResponseDto mapToOrderResponseDto(Order order);
}
