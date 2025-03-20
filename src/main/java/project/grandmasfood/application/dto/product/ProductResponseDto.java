package project.grandmasfood.application.dto.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import project.grandmasfood.domain.models.Category;

import java.util.UUID;

@Getter
@Setter
@Data
public class ProductResponseDto {
    private UUID uuid;
    private String fantasyName;
    private Category category;
    private String description;
    private float price;
    private boolean available;
}
