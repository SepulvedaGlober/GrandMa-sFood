package project.grandmasfood.domain.spi;

import project.grandmasfood.domain.models.Order;

import java.util.Optional;
import java.util.UUID;

public interface IOrderPersistencePort {
    Order createOrder(Order order);
    Order updateOrder(Order order);
    Optional<Order> getOrderById(Long idOrder);
    Optional<Order> getOrderByUuid(UUID uuid);
}
