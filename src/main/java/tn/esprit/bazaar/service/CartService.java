package tn.esprit.bazaar.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.bazaar.dto.AddProductInCartDto;
import tn.esprit.bazaar.dto.OrderDto;

public interface CartService {
     ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto ) ;
  OrderDto getCartByUserId(Long userId) ;
     OrderDto getCartByCurrentUser() ;



    }
