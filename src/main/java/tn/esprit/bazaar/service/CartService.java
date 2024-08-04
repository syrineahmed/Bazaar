package tn.esprit.bazaar.service;

import org.springframework.http.ResponseEntity;
import tn.esprit.bazaar.dto.OrderDto;
import tn.esprit.bazaar.dto.AddProductInCartDto;
import tn.esprit.bazaar.dto.PlaceOrderDto;

import java.util.List;
import java.util.UUID;

public interface CartService {
     ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto ) ;
     OrderDto getCartByUserId(Long userId) ;
     OrderDto getCartByCurrentUser() ;
     OrderDto applyCoupon(Long userId, String code);OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) ;
     OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);
     OrderDto placeOrder(PlaceOrderDto placeOrderDto) ;
     List<OrderDto> getMyPlacedOrders() ;
     OrderDto searchOrderByTrackingId(UUID trackingId);









     }
