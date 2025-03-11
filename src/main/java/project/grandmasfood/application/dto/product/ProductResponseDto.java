package project.grandmasfood.application.dto.product;

import lombok.Getter;
import lombok.Setter;
import project.grandmasfood.domain.models.Category;

@Getter
@Setter
public class ProductResponseDto {
    private String fantasyName;
    private Category category;
    private String description;
    private float price;
    private boolean available;
}
