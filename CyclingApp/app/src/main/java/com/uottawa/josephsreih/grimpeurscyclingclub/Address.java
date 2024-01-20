package com.uottawa.josephsreih.grimpeurscyclingclub;

public class Address {
    private String zipCode;
    private String location;

    public Address(String location, String zipCode) {

        this.location = location;
        this.zipCode = zipCode;
    }
    public Address(){

    }

    // Setters
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getters
    public String getZipCode() {
        return this.zipCode;
    }

    public String getLocation() {
        return this.location;
    }
}
