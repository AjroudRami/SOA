package fr.unice.polytech.esb.flows.cheapest.cars.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CarRequest {

    @JsonProperty("city")
    private String place;
    @JsonProperty("dateFrom")
    private Date from;
    @JsonProperty("dateTo")
    private Date to;

    public CarRequest() {
        super();
    }

    public CarRequest(String place, Date from, Date to) {
        this.place = place;
        this.from = from;
        this.to = to;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}