package tn.bazaar.dto;

import lombok.Data;

@Data
public class AddProductInCartDto {
    private Long productId;
    private Long userId;
}
