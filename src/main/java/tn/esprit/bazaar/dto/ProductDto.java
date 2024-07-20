package tn.esprit.bazaar.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private int quantity;
    private byte[] byteImg;
    private Long categoryId;
    private MultipartFile img;
}



