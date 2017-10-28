package fr.unice.polytech.esb.flows.hotels.data;

public class HotelInformation {

    private String place;
    private String name;
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
