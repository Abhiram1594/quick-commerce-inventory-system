package quickcommerce.controller;

import org.springframework.web.bind.annotation.*;
import quickcommerce.dto.OrderRequest;
import quickcommerce.entity.CustomerOrder;
import quickcommerce.entity.OrderStatus;
import quickcommerce.service.OrderService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public CustomerOrder placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping
    public List<CustomerOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public CustomerOrder getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}/status")
    public CustomerOrder updateOrderStatus(
            @PathVariable Long id,
            @RequestParam(required = false) OrderStatus status,
            @RequestBody(required = false) Map<String, String> request
    ) {
        OrderStatus newStatus;

        if (status != null) {
            newStatus = status;
        } else if (request != null && request.containsKey("status")) {
            newStatus = OrderStatus.valueOf(request.get("status"));
        } else {
            throw new RuntimeException("Status is required");
        }

        return orderService.updateOrderStatus(id, newStatus);
    }
}