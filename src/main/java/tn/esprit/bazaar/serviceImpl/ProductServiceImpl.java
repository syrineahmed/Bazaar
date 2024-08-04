package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.ProductDetailDto;
import tn.esprit.bazaar.dto.ProductDto;
import tn.esprit.bazaar.entities.*;
import tn.esprit.bazaar.repository.FAQRepository;
import tn.esprit.bazaar.repository.PorductRepository;
import tn.esprit.bazaar.repository.ReviewRepository;
import tn.esprit.bazaar.service.ProductService;
import tn.esprit.bazaar.repository.CategoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final PorductRepository porductRepository;

    private final CategoryRepository categoryRepository;

    private final UserServiceImpl userService;

    private final FAQRepository faqRepository;

    private final ReviewRepository reviewRepository;

    //the current user can add a product
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
        User currentUser = userService.getCurrentUser();
        product.setUser(currentUser);
        Product savedProduct = porductRepository.save(product);
        return savedProduct.getDto();
    }

    // all users can see all products

    public List<ProductDto> getAllProducts(){
        List<Product> products = porductRepository.findAll();
        return products.stream().map(Product::getDto).toList();
    }

    //all users can search for a product by name
    public List<ProductDto> getAllProductByName(String name){
        List<Product> products = porductRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).toList();
    }

    //all users can search for a product by category
    public List<ProductDto> getAllProductsByCategoryName(String categoryName) {
        List<Product> products = porductRepository.findAllByCategoryName(categoryName);
        return products.stream().map(Product::getDto).toList();
    }

    //the admin can delete the product
    public boolean deleteProduct(Long id) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getRole().equals(Role.ADMIN)) {
            Optional<Product> product = porductRepository.findById(id);
            if (product.isPresent()) {
                porductRepository.deleteById(id);
                return true;
            }
        } else {
            throw new IllegalStateException("Only admins can delete products.");
        }
        return false;
    }

    //the admin and the owner of the product can update the product
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        User currentUser = userService.getCurrentUser();
        Product product = porductRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);
        boolean isOwner = product.getUser().getId().equals(currentUser.getId());

        if (isAdmin || isOwner) {
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setQuantity(productDto.getQuantity());

            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            product.setCategory(category);

            Product updatedProduct = porductRepository.save(product);
            return updatedProduct.getDto();
        } else {
            throw new IllegalStateException("Only the admin or the user who added the product can update it.");
        }
    }
    public ProductDto getProductById(Long productId) {
       Optional<Product> optionalProduct = porductRepository.findById(productId);
         if (optionalProduct.isPresent()) {
              return optionalProduct.get().getDto();
         } else {
             return null;
         }
         }

         //all users can see the product details reviw and faq

    public ProductDetailDto getProductDetails(Long productId) {
        Optional<Product> optionalProduct = porductRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            List<FAQ> faqList = faqRepository.findAllByProductId(productId);
            List<Review> reviewList = reviewRepository.findAllByProductId(productId);
            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setProductDto(optionalProduct.get().getDto());
            productDetailDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            productDetailDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));
            return productDetailDto;

        }
        return null;
    }

    //the seller can see his posted products
    public List<ProductDto> getProductsByCurrentUser() {
        User currentUser = userService.getCurrentUser();
        List<Product> products = porductRepository.findAllByUserId(currentUser.getId());
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
