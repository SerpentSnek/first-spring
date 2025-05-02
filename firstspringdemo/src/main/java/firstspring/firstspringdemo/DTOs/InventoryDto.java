package firstspring.firstspringdemo.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class InventoryDto {

    private UUID inventoryId;

    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotNull(message = "Location ID is required")
    private UUID locationId;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be a non-negative number")
    private Integer quantity;

    private Integer reorderPoint; //Optional
    private Integer reorderQuantity; //Optional

    // Constructors (default, and with all fields)
    public InventoryDto() {}

    public InventoryDto(UUID inventoryId, UUID productId, UUID locationId, Integer quantity, Integer reorderPoint, Integer reorderQuantity) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.locationId = locationId;
        this.quantity = quantity;
        this.reorderPoint = reorderPoint;
        this.reorderQuantity = reorderQuantity;
    }

    // Getters and setters
    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
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
