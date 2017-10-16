package fr.unice.polytech.esb.flows.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class Flight implements Serializable {

    private String from;
    private String to;
    private Date departure;
    private Date arrival;
    private double price;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;

        Flight flight = (Flight) o;

        if (Double.compare(flight.getPrice(), getPrice()) != 0) return false;
        if (!getFrom().equals(flight.getFrom())) return false;
        if (!getTo().equals(flight.getTo())) return false;
        if (!getDeparture().equals(flight.getDeparture())) return false;
        return getArrival().equals(flight.getArrival());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getFrom().hashCode();
        result = 31 * result + getTo().hashCode();
        result = 31 * result + getDeparture().hashCode();
        result = 31 * result + getArrival().hashCode();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
