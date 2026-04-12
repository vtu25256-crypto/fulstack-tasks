package com.example.order.controller;

import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Map<String, String> placeOrder(@RequestParam String skuCode, @RequestParam int quantity) {
        String status = orderService.placeOrder(skuCode, quantity);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", status);
        return response;
    }
}
