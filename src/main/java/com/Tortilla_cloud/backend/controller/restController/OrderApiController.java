package com.Tortilla_cloud.backend.controller.restController;

import com.Tortilla_cloud.backend.model.Order;
import com.Tortilla_cloud.backend.model.User;
import com.Tortilla_cloud.backend.repository.OrderRepository;
import com.Tortilla_cloud.backend.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderApiController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    //get all orders
    @GetMapping
    public ResponseEntity<Page<Order>> userOrders(@AuthenticationPrincipal User user ,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "placedAt") String sortBy,
                                                  @RequestParam(defaultValue = "DESC") String direction){
        if (user == null) {
            log.warn("Unauthorized access to /api/orders");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC")
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page , size , Sort.by(sortDirection , sortBy));

        Page<Order> orders = orderService.getUserOrders(user, pageable);
        log.info("Fetched {} orders for user: {}", orders.getNumberOfElements(), user.getUsername());
        return new ResponseEntity<>(orders , HttpStatus.OK);
    }


    //get orders by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrdersById(@PathVariable Long id){
        var order = orderRepository.findById(id);
        if (order.isPresent()){
            log.info("Fetched order with id: {}" , id);
            return ResponseEntity.ok(order.get());
        }
        log.warn("Order with id {} not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Order with id " + id + " not found"));
    }

    //post , create a new order
    @PostMapping
    public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order ,
                           @AuthenticationPrincipal User user){
        if (user == null) {
            log.warn("Unauthorized POST to /api/orders");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info("Creating new order for user: {}" , user.getUsername());
        Order saved = orderService.createOrder(order, user);
        return new ResponseEntity<>(saved , HttpStatus.CREATED);
    }
}
