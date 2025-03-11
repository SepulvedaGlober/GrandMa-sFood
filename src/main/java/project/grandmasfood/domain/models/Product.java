package project.grandmasfood.domain.models;

import lombok.Data;

@Data
public class Product {
    private Long idProduct;
    private String fantasyName;
    private Category category;
    private String description;
    private float price;
    private boolean available;
}
