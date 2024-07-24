package tn.esprit.bazaar.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tn.esprit.bazaar.dto.CartItemDto;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long price;
    private Long quantity;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "buyer_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public CartItemDto getCartDto(){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(id);
        cartItemDto.setPrice(price);
        cartItemDto.setQuantity(quantity);
        cartItemDto.setProductId(product.getId());
        cartItemDto.setProductName(product.getName());
        cartItemDto.setProductImage(product.getImage());
        cartItemDto.setBuyerId(user.getId());
        return cartItemDto;
    }

}
