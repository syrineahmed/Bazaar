package tn.esprit.bazaar.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.CartItemDto;
import tn.esprit.bazaar.dto.OrderDto;
import tn.esprit.bazaar.entities.*;
import tn.esprit.bazaar.exceptions.ValidationException;
import tn.esprit.bazaar.repository.*;
import tn.esprit.bazaar.service.CartService;
import tn.esprit.bazaar.entities.*;
import tn.esprit.bazaar.dto.AddProductInCartDto;
import tn.esprit.bazaar.service.CouponService;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;

    //everyyusercan add a product to cart

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        User currentUser = userServiceImpl.getCurrentUser();
        Long currentUserId = currentUser.getId();

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(currentUserId, OrderStatus.PENDING);
        Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), currentUserId);

        if (optionalCartItem.isPresent()) {
            CartItem existingCartItem = optionalCartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            cartItemRepository.save(existingCartItem);
        } else {
            Optional<Product> optionalProduct = porductRepository.findById(addProductInCartDto.getProductId());

            if(optionalProduct.isPresent()){
                CartItem cartItem = new CartItem();
                cartItem.setProduct(optionalProduct.get());
                cartItem.setPrice(optionalProduct.get().getPrice());
                cartItem.setQuantity(1L);
                cartItem.setOrder(activeOrder);
                cartItem.setUser(currentUser);
                cartItemRepository.save(cartItem);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
                activeOrder.getCartItems().add(cartItem);
                orderRepository.save(activeOrder);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
            }
        }


        double totalAmount = 0;
        double amount = 0;
        for (CartItem ci : activeOrder.getCartItems()) {
            double itemTotal = ci.getPrice() * ci.getQuantity();
            amount += itemTotal;

            totalAmount += itemTotal;
        }


        activeOrder.setTotalAmount(Math.round(totalAmount));
        activeOrder.setAmount(Math.round(amount));

        orderRepository.save(activeOrder);

        return ResponseEntity.ok().body("Product added to cart successfully");    }

    // Admin Can get Cart by userId


    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        List<CartItemDto> cartItemDtoList = activeOrder.getCartItems().stream().map(CartItem::getCartDto).collect(Collectors.toList()) ;
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemDtoList);
        if (activeOrder.getCoupon() != null){     ///utilisation de coupon
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }
        return orderDto;
    }

    //every user can check his cart

    public OrderDto getCartByCurrentUser() {
        Long currentUserId = userServiceImpl.getCurrentUser().getId();
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(currentUserId, OrderStatus.PENDING);


        OrderDto orderDto = new OrderDto();


        if (activeOrder != null) {
            List<CartItemDto> cartItemDtoList = activeOrder.getCartItems().stream()
                    .map(CartItem::getCartDto)
                    .collect(Collectors.toList());

            orderDto.setAmount(activeOrder.getAmount());
            orderDto.setId(activeOrder.getId());
            orderDto.setOrderStatus(activeOrder.getOrderStatus());
            orderDto.setTotalAmount(activeOrder.getTotalAmount());
            orderDto.setCartItems(cartItemDtoList);


        } else {

            orderDto.setOrderStatus(OrderStatus.NONE);
            orderDto.setCartItems(Collections.emptyList());
        }

        return orderDto;
    }

    //everu user can applyCoupon

    public OrderDto applyCoupon(Long userId, String code) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon not found"));
        if (couponIsExpired(coupon)) {

            throw new ValidationException("Coupon is expired");
        }
        double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount() - discountAmount;
        activeOrder.setAmount((long)netAmount);
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);
        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();

    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();
        return expirationDate != null && currentDate.after(expirationDate);
    }


    //the user can increase product quantity in cart
   /* public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
       Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
        }*/
}

