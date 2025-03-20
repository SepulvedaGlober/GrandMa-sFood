package project.grandmasfood.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.grandmasfood.infrastructure.jpa.entities.OrderEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByIdOrder(Long idOrder);
    Optional<OrderEntity> findByUuid(UUID uuid);
}
