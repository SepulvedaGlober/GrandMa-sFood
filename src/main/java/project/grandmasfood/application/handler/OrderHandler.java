package project.grandmasfood.application.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.grandmasfood.application.dto.order.OrderRequestDto;
import project.grandmasfood.application.dto.order.OrderResponseDto;
import project.grandmasfood.application.dto.order.OrderUpdateRequestDto;
import project.grandmasfood.application.handler.interfaces.IOrderHandler;
import project.grandmasfood.application.mapper.order.IOrderRequestMapper;
import project.grandmasfood.application.mapper.order.IOrderResponseMapper;
import project.grandmasfood.domain.api.IOrderServicePort;
import project.grandmasfood.domain.models.Order;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {


    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;


    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = orderRequestMapper.mapToOrderRequestDto(orderRequestDto);
        Order createdOrder = orderServicePort.createOrder(order);
        return orderResponseMapper.mapToOrderResponseDto(createdOrder);
    }

    @Override
    public OrderResponseDto updateOrder(OrderUpdateRequestDto orderUpdateRequestDto) {
        Order order = orderRequestMapper.mapToOrderUpdateRequestDto(orderUpdateRequestDto);
        Order updateOrder = orderServicePort.updateOrder(order);
        return orderResponseMapper.mapToOrderResponseDto(updateOrder);

    }
}
