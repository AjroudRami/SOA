package fr.unice.polytech.esb.flows.data;


import java.io.Serializable;

public class FlightInformation implements Serializable {

    private String date;
    private String endingAirport;
    private float price;
    private String startingAirport;

    public FlightInformation(String date, String endingAirport,
                             float price, String startingAirport) {
        this.date = date;
        this.endingAirport = endingAirport;
        this.price = price;
        this.startingAirport = startingAirport;
    }

    public FlightInformation(FlightInformation origin){
        this.date = origin.date;
        this.endingAirport = origin.endingAirport;
        this.price = origin.price;
        this.startingAirport = origin.startingAirport;
    }

    public FlightInformation() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String value) {
        this.date = value;
    }

    public String getEndingAirport() {
        return endingAirport;
    }

    public void setEndingAirport(String value) {
        this.endingAirport = value;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float value) {
        this.price = value;
    }

    public String getStartingAirport() {
        return startingAirport;
    }

    public void setStartingAirport(String value) {
        this.startingAirport = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightInformation)) return false;

        FlightInformation that = (FlightInformation) o;

        if (Float.compare(that.getPrice(), getPrice()) != 0) return false;
        if (!getDate().equals(that.getDate())) return false;
        if (!getEndingAirport().equals(that.getEndingAirport())) return false;
        return getStartingAirport().equals(that.getStartingAirport());
    }

    @Override
    public int hashCode() {
        int result = getDate().hashCode();
        result = 31 * result + getEndingAirport().hashCode();
        result = 31 * result + (getPrice() != +0.0f ? Float.floatToIntBits(getPrice()) : 0);
        result = 31 * result + getStartingAirport().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FlightInformation{" +
                "date='" + date + '\'' +
                ", endingAirport='" + endingAirport + '\'' +
                ", price=" + price +
                ", startingAirport='" + startingAirport + '\'' +
                '}';
    }
}
