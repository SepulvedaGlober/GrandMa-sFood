package project.grandmasfood.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import project.grandmasfood.domain.models.Category;




@Entity
@Table(name = "products")
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;
    @Column(name = "fantasy_name", nullable = false)
    private String fantasyName;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "available", nullable = false)
    private boolean available;
}
