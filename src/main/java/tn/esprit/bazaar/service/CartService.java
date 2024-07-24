package tn.esprit.bazaar.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.bazaar.dto.OrderDto;
import tn.esprit.bazaar.dto.AddProductInCartDto;

public interface CartService {
     ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto ) ;
  OrderDto getCartByUserId(Long userId) ;
     OrderDto getCartByCurrentUser() ;
   OrderDto applyCoupon(Long userId, String code);




    }
