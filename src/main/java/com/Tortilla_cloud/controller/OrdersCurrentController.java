package com.Tortilla_cloud.controller;

import com.Tortilla_cloud.DTO.OrderMessage;
import com.Tortilla_cloud.configuration.OrderProps;
import com.Tortilla_cloud.model.Order;
import com.Tortilla_cloud.model.User;
import com.Tortilla_cloud.repository.OrderRepository;
import com.Tortilla_cloud.service.messaging.producer.OrderPublisher;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrdersCurrentController {

    private final OrderRepository orderRepository;
    private final OrderProps orderProps;
    private final OrderPublisher orderPublisher;

    public OrdersCurrentController(OrderRepository orderRepository,
                                   OrderProps orderProps,
                                   OrderPublisher orderPublisher) {
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
        this.orderPublisher = orderPublisher;
    }


    @GetMapping
    public String OrderForUsers(Model model , @AuthenticationPrincipal User user){
        Pageable pageable = PageRequest.of(0 , orderProps.getPageSize());
        Page<Order> orders = orderRepository.findByUser(user , pageable);
        model.addAttribute("orders" , orders);
        log.info("Fetched {} orders for user: {}", orders.getTotalElements() , user.getUsername());
        return "orderList";
    }

    @GetMapping("/current")
    public String orderForm(Model model){
        model.addAttribute("order" , new Order());
        return "orderForm";
    }

    @PostMapping("/current")
    public String processOrder(@Valid Order order , Errors errors
                                   , SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user){
        if (errors.hasErrors()) {
            return "orderForm";
        }
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);

        OrderMessage message = OrderMessage.from(
                savedOrder.getId() ,
                savedOrder.getName(),
                savedOrder.getCity(),
                savedOrder.getState()
        );

        orderPublisher.publishOrderMessage(message);
        sessionStatus.setComplete();
        log.info("Order Submitted for user:  {} " , user.getUsername());
        return "redirect:/";
    }
}
