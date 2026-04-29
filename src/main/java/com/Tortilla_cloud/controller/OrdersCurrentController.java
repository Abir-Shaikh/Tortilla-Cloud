package com.Tortilla_cloud.controller;

import com.Tortilla_cloud.model.Order;
import com.Tortilla_cloud.model.User;
import com.Tortilla_cloud.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping({"/orders", "/order"})
public class OrdersCurrentController {

    private final OrderRepository orderRepository;

    public OrdersCurrentController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
        orderRepository.save(order);
        sessionStatus.setComplete();
        log.info("Order Submitted for user:  {} " , user.getUsername());
        return "redirect:/";
    }
}
