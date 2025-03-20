package project.grandmasfood.infrastructure.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.grandmasfood.application.dto.order.OrderRequestDto;
import project.grandmasfood.application.dto.order.OrderResponseDto;
import project.grandmasfood.application.dto.order.OrderUpdateRequestDto;
import project.grandmasfood.application.handler.interfaces.IOrderHandler;
import project.grandmasfood.domain.exceptions.OrderNotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRestControllerTest {

    @Mock
    private IOrderHandler orderHandler;

    @InjectMocks
    private OrderRestController orderRestController;

    private OrderRequestDto orderRequestDto;
    private OrderResponseDto orderResponseDto;
    private OrderUpdateRequestDto orderUpdateRequestDto;
    private UUID orderUuid;
    private LocalDateTime deliveredDate;

    @BeforeEach
    void setUp() {
        orderUuid = UUID.randomUUID();
        UUID productUuid = UUID.randomUUID();
        deliveredDate = LocalDateTime.now();

        // Setup OrderRequestDto
        orderRequestDto = new OrderRequestDto();
        orderRequestDto.setProductUuid(productUuid);
        orderRequestDto.setClientDocument("CC-123456");
        orderRequestDto.setQuantity(2);
        orderRequestDto.setExtraInformation("Test order");

        // Setup OrderResponseDto
        orderResponseDto = new OrderResponseDto();
        orderResponseDto.setUuid(orderUuid);
        orderResponseDto.setProductUuid(productUuid);
        orderResponseDto.setClientDocument("CC-123456");
        orderResponseDto.setQuantity(2);
        orderResponseDto.setExtraInformation("Test order");
        orderResponseDto.setSubTotal(20000.0f);
        orderResponseDto.setTax(0.19f);
        orderResponseDto.setGrandTotal(23800.0f);
        orderResponseDto.setDelivered(false);
        orderResponseDto.setDeliveredDate(null);
        orderResponseDto.setCreationDateTime(LocalDateTime.now());

        // Setup OrderUpdateRequestDto
        orderUpdateRequestDto = new OrderUpdateRequestDto();
        orderUpdateRequestDto.setUuid(orderUuid);
        orderUpdateRequestDto.setDelivered(true);
        orderUpdateRequestDto.setDeliveredDate(deliveredDate);
    }

    @Test
    void createOrder_Success() {
        when(orderHandler.createOrder(any(OrderRequestDto.class)))
                .thenReturn(orderResponseDto);

        ResponseEntity<OrderResponseDto> response =
                orderRestController.createOrder(orderRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderResponseDto, response.getBody());
        verify(orderHandler).createOrder(orderRequestDto);
    }

    @Test
    void createOrder_BadRequest() {
        when(orderHandler.createOrder(any(OrderRequestDto.class)))
                .thenThrow(IllegalArgumentException.class);

        ResponseEntity<OrderResponseDto> response =
                orderRestController.createOrder(orderRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderHandler).createOrder(orderRequestDto);
    }

    @Test
    void createOrder_ProductNotFound() {
        when(orderHandler.createOrder(any(OrderRequestDto.class)))
                .thenThrow(new IllegalArgumentException("Product not found"));

        ResponseEntity<OrderResponseDto> response =
                orderRestController.createOrder(orderRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderHandler).createOrder(orderRequestDto);
    }

    @Test
    void createOrder_ClientNotFound() {
        when(orderHandler.createOrder(any(OrderRequestDto.class)))
                .thenThrow(new IllegalArgumentException("Client not found"));

        ResponseEntity<OrderResponseDto> response =
                orderRestController.createOrder(orderRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderHandler).createOrder(orderRequestDto);
    }

    @Test
    void updateOrder_Success() {
        OrderResponseDto updatedOrder = new OrderResponseDto();
        updatedOrder.setUuid(orderUuid);
        updatedOrder.setDelivered(true);
        updatedOrder.setDeliveredDate(deliveredDate);

        when(orderHandler.updateOrder(any(OrderUpdateRequestDto.class)))
                .thenReturn(updatedOrder);

        ResponseEntity<OrderResponseDto> response =
                orderRestController.updateOrder(orderUuid, deliveredDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isDelivered());
        assertEquals(deliveredDate, response.getBody().getDeliveredDate());
        verify(orderHandler).updateOrder(any(OrderUpdateRequestDto.class));
    }

    @Test
    void updateOrder_NotFound() {
        when(orderHandler.updateOrder(any(OrderUpdateRequestDto.class)))
                .thenThrow(OrderNotFoundException.class);

        assertThrows(OrderNotFoundException.class, () ->
                orderRestController.updateOrder(orderUuid, deliveredDate));

        verify(orderHandler).updateOrder(any(OrderUpdateRequestDto.class));
    }

    @Test
    void updateOrder_BadRequest() {
        when(orderHandler.updateOrder(any(OrderUpdateRequestDto.class)))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () ->
                orderRestController.updateOrder(orderUuid, deliveredDate));

        verify(orderHandler).updateOrder(any(OrderUpdateRequestDto.class));
    }
}