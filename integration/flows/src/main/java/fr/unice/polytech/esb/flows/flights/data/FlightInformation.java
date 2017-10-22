package fr.unice.polytech.esb.flows.flights.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FlightInformation implements Serializable {

    @JsonProperty("departure") private long departureTimestamp;
    @JsonProperty private float price;
    @JsonProperty("from") private String startingAirport;
    @JsonProperty("to") private String endingAirport;

    public FlightInformation(long departureTimestamp, String endingAirport,
                             float price, String startingAirport) {
        this.departureTimestamp = departureTimestamp;
        this.endingAirport = endingAirport;
        this.price = price;
        this.startingAirport = startingAirport;
    }

    public FlightInformation(FlightInformation origin){
        this.departureTimestamp = origin.departureTimestamp;
        this.endingAirport = origin.endingAirport;
        this.price = origin.price;
        this.startingAirport = origin.startingAirport;
    }

    public FlightInformation() {
    }

    public long getDepartureTimestamp() {
        return departureTimestamp;
    }

    public void setDepartureTimestamp(long value) {
        this.departureTimestamp = value;
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
    public String toString() {
        return "FlightInformation{" +
                "departureTimestamp='" + departureTimestamp + '\'' +
                ", endingAirport='" + endingAirport + '\'' +
                ", price=" + price +
                ", startingAirport='" + startingAirport + '\'' +
                '}';
    }
}
