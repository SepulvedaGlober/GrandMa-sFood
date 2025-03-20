package project.grandmasfood.domain.usecase;

import lombok.RequiredArgsConstructor;
import project.grandmasfood.domain.api.IOrderServicePort;
import project.grandmasfood.domain.exceptions.ClientNotFoundException;
import project.grandmasfood.domain.exceptions.InformationLengthException;
import project.grandmasfood.domain.exceptions.OrderNotFoundException;
import project.grandmasfood.domain.exceptions.ProductNotFoundException;
import project.grandmasfood.domain.models.Client;
import project.grandmasfood.domain.models.Order;
import project.grandmasfood.domain.models.Product;
import project.grandmasfood.domain.spi.IClientPersistencePort;
import project.grandmasfood.domain.spi.IOrderPersistencePort;
import project.grandmasfood.domain.spi.IProductPersistencePort;

import java.time.LocalDateTime;
import java.util.UUID;

import static project.grandmasfood.utils.ErrorMessages.ORDER_NOT_FOUND;


@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IProductPersistencePort productPersistencePort;
    private final IClientPersistencePort clientPersistencePort;


    @Override
    public Order createOrder(Order order) {
        Product product = productPersistencePort.getProductByUuid(order.getProductUuid())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Client client = clientPersistencePort.getClientByDocument(order.getClientDocument())
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        if (order.getExtraInformation().length() > 511) {
            throw new InformationLengthException("The description is too long");
        }
        order.setUuid(UUID.randomUUID());
        order.setClientDocument(client.getDocument());
        order.setProductUuid(product.getUuid());
        order.setCreationDateTime(LocalDateTime.now());
        order.setSubTotal(product.getPrice() * order.getQuantity());
        order.setTax(0.19f);
        order.setGrandTotal(order.getSubTotal() + (order.getSubTotal() * order.getTax()));
        order.setDelivered(false);
        order.setDeliveredDate(null);
        return orderPersistencePort.createOrder(order);
    }

    @Override
    public Order updateOrder(Order orderUpdateInfo) {
        Order existingOrder = orderPersistencePort.getOrderByUuid(orderUpdateInfo.getUuid())
                .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND));
        existingOrder.setDelivered(orderUpdateInfo.isDelivered());
        existingOrder.setDeliveredDate(orderUpdateInfo.getDeliveredDate());
        return orderPersistencePort.updateOrder(existingOrder);
    }
}
