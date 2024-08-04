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

    @GetMapping("/searchbyname/{name}")

    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name) {
        List<ProductDto> productDtos = productService.getAllProductByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/searchByCategoryName")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryName(@RequestParam String categoryName) {
        List<ProductDto> productDtos = productService.getAllProductsByCategoryName(categoryName);
        return ResponseEntity.ok(productDtos);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                return ResponseEntity.ok().body("Product deleted successfully");
            } else {
                return ResponseEntity.badRequest().body("Product could not be deleted");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting product: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam("product") String productStr,
            @RequestParam(value = "img", required = false) MultipartFile img) {
        try {
            ProductDto productDto = objectMapper.readValue(productStr, ProductDto.class);

            if (img != null) {
                productDto.setImg(img);
            }

            ProductDto updatedProduct = productService.updateProduct(id, productDto);

            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating product: " + e.getMessage());
        }
    }

    @GetMapping("/getbyid/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        if (productDto != null) {
            return ResponseEntity.ok(productDto);
        } else {
        return ResponseEntity.notFound().build();
        }
    }
}
