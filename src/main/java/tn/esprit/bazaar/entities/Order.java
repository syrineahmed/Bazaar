package tn.esprit.bazaar.entities;

import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.bazaar.dto.OrderDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id",referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
    private List<CartItem> cartItems;

    public OrderDto getOrderDto(){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setDate(date);
        orderDto.setAmount(amount);
        orderDto.setAddress(address);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setTrackingId(trackingId);
        orderDto.setUserName(user.getFirstName()+" "+user.getLastName());
        if (coupon != null){
            orderDto.setCouponName(coupon.getName())   ;
        }
        return orderDto;
    }



}
