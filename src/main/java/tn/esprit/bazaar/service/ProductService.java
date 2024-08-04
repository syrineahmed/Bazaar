package tn.esprit.bazaar.service;

import tn.esprit.bazaar.dto.ProductDetailDto;
import tn.esprit.bazaar.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto) throws IOException ;
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductByName(String name);
    List<ProductDto> getAllProductsByCategoryName(String categoryName) ;
     boolean deleteProduct(Long id);
    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException ;
    ProductDto getProductById(Long productId) ;
    ProductDetailDto getProductDetails(Long productId) ;



    }
