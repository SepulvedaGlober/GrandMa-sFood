package project.grandmasfood.domain.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private Long idOrder;
    private LocalDateTime creationDateTime;
    private String clientDocument;
    private Long productId;
    private int quantity;
    private String extraInformation;
    private float subTotal;
    private float tax;
    private float grandTotal;
    private boolean delivered;
    private LocalDateTime deliveredDate;
}
