package com.example.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/{orderId}/{userId}/{productId}")
    public String getOrderDetails(@PathVariable String orderId, 
                                  @PathVariable String userId, 
                                  @PathVariable String productId) {
        
        String userInfo = userClient.getUserInfo(userId);
        String productInfo = productClient.getProductInfo(productId);
        
        return "Order Summary:\n" +
               "Order ID: " + orderId + "\n" +
               "Customer: " + userInfo + "\n" +
               "Product: " + productInfo;
    }
}
