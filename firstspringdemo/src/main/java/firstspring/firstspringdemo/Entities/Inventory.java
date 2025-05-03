package firstspring.firstspringdemo.Entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID inventoryId;

    @ManyToOne  // Many Inventory entries can relate to one Product
    @JoinColumn(name = "product_id", nullable = false)  //  Database column name for the foreign key
    private Product product;

    @ManyToOne  // Many Inventory entries can relate to one Location
    @JoinColumn(name = "location_id", nullable = false) // Database column name for the foreign key
    private Location location;

    private Integer quantity;
    private Integer reorderPoint;
    private Integer reorderQuantity;
    private LocalDateTime lastStockUpdate;
    
    public Inventory() {}
    
    public Inventory(Product product, Location location, Integer quantity, Integer reorderPoint, Integer reorderQuantity, LocalDateTime lastStockUpdate) {
        this.product = product;
        this.location = location;
        this.quantity = quantity;
        this.reorderPoint = reorderPoint;
        this.reorderQuantity = reorderQuantity;
        this.lastStockUpdate = lastStockUpdate;
    }
    // Getters and Setters
    public UUID getInventoryId() {
        return inventoryId;
    }
    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }
    public UUID getLocationId() {
        return location != null ? location.getLocationId() : null; // Return the location ID if location is not null
    }
    public void setLocationId(UUID locationId) {
        if (location != null) {
            location.setLocationId(locationId); // Set the location ID if location is not null
        }
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
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
    public LocalDateTime getLastStockUpdate() {
        return lastStockUpdate;
    }
    public void setLastStockUpdate(LocalDateTime lastStockUpdate) {
        this.lastStockUpdate = lastStockUpdate;
    }
    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", product=" + product +
                ", location=" + location +
                ", quantity=" + quantity +
                ", reorderPoint=" + reorderPoint +
                ", reorderQuantity=" + reorderQuantity +
                ", lastStockUpdate=" + lastStockUpdate +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return inventoryId.equals(inventory.inventoryId);
    }
    @Override
    public int hashCode() {
        return inventoryId.hashCode();
    }
    // Optional: You can add additional methods to check stock levels, reorder status, etc.
    public boolean isBelowReorderPoint() {
        return quantity <= reorderPoint;
    }
    public void updateStock(int amount) {
        this.quantity += amount;
        this.lastStockUpdate = LocalDateTime.now();
    }
}
