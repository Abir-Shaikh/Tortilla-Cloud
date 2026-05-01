package com.Tortilla_cloud.controller;

import com.Tortilla_cloud.model.Order;
import com.Tortilla_cloud.model.User;
import com.Tortilla_cloud.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderApiController {

    private final OrderRepository orderRepository;

    public OrderApiController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //get all orders
    @GetMapping
    public ResponseEntity<List<Order>> userOrders(@AuthenticationPrincipal User user){
        if (user == null) {
            log.warn("Unauthorized access to /api/orders");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Pageable pageable = PageRequest.of(0,10);
        List<Order> orders = orderRepository.findByUser(user , pageable);
        log.info("Fetched {} orders for user: {}" , orders.size() , user.getUsername());
        return ResponseEntity.ok(orders);
    }


    //get orders by id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrdersById(@PathVariable Long id){
        var order = orderRepository.findById(id);
        if (order.isPresent()){
            log.info("Fetched order with id: {}" , id);
            return ResponseEntity.ok(order.get());
        }
        log.warn("Order with id {} not found", id);
        return ResponseEntity.notFound().build();
    }

    //post , create a new order
    @PostMapping
    public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order ,
                           @AuthenticationPrincipal User user){
        order.setUser(user);
        log.info("Creating new order for user: {}" , user.getUsername());
        Order save = orderRepository.save(order);
        return new ResponseEntity<>(save , HttpStatus.CREATED);
    }
}
