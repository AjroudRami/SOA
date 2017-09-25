package business;

/**
 * Created by danial
 */
public class Car {

    private String brand;
    private String model;
    private String place;
    private int rentPricePerDay;

    public Car(String brand, String model, String place, int rentPricePerDay) {
        this.brand = brand;
        this.model = model;
        this.place = place;
        this.rentPricePerDay = rentPricePerDay;
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
}
