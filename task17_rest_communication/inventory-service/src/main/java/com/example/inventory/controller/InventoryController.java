package com.example.inventory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private static final Map<String, Integer> stockMap = new HashMap<>();

    static {
        stockMap.put("IPHONE15", 10);
        stockMap.put("MACBOOK_PRO", 5);
        stockMap.put("AIRPODS", 0);
    }

    @GetMapping("/{skuCode}")
    public Map<String, Object> checkStock(@PathVariable String skuCode) {
        // Simple delay to simulate real processing
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        if (!stockMap.containsKey(skuCode)) {
            throw new RuntimeException("Product not found: " + skuCode);
        }

        int stock = stockMap.get(skuCode);
        
        Map<String, Object> response = new HashMap<>();
        response.put("skuCode", skuCode);
        response.put("isInStock", stock > 0);
        response.put("quantity", stock);
        
        return response;
    }
}
