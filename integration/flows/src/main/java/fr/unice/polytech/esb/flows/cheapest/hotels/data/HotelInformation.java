package fr.unice.polytech.esb.flows.cheapest.hotels.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.esb.flows.cheapest.common.Buyable;

public class HotelInformation implements Buyable {

    @JsonProperty("city")
    private String place;
    @JsonProperty
    private String name;
    @JsonProperty("roomCost")
    private float price;

    public HotelInformation() {
    }

    public HotelInformation(String place, String name, float price) {
        this.place = place;
        this.name = name;
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
