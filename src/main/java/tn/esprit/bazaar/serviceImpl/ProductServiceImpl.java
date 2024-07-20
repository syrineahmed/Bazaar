package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.ProductDto;
import tn.esprit.bazaar.entities.Category;
import tn.esprit.bazaar.entities.Product;
import tn.esprit.bazaar.repository.CategoryRepository;
import tn.esprit.bazaar.repository.PorductRepository;
import tn.esprit.bazaar.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final PorductRepository porductRepository;

    private final CategoryRepository categoryRepository;


    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        if (productDto.getImg() != null && !productDto.getImg().isEmpty()) {
            product.setImage(productDto.getImg().getBytes());
        }
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.setCategory(category);
        Product savedProduct = porductRepository.save(product);
        return savedProduct.getDto();
    }

    public List<ProductDto> getAllProducts(){
        List<Product> products = porductRepository.findAll();
        return products.stream().map(Product::getDto).toList();
    }

    public List<ProductDto> getAllProductByName(String name){
        List<Product> products = porductRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).toList();
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> product = porductRepository.findById(id);
        if (product.isPresent()) {
            porductRepository.deleteById(id);
            return true;
        }
        return false;

    }
}
