package project.grandmasfood.application.dto.order;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderRequestDto {
    private UUID productUuid;
    private String clientDocument;
    private int quantity;
    private String extraInformation;
}
