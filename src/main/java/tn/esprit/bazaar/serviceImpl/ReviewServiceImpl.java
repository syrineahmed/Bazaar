package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.OrderedProductResponseDto;
import tn.esprit.bazaar.dto.ProductDto;
import tn.esprit.bazaar.dto.ReviewDto;
import tn.esprit.bazaar.entities.*;
import tn.esprit.bazaar.repository.OrderRepository;
import tn.esprit.bazaar.repository.PorductRepository;
import tn.esprit.bazaar.repository.ReviewRepository;
import tn.esprit.bazaar.repository.UserRepository;
import tn.esprit.bazaar.service.ReviewService;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final OrderRepository orderRepository;
    private final PorductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public OrderedProductResponseDto getOrderedProductDetailsByOrderId(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        OrderedProductResponseDto orderedProductResponseDto = new OrderedProductResponseDto();
        if (optionalOrder.isPresent()) {
           orderedProductResponseDto.setOrderAmount(optionalOrder.get().getAmount());

           List<ProductDto> productDtoList = new ArrayList<>();
           for (CartItem cartItem : optionalOrder.get().getCartItems()) {
               ProductDto productDto = new ProductDto();
               productDto.setId(cartItem.getProduct().getId());
                productDto.setName(cartItem.getProduct().getName());
                productDto.setPrice(cartItem.getPrice());
               productDto.setQuantity(cartItem.getQuantity().intValue()); // Convert Long to int

                productDto.setByteImg(cartItem.getProduct().getImage());
               productDto.setCategoryId(cartItem.getProduct().getCategory().getId());
               productDto.setCategoryName(cartItem.getProduct().getCategory().getName());
               productDto.setUserId(cartItem.getProduct().getUser().getId());
                productDtoList.add(productDto);
           }
           orderedProductResponseDto.setProductDtoList(productDtoList);
        }
        return orderedProductResponseDto;

    }

    //every user can give a review

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(reviewDto.getProductId());
        if (optionalProduct.isPresent()) {
            Review review = new Review();
            review.setRating(reviewDto.getRating());
            review.setDescription(reviewDto.getDescription());
            review.setProduct(optionalProduct.get());

            // Get the current authenticated user
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + userDetails.getUsername()));

            review.setUser(currentUser);
            review.setImg(reviewDto.getImg().getBytes());
            return reviewRepository.save(review).getDto();
        }
        return null;
    }
}
