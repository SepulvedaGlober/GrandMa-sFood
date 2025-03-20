package project.grandmasfood.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import project.grandmasfood.domain.models.Category;

import java.util.UUID;


@Entity
@Table(name = "products")
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "fantasy_name", nullable = false)
    private String fantasyName;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    private String description;
    private float price;
    private boolean available;
}
