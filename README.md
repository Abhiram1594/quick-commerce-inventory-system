# Quick Commerce Inventory & Order Management System

A backend project built using Java, Spring Boot, MySQL, REST APIs, and Maven.  
The system allows admins to manage products, customers to place orders, stock to reduce automatically, order status to be updated, and dashboard analytics to be viewed.

## Features

- Add and manage products
- Store product details such as price, category, stock quantity, warehouse location, and low-stock threshold
- Place customer orders
- Automatically reduce stock after order placement
- Prevent order placement when stock is insufficient
- Update order status:
  - PLACED
  - PACKED
  - OUT_FOR_DELIVERY
  - DELIVERED
- Dashboard summary API showing:
  - Total orders
  - Total revenue
  - Low-stock products
  - Fast-moving products
- Global exception handling for clean API error responses

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- REST APIs
- Maven
- Postman
- Git & GitHub

## API Endpoints

### Product APIs

Add product:

```http
POST /api/products
```

Get all products:

```http
GET /api/products
```

Get product by ID:

```http
GET /api/products/{id}
```

Update product:

```http
PUT /api/products/{id}
```

Delete product:

```http
DELETE /api/products/{id}
```

### Order APIs

Place order:

```http
POST /api/orders
```

Get all orders:

```http
GET /api/orders
```

Get order by ID:

```http
GET /api/orders/{id}
```

Update order status:

```http
PUT /api/orders/{id}/status?status=PACKED
```

Valid statuses:

```text
PLACED
PACKED
OUT_FOR_DELIVERY
DELIVERED
```

### Dashboard API

Get dashboard summary:

```http
GET /api/dashboard/summary
```

## Sample Product JSON

```json
{
  "name": "Amul Milk",
  "description": "Fresh toned milk 1L pack",
  "category": "Dairy",
  "price": 65.0,
  "stockQuantity": 100,
  "lowStockThreshold": 10,
  "warehouseLocation": "Hyderabad-WH1"
}
```

## Sample Order JSON

```json
{
  "customerName": "Rahul",
  "customerPhone": "9876543210",
  "items": [
    {
      "productId": 2,
      "quantity": 2
    }
  ]
}
```

## Sample Order Status Update

```http
PUT /api/orders/1/status?status=PACKED
```

## Sample Dashboard Response

```json
{
  "totalOrders": 2,
  "totalRevenue": 1500.0,
  "lowStockProductsCount": 1,
  "fastMovingProducts": []
}
```

## Error Handling

The project includes global exception handling to return clean error responses.

Example invalid status response:

```json
{
  "timestamp": "2026-05-22T15:40:00",
  "status": 400,
  "error": "Invalid Input",
  "message": "Invalid value provided. Please check your request."
}
```

## How to Run

1. Clone the repository:

```bash
git clone <your-repo-link>
```

2. Open the project in VS Code or IntelliJ.

3. Configure MySQL database in:

```text
src/main/resources/application.properties
```

4. Run the application:

```bash
mvn spring-boot:run
```

5. Test APIs using Postman.

## Project Status

Completed backend features:

- Product management
- Order placement
- Stock reduction
- Order status update
- Dashboard summary
- Error handling

## Future Improvements

- Add authentication and authorization
- Add React dashboard frontend
- Add Docker support
- Deploy backend on cloud
- Add AI-based product description or stock recommendation