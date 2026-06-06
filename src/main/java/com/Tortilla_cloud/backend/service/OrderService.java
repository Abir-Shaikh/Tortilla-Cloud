package com.Tortilla_cloud.backend.service;

import com.Tortilla_cloud.backend.DTO.OrderMessage;
import com.Tortilla_cloud.backend.model.Order;
import com.Tortilla_cloud.backend.model.User;
import com.Tortilla_cloud.backend.repository.OrderRepository;
import com.Tortilla_cloud.backend.service.messaging.producer.OrderPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderPublisher orderPublisher;

    public OrderService(OrderRepository orderRepository, OrderPublisher orderPublisher) {
        this.orderRepository = orderRepository;
        this.orderPublisher = orderPublisher;
    }

    //get User Orders
    public Page<Order> getUserOrders(User user , Pageable pageable){
        log.info("Fetching Orders for user: {}" , user.getUsername());
        return orderRepository.findByUser(user, pageable);
    }


    //create order and send to message queue
    public Order createOrder(Order order , User user){

        order.setUser(user);

        log.info("Creating order for user: {}" , user.getUsername());
        Order savedOrder = orderRepository.save(order);

        OrderMessage message = OrderMessage.from(
                savedOrder.getId(),
                user.getUsername(),
                savedOrder.getCity(),
                savedOrder.getState()
        );

        orderPublisher.publishOrderMessage(message);

        return savedOrder;
    }
}
