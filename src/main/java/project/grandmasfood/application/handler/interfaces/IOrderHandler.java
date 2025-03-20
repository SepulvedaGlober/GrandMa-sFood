package project.grandmasfood.application.handler.interfaces;

import project.grandmasfood.application.dto.order.OrderRequestDto;
import project.grandmasfood.application.dto.order.OrderResponseDto;
import project.grandmasfood.application.dto.order.OrderUpdateRequestDto;



public interface IOrderHandler {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);
    OrderResponseDto updateOrder(OrderUpdateRequestDto orderUpdateRequestDto);
}
