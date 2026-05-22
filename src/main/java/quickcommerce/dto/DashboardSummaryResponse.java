package quickcommerce.dto;

import java.util.List;

public class DashboardSummaryResponse {

    private Long totalOrders;
    private Double totalRevenue;
    private Long lowStockProductsCount;
    private List<FastMovingProductResponse> fastMovingProducts;

    public DashboardSummaryResponse(Long totalOrders, Double totalRevenue,
                                    Long lowStockProductsCount,
                                    List<FastMovingProductResponse> fastMovingProducts) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.lowStockProductsCount = lowStockProductsCount;
        this.fastMovingProducts = fastMovingProducts;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public Long getLowStockProductsCount() {
        return lowStockProductsCount;
    }

    public List<FastMovingProductResponse> getFastMovingProducts() {
        return fastMovingProducts;
    }
}