package project.grandmasfood.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.grandmasfood.infrastructure.jpa.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByFantasyName(String fantasyName);
    Optional<ProductEntity> findByIdProduct(Long idProduct);

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.fantasyName) LIKE LOWER(CONCAT('%', :keyword, '%'))ORDER BY p.fantasyName ASC ")
    List<ProductEntity> findByFantasyNameContainingIgnoreCase(@Param("keyword") String keyword);
}
