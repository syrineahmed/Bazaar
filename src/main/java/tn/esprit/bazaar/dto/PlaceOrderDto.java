package tn.esprit.bazaar.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaceOrderDto {
    private Long userId;
    private String address;
    private String orderDescription;
}
