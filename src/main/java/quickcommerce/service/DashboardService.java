package quickcommerce.service;

import org.springframework.stereotype.Service;
import quickcommerce.dto.DashboardSummaryResponse;
import quickcommerce.dto.FastMovingProductResponse;
import quickcommerce.entity.CustomerOrder;
import quickcommerce.entity.OrderItem;
import quickcommerce.entity.Product;
import quickcommerce.repository.OrderItemRepository;
import quickcommerce.repository.OrderRepository;
import quickcommerce.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public DashboardService(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public DashboardSummaryResponse getDashboardSummary() {

        List<CustomerOrder> orders = orderRepository.findAll();

        Long totalOrders = (long) orders.size();

        Double totalRevenue = orders.stream()
                .mapToDouble(order -> order.getTotalAmount() != null ? order.getTotalAmount() : 0.0)
                .sum();

        Long lowStockProductsCount = productRepository.findAll()
                .stream()
                .filter(product -> product.getStockQuantity() <= product.getLowStockThreshold())
                .count();

        List<OrderItem> orderItems = orderItemRepository.findAll();

        Map<Product, Long> productSalesMap = orderItems.stream()
                .collect(Collectors.groupingBy(
                        OrderItem::getProduct,
                        Collectors.summingLong(OrderItem::getQuantity)
                ));

        List<FastMovingProductResponse> fastMovingProducts = productSalesMap.entrySet()
                .stream()
                .map(entry -> new FastMovingProductResponse(
                        entry.getKey().getId(),
                        entry.getKey().getName(),
                        entry.getValue()
                ))
                .sorted(Comparator.comparing(FastMovingProductResponse::getTotalSold).reversed())
                .limit(5)
                .toList();

        return new DashboardSummaryResponse(
                totalOrders,
                totalRevenue,
                lowStockProductsCount,
                fastMovingProducts
        );
    }
}