package tn.bazaar.service;

import org.springframework.http.ResponseEntity;
import tn.bazaar.dto.OrderDto;
import tn.bazaar.dto.AddProductInCartDto;

public interface CartService {
     ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto ) ;
  OrderDto getCartByUserId(Long userId) ;
     OrderDto getCartByCurrentUser() ;



    }
