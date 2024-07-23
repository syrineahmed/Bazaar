package tn.esprit.bazaar.dto;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.bazaar.entities.CartItem;
import tn.esprit.bazaar.entities.OrderStatus;
import tn.esprit.bazaar.entities.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private Long id;
    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;

    private String userName;

    private List<CartItemDto> cartItems;



}
