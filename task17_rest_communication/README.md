# Task 17: Inter-Service Communication via REST

This task demonstrates how two microservices communicate using Spring's `RestTemplate` and how to handle failures gracefully.

## Microservices
1. **Inventory Service (Port 8081)**:
   - Exposes `GET /api/inventory/{skuCode}`.
   - Provides stock information for products.
   - Mock data: `IPHONE15` (10 units), `MACBOOK_PRO` (5 units), `AIRPODS` (0 units).

2. **Order Service (Port 8082)**:
   - Exposes `POST /api/orders?skuCode=...&quantity=...`.
   - Consumes Inventory Service API to verify stock before placing an order.

## Graceful Failure Handling
The `OrderService` implements logic to handle common communication failures:
- **Service Down**: If Inventory Service is not reachable, it catches `ResourceAccessException` and returns a friendly error message instead of a 500 error.
- **API Errors (4xx/5xx)**: If the Inventory Service returns an error (e.g., Product Not Found), it catches `RestClientResponseException` and handles it.
- **Stock Validation**: Gracefully handles "Out of Stock" or "Insufficient Quantity" scenarios.

## How to Run
1. Start **Inventory Service**:
   ```bash
   cd inventory-service
   mvn spring-boot:run
   ```
2. Start **Order Service**:
   ```bash
   cd order-service
   mvn spring-boot:run
   ```

## How to Test
You can use the following `curl` commands:

### 1. Success Scenario (Stock available)
```bash
curl -X POST "http://localhost:8082/api/orders?skuCode=IPHONE15&quantity=2"
```
**Response**: `{"status": "Order placed successfully for IPHONE15"}`

### 2. Failure Scenario (Out of Stock)
```bash
curl -X POST "http://localhost:8082/api/orders?skuCode=AIRPODS&quantity=1"
```
**Response**: `{"status": "Product AIRPODS is out of stock."}`

### 3. Failure Scenario (Service Down)
- Stop the Inventory Service.
- Run the success scenario command again.
**Response**: `{"status": "Order could not be processed: Inventory Service is currently unavailable. Please try again later. (Graceful Failure Handling)"}`

### 4. Failure Scenario (Invalid Product)
```bash
curl -X POST "http://localhost:8082/api/orders?skuCode=UNKNOWN&quantity=1"
```
**Response**: `{"status": "Order failed: Inventory Service reported an error for SKU UNKNOWN. (Graceful response handling)"}`
