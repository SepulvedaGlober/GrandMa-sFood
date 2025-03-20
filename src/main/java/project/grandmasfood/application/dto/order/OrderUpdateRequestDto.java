package project.grandmasfood.application.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class OrderUpdateRequestDto {
    private UUID uuid;
    private boolean delivered;
    private LocalDateTime deliveredDate;
}
