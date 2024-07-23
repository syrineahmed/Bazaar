package tn.bazaar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.bazaar.dto.OrderDto;
import tn.bazaar.service.CartService;
import tn.bazaar.dto.AddProductInCartDto;

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


}
