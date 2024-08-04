package tn.esprit.bazaar.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bazaar.entities.Product;
import tn.esprit.bazaar.entities.User;

@Data
public class ReviewDto {
    private Long id;
    private Long rating;
    private String description;
    private MultipartFile img;
    private byte[] returnedImg;
    private Long userId;
    private Long productId;
    private String userName;
}
