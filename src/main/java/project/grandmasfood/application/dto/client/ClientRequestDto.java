package project.grandmasfood.application.dto.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDto {
    private String document;
    private String fullName;
    private String email;
    private String phone;
    private String deliveryAddress;
}
