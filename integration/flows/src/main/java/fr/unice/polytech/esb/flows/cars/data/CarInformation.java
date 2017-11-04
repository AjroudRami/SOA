package fr.unice.polytech.esb.flows.cars.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarInformation {

    @JsonProperty private String brand;
    @JsonProperty private String model;
    @JsonProperty private String place;
    @JsonProperty private double price;

    public CarInformation() {
    }

    public CarInformation(String brand, String model, String place, double price) {
        this.brand = brand;
        this.model = model;
        this.place = place;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
