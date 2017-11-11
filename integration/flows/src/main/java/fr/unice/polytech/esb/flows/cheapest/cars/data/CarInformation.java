package fr.unice.polytech.esb.flows.cheapest.cars.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.esb.flows.cheapest.common.Buyable;

public class CarInformation implements Buyable {

    @JsonProperty
    private String brand;
    @JsonProperty
    private String model;
    @JsonProperty
    private String place;
    @JsonProperty
    private Float price;

    public CarInformation() {
    }

    public CarInformation(String brand, String model, String place, float price) {
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

    @Override
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
