package project.grandmasfood.application.mapper.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.grandmasfood.application.dto.order.OrderRequestDto;
import project.grandmasfood.application.dto.order.OrderUpdateRequestDto;
import project.grandmasfood.domain.models.Order;

@Mapper(componentModel = "spring")
public interface IOrderRequestMapper {
    /**
     * Transform OrderRequestDto to Order
     *
     * @param orderRequestDto OrderRequestDto
     * @return Order
     */
    @Mapping(source = "productUuid", target = "productUuid")
    Order mapToOrderRequestDto(OrderRequestDto orderRequestDto);


    /**
     * Transform OrderUpdateRequestDto to Order
     *
     * @param orderUpdateRequestDto OrderUpdateRequestDto
     * @return Order
     */
    Order mapToOrderUpdateRequestDto(OrderUpdateRequestDto orderUpdateRequestDto);
}
