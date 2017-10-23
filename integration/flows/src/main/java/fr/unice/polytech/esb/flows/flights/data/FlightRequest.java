package fr.unice.polytech.esb.flows.flights.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class FlightRequest implements Serializable {

    @JsonProperty private String from;
    @JsonProperty private String to;
    @JsonProperty private Long departure;

    public FlightRequest(String from, String to, Long departure) {
        this.from = from;
        this.to = to;
        this.departure = departure;
    }

    public FlightRequest() {
        super();
    }

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

    public Long getDeparture() {
        return departure;
    }

    public void setDeparture(Long departure) {
        this.departure = departure;
    }
}
