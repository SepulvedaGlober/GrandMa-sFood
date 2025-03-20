package project.grandmasfood.domain.models;

import lombok.Data;

import java.util.UUID;

@Data
public class Product {
    private Long idProduct;
    private UUID uuid;
    private String fantasyName;
    private Category category;
    private String description;
    private float price;
    private boolean available;

    public Product(String fantasyName, Category category, String description, float price, boolean available) {
        this.uuid = UUID.randomUUID();
        this.fantasyName = fantasyName;
        this.category = category;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public Product() {}
}
