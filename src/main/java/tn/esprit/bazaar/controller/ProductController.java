package tn.esprit.bazaar.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.bazaar.dto.ProductDto;
import tn.esprit.bazaar.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    // In ProductController.java
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestParam("product") String productStr,
            @RequestParam("img") MultipartFile img) {
        try {
            ProductDto productDto = objectMapper.readValue(productStr, ProductDto.class);
            productDto.setImg(img);
            ProductDto createdProduct = productService.addProduct(productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product: " + e.getMessage());
        }
    }
    @GetMapping("/all")

    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = productService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }
}
