package com.linh.OrderService.controller;

import com.linh.OrderService.kafka.OrderProducer;
import com.linh.OrderService.model.KeyCloakCurrentUser;
import com.linh.OrderService.model.OrderEvent;
import com.linh.OrderService.model.OrderRequest;
import com.linh.OrderService.model.OrderResponse;
import com.linh.OrderService.serice.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WebClient webClient;

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping(path = "/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest request){
        long orderId = orderService.placeOrder(request);
        log.info("Order id {}", orderId);
        return ResponseEntity.ok(orderId);
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){
        OrderResponse response = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/kafka/create")
    public ResponseEntity<String> placeWithKafka(@RequestBody OrderRequest request){
        OrderEvent event = OrderEvent.builder()
                .message("Order status is pending")
                .status("PENDING")
                .request(request)
                .build();

        orderProducer.sendMessage(event);

        return ResponseEntity.ok("Order Placed Successfully...");
    }

    @GetMapping(path = "/test_security")
    public String testSecurity(){
        String scopes = webClient
                .get()
                .uri("http://PRODUCT-SERVICE/product/test_security")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return "Product scopes: " + scopes;
    }

    @GetMapping(path = "/getCurrentUser")
    public ResponseEntity<?> getCurrentUser(){
        return ResponseEntity.ok(
                webClient
                .get()
                .uri("http://USER-SERVICE/user/current")
                        .retrieve()
                        .bodyToMono(KeyCloakCurrentUser.class)
                        .block()
        );
    }
}
