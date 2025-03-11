package project.grandmasfood.application.dto.product;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import project.grandmasfood.domain.models.Category;

@Getter
@Setter
public class ProductRequestDto {
    @NotNull
    private String fantasyName;
    @NotNull
    private Category category;
    @NotNull
    private String description;
    @NotNull
    private float price;
    @NotNull
    private boolean available;
}
