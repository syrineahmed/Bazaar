package tn.esprit.bazaar.service;

import tn.esprit.bazaar.dto.OrderDto;
import tn.esprit.bazaar.entities.OrderStatus;

import java.util.List;

public interface OrderService {
     List<OrderDto> getAllPlacedOrders() ;
    OrderDto changeOrderStatus(Long orderId, String status);



    }
