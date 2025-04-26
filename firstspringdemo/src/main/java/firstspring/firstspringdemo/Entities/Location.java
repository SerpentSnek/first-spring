package firstspring.firstspringdemo.Entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID locationId;

    private String name;
    private String address;
    private String type;
    private String contactPerson;

    public Location() {}

    // ... (Constructors, Getters, and Setters) ...
    public Location(String name, String address, String type, String contactPerson) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.contactPerson = contactPerson;
    }

    // getters and setters
    public UUID getLocationId() {
        return locationId;
    }
    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContactPerson() {
        return contactPerson;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", type='" + type + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }
    // hashCode and equals methods (optional, but good practice for entities)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return locationId != null && locationId.equals(location.getLocationId());
    }
    @Override
    public int hashCode() {
        return 31; // or use a more complex hash code based on fields
    }
    // You can also override toString() for better readability in logs or debugging

}