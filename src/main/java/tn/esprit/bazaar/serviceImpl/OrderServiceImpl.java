package tn.esprit.bazaar.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tn.esprit.bazaar.dto.OrderDto;
import tn.esprit.bazaar.entities.Order;
import tn.esprit.bazaar.entities.OrderStatus;
import tn.esprit.bazaar.repository.OrderRepository;
import tn.esprit.bazaar.service.OrderService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    //the admin can check all placed orders
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getAllPlacedOrders() {
        List<Order> orderList = orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.PLACED, OrderStatus.SHIPPED, OrderStatus.DELIVERED));
        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    //the admin can change the status of an order
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto changeOrderStatus(Long orderId, String status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }


}
