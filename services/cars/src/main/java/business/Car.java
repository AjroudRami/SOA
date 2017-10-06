package business;

/**
 * Created by danial
 */
public class Car {

    private String brand;
    private String model;
    private String place;
    private int rentPricePerDay;
    private int availability;

    public Car(){
        this.brand = "";
        this.model = "";
        this.place = "";
        this.rentPricePerDay = 0;
        this.availability = 0;
    }

    public Car(String brand, String model, String place, int rentPricePerDay, int availability) {
        this.brand = brand;
        this.model = model;
        this.place = place;
        this.rentPricePerDay = rentPricePerDay;
        this.availability = availability;
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

    public int getRentPricePerDay() {
        return rentPricePerDay;
    }

    public void setRentPricePerDay(int rentPricePerDay) {
        this.rentPricePerDay = rentPricePerDay;
    }

    public int isAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
