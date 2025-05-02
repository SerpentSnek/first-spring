package firstspring.firstspringdemo.DTOs;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// useful because it allows you to specify which fields you want to update, without requiring the client to send all of the fields in the InventoryDTO. It also makes PATCH requests easier. --Gemini flash
public class InventoryUpdateDto {
    
    @Min(value = 0, message = "Quantity must be a non-negative number")
    private Integer quantity;
    
    @NotNull(message = "Product ID is required")
    private UUID productId; //Optional
    
    @NotNull(message = "Location ID is required")
    private UUID locationId; //Optional

    @Min(value = 0, message = "reorderPoint must be a non-negative number")
    private Integer reorderPoint; //Optional
    
    @Min(value = 0, message = "reorderQuantity must be a non-negative number")
    private Integer reorderQuantity; //Optional

    // Constructors (default, and with all fields)
    public InventoryUpdateDto() {}
    public InventoryUpdateDto(Integer quantity, UUID productId, UUID locationId, Integer reorderPoint, Integer reorderQuantity) {
        this.quantity = quantity;
        this.productId = productId;
        this.locationId = locationId;
        this.reorderPoint = reorderPoint;
        this.reorderQuantity = reorderQuantity;
    }

    // Getters and setters
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
