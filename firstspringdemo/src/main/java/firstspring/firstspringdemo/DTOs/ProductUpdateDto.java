package firstspring.firstspringdemo.DTOs;

public class ProductUpdateDto {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String locationId;

    public ProductUpdateDto() {
    }

    public ProductUpdateDto(String name, String description, double price, int quantity, String locationId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.locationId = locationId;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
