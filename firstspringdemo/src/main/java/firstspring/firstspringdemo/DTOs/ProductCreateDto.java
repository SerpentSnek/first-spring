package firstspring.firstspringdemo.DTOs;

import java.util.UUID;

public class ProductCreateDto {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private UUID productId;

    public ProductCreateDto() {
    }
    
    public ProductCreateDto(String name, String description, double price, int quantity, UUID productId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
