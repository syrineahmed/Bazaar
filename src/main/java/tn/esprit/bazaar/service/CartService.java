package tn.esprit.bazaar.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.bazaar.dto.AddProductInCartDto;

public interface CartService {
     ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto ) ;

    }
