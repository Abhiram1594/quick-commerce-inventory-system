package quickcommerce.dto;

public class FastMovingProductResponse {

    private Long productId;
    private String productName;
    private Long totalSold;

    public FastMovingProductResponse(Long productId, String productName, Long totalSold) {
        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Long getTotalSold() {
        return totalSold;
    }
}