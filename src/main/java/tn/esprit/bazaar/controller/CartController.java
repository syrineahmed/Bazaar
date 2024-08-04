package tn.esprit.bazaar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.bazaar.dto.OrderDto;
import tn.esprit.bazaar.dto.PlaceOrderDto;
import tn.esprit.bazaar.exceptions.ValidationException;
import tn.esprit.bazaar.service.CartService;
import tn.esprit.bazaar.dto.AddProductInCartDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/create")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto) {
        return cartService.addProductToCart(addProductInCartDto);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/getmycart")
    public ResponseEntity<?> getCartByCurrentUser() {
        OrderDto orderDto = cartService.getCartByCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
        try {
            OrderDto orderDto = cartService.applyCoupon(userId, code);
            return ResponseEntity.ok().body(orderDto);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/addition")
    public ResponseEntity<OrderDto> increaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseProductQuantity(addProductInCartDto));
    }
    @PostMapping("/decrease")
    public ResponseEntity<OrderDto> decreaseProductQuantity(@RequestBody AddProductInCartDto addProductInCartDto) {
        return ResponseEntity.status(HttpStatus.OK).body(cartService.decreaseProductQuantity(addProductInCartDto));
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        try {
            OrderDto orderDto = cartService.placeOrder(placeOrderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderDto);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @GetMapping("/myPlacedOrders")
    public ResponseEntity<List<OrderDto>> getMyPlacedOrders() {
        return ResponseEntity.ok(cartService.getMyPlacedOrders());
    }

    @GetMapping("/searchOrder/{trackingId}")
    public ResponseEntity<?> searchOrderByTrackingId(@PathVariable String trackingId) {
        if (!isValidUUID(trackingId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID format: " + trackingId);
        }
        UUID uuid = UUID.fromString(trackingId);
        OrderDto orderDto = cartService.searchOrderByTrackingId(uuid);
        if (orderDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found for tracking ID: " + trackingId);
        }
        return ResponseEntity.ok(orderDto);
    }

    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
