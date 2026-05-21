package quickcommerce.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import quickcommerce.dto.OrderItemRequest;
import quickcommerce.dto.OrderRequest;
import quickcommerce.entity.CustomerOrder;
import quickcommerce.entity.OrderItem;
import quickcommerce.entity.OrderStatus;
import quickcommerce.entity.Product;
import quickcommerce.repository.OrderRepository;
import quickcommerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CustomerOrder placeOrder(OrderRequest orderRequest) {
        CustomerOrder order = new CustomerOrder();

        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerPhone(orderRequest.getCustomerPhone());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.getProductId()));

            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);

            totalAmount += product.getPrice() * itemRequest.getQuantity();
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PLACED);

        return orderRepository.save(order);
    }

    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public CustomerOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public CustomerOrder updateOrderStatus(Long id, OrderStatus status) {
        CustomerOrder order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}