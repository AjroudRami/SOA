package fr.unice.polytech.esb.flows.hotels.data;

import java.util.Date;

public class HotelInfo {

    private String place;
    private String name;
    private int price;

    public HotelInfo() {
    }

    public HotelInfo(String place, String name, int price) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
