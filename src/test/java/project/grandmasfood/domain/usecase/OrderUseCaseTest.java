package project.grandmasfood.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.grandmasfood.domain.exceptions.*;
import project.grandmasfood.domain.models.Client;
import project.grandmasfood.domain.models.Order;
import project.grandmasfood.domain.models.Product;
import project.grandmasfood.domain.spi.IClientPersistencePort;
import project.grandmasfood.domain.spi.IOrderPersistencePort;
import project.grandmasfood.domain.spi.IProductPersistencePort;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private IClientPersistencePort clientPersistencePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private Order testOrder;
    private Product testProduct;
    private Client testClient;
    private UUID productUuid;

    @BeforeEach
    void setUp() {
        productUuid = UUID.randomUUID();

        testProduct = new Product();
        testProduct.setUuid(productUuid);
        testProduct.setPrice(10000.0f);

        testClient = new Client();
        testClient.setDocument("CC-123456");

        testOrder = new Order();
        testOrder.setUuid(UUID.randomUUID());
        testOrder.setProductUuid(productUuid);
        testOrder.setClientDocument("CC-123456");
        testOrder.setQuantity(2);
        testOrder.setExtraInformation("Test order");
    }

    @Test
    void createOrder_Success() {
        when(productPersistencePort.getProductByUuid(productUuid))
                .thenReturn(Optional.of(testProduct));
        when(clientPersistencePort.getClientByDocument(testClient.getDocument()))
                .thenReturn(Optional.of(testClient));
        when(orderPersistencePort.createOrder(any(Order.class)))
                .thenReturn(testOrder);

        Order result = orderUseCase.createOrder(testOrder);

        assertNotNull(result);
        assertNotNull(result.getUuid());
        assertEquals(20000.0f, result.getSubTotal());
        assertEquals(0.19f, result.getTax());
        assertEquals(23800.0f, result.getGrandTotal());
        assertFalse(result.isDelivered());
        assertNull(result.getDeliveredDate());
        verify(orderPersistencePort).createOrder(any(Order.class));
    }

    @Test
    void createOrder_ThrowsException_WhenProductNotFound() {
        when(productPersistencePort.getProductByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> orderUseCase.createOrder(testOrder));
        verify(orderPersistencePort, never()).createOrder(any());
    }

    @Test
    void createOrder_ThrowsException_WhenClientNotFound() {
        when(productPersistencePort.getProductByUuid(any(UUID.class)))
                .thenReturn(Optional.of(testProduct));
        when(clientPersistencePort.getClientByDocument(any()))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> orderUseCase.createOrder(testOrder));
        verify(orderPersistencePort, never()).createOrder(any());
    }

    @Test
    void createOrder_ThrowsException_WhenExtraInformationTooLong() {
        testOrder.setExtraInformation("a".repeat(512));
        when(productPersistencePort.getProductByUuid(any(UUID.class)))
                .thenReturn(Optional.of(testProduct));
        when(clientPersistencePort.getClientByDocument(any()))
                .thenReturn(Optional.of(testClient));

        assertThrows(InformationLengthException.class,
                () -> orderUseCase.createOrder(testOrder));
        verify(orderPersistencePort, never()).createOrder(any());
    }

    @Test
    void updateOrder_Success() {
        UUID orderUuid = UUID.randomUUID();
        Order existingOrder = new Order();
        existingOrder.setUuid(orderUuid);
        existingOrder.setDelivered(false);
        existingOrder.setDeliveredDate(null);

        Order updateInfo = new Order();
        updateInfo.setUuid(orderUuid);
        updateInfo.setDelivered(true);
        updateInfo.setDeliveredDate(LocalDateTime.now());

        when(orderPersistencePort.getOrderByUuid(orderUuid))
                .thenReturn(Optional.of(existingOrder));
        when(orderPersistencePort.updateOrder(any(Order.class)))
                .thenReturn(updateInfo);

        Order result = orderUseCase.updateOrder(updateInfo);

        assertNotNull(result);
        assertTrue(result.isDelivered());
        assertNotNull(result.getDeliveredDate());
        verify(orderPersistencePort).updateOrder(any(Order.class));
    }

    @Test
    void updateOrder_ThrowsException_WhenOrderNotFound() {
        Order updateInfo = new Order();
        updateInfo.setUuid(UUID.randomUUID());

        when(orderPersistencePort.getOrderByUuid(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderUseCase.updateOrder(updateInfo));
        verify(orderPersistencePort, never()).updateOrder(any());
    }
}