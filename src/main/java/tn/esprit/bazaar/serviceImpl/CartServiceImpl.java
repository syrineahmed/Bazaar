package tn.esprit.bazaar.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.AddProductInCartDto;
import tn.esprit.bazaar.entities.*;
import tn.esprit.bazaar.repository.CartItemRepository;
import tn.esprit.bazaar.repository.OrderRepository;
import tn.esprit.bazaar.repository.PorductRepository;
import tn.esprit.bazaar.repository.UserRepository;
import tn.esprit.bazaar.service.CartService;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private PorductRepository porductRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        // Obtain the currently authenticated user
        User currentUser = userServiceImpl.getCurrentUser();
        Long currentUserId = currentUser.getId();

        // Use the currentUserId instead of getting it from addProductInCartDto
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(currentUserId, OrderStatus.PENDING);
        Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), currentUserId);

        if (optionalCartItem.isPresent()) {
            CartItem existingCartItem = optionalCartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1); // Increment quantity
            cartItemRepository.save(existingCartItem);
        } else {
            Optional<Product> optionalProduct = porductRepository.findById(addProductInCartDto.getProductId());

            if(optionalProduct.isPresent()){
                CartItem cartItem = new CartItem();
                cartItem.setProduct(optionalProduct.get());
                cartItem.setPrice(optionalProduct.get().getPrice());
                cartItem.setQuantity(1L); // Set initial quantity
                cartItem.setOrder(activeOrder);
                cartItem.setUser(currentUser); // Use the current user
                cartItemRepository.save(cartItem);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
                activeOrder.getCartItems().add(cartItem);
                orderRepository.save(activeOrder);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        }

// After adding/updating the cart item

// Recalculate the total amount and amount for the order
        double totalAmount = 0;
        double amount = 0;
        for (CartItem ci : activeOrder.getCartItems()) {
            double itemTotal = ci.getPrice() * ci.getQuantity();
            amount += itemTotal;
            // Add any additional calculations for totalAmount if necessary
            totalAmount += itemTotal;
        }

// Update the order's totalAmount and amount
        activeOrder.setTotalAmount(Math.round(totalAmount));
        activeOrder.setAmount(Math.round(amount)); // Ensure this method exists and is correctly implemented in the Order entity

// Save the updated order
        orderRepository.save(activeOrder);

        return ResponseEntity.ok().body("Product added to cart successfully");    }

}

