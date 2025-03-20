package project.grandmasfood.domain.api;

import project.grandmasfood.domain.models.Order;

public interface IOrderServicePort {
    Order createOrder(Order order);
    Order updateOrder(Order order);
}
