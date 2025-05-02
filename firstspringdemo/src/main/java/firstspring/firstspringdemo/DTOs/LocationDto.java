package firstspring.firstspringdemo.DTOs;

import java.util.UUID;

public class LocationDto {
    private UUID locationId;
    private String name;
    private String address;
    // private String city;
    // private String state;
    // private String country;
    // private String postalCode;

    public LocationDto() {
    }

    public LocationDto(UUID locationId, String name, String address, String city, String state, String country, String postalCode) {
        this.locationId = locationId;
        this.name = name;
        this.address = address;
        // this.city = city;
        // this.state = state;
        // this.country = country;
        // this.postalCode = postalCode;
    }

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

    // public String getCity() {
    //     return city;
    // }

    // public void setCity(String city) {
    //     this.city = city;
    // }

    // public String getState() {
    //     return state;
    // }

    // public void setState(String state) {
    //     this.state = state;
    // }

    // public String getCountry() {
    //     return country;
    // }

    // public void setCountry(String country) {
    //     this.country = country;
    // }

    // public String getPostalCode() {
    //     return postalCode;
    // }

    // public void setPostalCode(String postalCode) {
    //     this.postalCode = postalCode;
    // }
}
