package firstspring.firstspringdemo.Entities;

import jakarta.persistence.*;
// import java.util.Objects;
import java.math.BigDecimal;
// import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Use UUID generation
    private UUID productId;  // Changed to UUID

    private String name;
    private String description;
    private String sku;
    private String category; // Could be an Enum later
    private String manufacturer;
    private BigDecimal unitPrice;
    private String imageUrl;

    // Constructors (no-arg, all-args) - important for JPA and Jackson (JSON serialization)
    public Product() {}

    public Product(String name, String description, String sku, String category, String manufacturer, BigDecimal unitPrice, String imageUrl) {
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.category = category;
        this.manufacturer = manufacturer;
        this.unitPrice = unitPrice;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters (for all fields)
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    // Override equals and hashCode (optional, but good practice for entities)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return productId.equals(product.productId);
    }
    @Override
    public int hashCode() {
        return productId.hashCode();
    }
    // toString method (optional, useful for debugging)
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", category='" + category + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", unitPrice=" + unitPrice +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
    // Additional methods (if needed, e.g., for business logic)
}
