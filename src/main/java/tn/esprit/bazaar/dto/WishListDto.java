package tn.esprit.bazaar.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishListDto {

    private Long userId;

    private Long productId;

    private Long id;

    private String productName;

    private String productDescription;

    private byte[] returnedImg;

    private Long price;
}
