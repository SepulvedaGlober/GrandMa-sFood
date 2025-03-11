package project.grandmasfood.domain.models;

import lombok.Data;

@Data
public class Client {
    private Long idClient;
    private String document;
    private String fullName;
    private String email;
    private String phone;
    private String deliveryAddress;
}
