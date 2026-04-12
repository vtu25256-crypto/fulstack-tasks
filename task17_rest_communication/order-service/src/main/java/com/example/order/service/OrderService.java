package com.example.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final RestTemplate restTemplate;
    private final String INVENTORY_SERVICE_URL = "http://localhost:8081/api/inventory/";

    public String placeOrder(String skuCode, int quantity) {
        log.info("Placing order for SKU: {} with quantity: {}", skuCode, quantity);

        try {
            // Inter-service communication via REST
            Map<String, Object> response = restTemplate.getForObject(INVENTORY_SERVICE_URL + skuCode, Map.class);

            if (response != null && (Boolean) response.get("isInStock")) {
                int availableQuantity = (Integer) response.get("quantity");
                if (availableQuantity >= quantity) {
                    return "Order placed successfully for " + skuCode;
                } else {
                    return "Insufficient stock for " + skuCode + ". Available: " + availableQuantity;
                }
            } else {
                return "Product " + skuCode + " is out of stock.";
            }

        } catch (ResourceAccessException e) {
            log.error("Inventory Service is down: {}", e.getMessage());
            return "Order could not be processed: Inventory Service is currently unavailable. Please try again later. (Graceful Failure Handling)";
        } catch (RestClientResponseException e) {
            log.error("Inventory Service returned an error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return "Order failed: Inventory Service reported an error for SKU " + skuCode + ". (Graceful response handling)";
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            return "Order failed due to an internal error. Please contact support.";
        }
    }
}
