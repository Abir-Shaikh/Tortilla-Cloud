package com.Tortilla_cloud.controller;

import com.Tortilla_cloud.model.Order;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping({"/orders", "/order"})
public class OrdersCurrentController {

    @GetMapping("/current")
    public String orderForm(Model model){
        model.addAttribute("order" , new Order());
        return "orderForm";
    }

    @PostMapping("/current")
    public String orderSubmission(@Valid Order order , Errors errors){
        if (errors.hasErrors()) {
            return "orderForm";
        }
        log.info("Order Submiited : " + order);
        return "redirect:/";
    }
}
