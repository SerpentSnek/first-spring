package firstspring.firstspringdemo.DTOs;

public class InventoryCreateDto {
    private String productId;
    private String locationId;
    private Integer quantity;
    private Integer reorderPoint; // Optional
    private Integer reorderQuantity; // Optional

    // Constructors (default, and with all fields)
    public InventoryCreateDto() {}

    public InventoryCreateDto(String productId, String locationId, Integer quantity, Integer reorderPoint, Integer reorderQuantity) {
        this.productId = productId;
        this.locationId = locationId;
        this.quantity = quantity;
        this.reorderPoint = reorderPoint;
        this.reorderQuantity = reorderQuantity;
    }

    // Getters and setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }
}
