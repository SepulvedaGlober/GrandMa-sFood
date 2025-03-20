package project.grandmasfood.infrastructure.jpa.adapter;

import lombok.RequiredArgsConstructor;
import project.grandmasfood.domain.exceptions.ClientNotFoundException;
import project.grandmasfood.domain.exceptions.OrderNotFoundException;
import project.grandmasfood.domain.exceptions.ProductNotFoundException;
import project.grandmasfood.domain.models.Order;
import project.grandmasfood.domain.spi.IOrderPersistencePort;
import project.grandmasfood.infrastructure.jpa.entities.ClientEntity;
import project.grandmasfood.infrastructure.jpa.entities.OrderEntity;
import project.grandmasfood.infrastructure.jpa.entities.ProductEntity;
import project.grandmasfood.infrastructure.jpa.mapper.order.IOrderEntityMapper;
import project.grandmasfood.infrastructure.jpa.repository.IClientRepository;
import project.grandmasfood.infrastructure.jpa.repository.IOrderRepository;
import project.grandmasfood.infrastructure.jpa.repository.IProductRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IClientRepository clientRepository;
    private final IProductRepository productRepository;
    private final IOrderEntityMapper orderEntityMapper;


    @Override
    public Order createOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toOrderEntity(order);

        ClientEntity clientEntity = clientRepository.findByDocument(order.getClientDocument())
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        ProductEntity productEntity = productRepository.findByUuid(order.getProductUuid())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        orderEntity.setClient(clientEntity);
        orderEntity.setProduct(productEntity);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        return orderEntityMapper.toOrder(savedOrderEntity);
    }

    @Override
    public Order updateOrder(Order order) {
        OrderEntity existingEntity = orderRepository.findById(order.getIdOrder())
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        existingEntity.setDeliveredDate(order.getDeliveredDate());
        existingEntity.setDelivered(order.isDelivered());
        orderRepository.save(existingEntity);
        return orderEntityMapper.toOrder(existingEntity);
    }

    @Override
    public Optional<Order> getOrderById(Long idOrder) {
      return orderRepository.findByIdOrder(idOrder)
              .map(orderEntityMapper::toOrder);
    }

    @Override
    public Optional<Order> getOrderByUuid(UUID uuid) {
        return orderRepository.findByUuid(uuid)
                .map(orderEntityMapper::toOrder);
    }
}
