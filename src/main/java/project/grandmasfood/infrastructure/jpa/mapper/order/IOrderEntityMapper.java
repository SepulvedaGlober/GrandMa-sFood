package project.grandmasfood.infrastructure.jpa.mapper.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.grandmasfood.domain.models.Order;
import project.grandmasfood.infrastructure.jpa.entities.OrderEntity;

@Mapper(componentModel = "spring")
public interface IOrderEntityMapper {

    /**
     * Transform Order to OrderEntity
     *
     * @param order Order
     * @return OrderEntity
     */
    OrderEntity toOrderEntity(Order order);

    /**
     * Transform OrderEntity to Order
     *
     * @param entity OrderEntity
     * @return Order
     */
    @Mapping(source = "client.document", target = "clientDocument")
    @Mapping(source = "product.uuid", target = "productUuid")
    Order toOrder(OrderEntity entity);


}