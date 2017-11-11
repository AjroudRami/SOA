package fr.unice.polytech.esb.flows.cheapest.hotels.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelRequest {

    @JsonProperty private String destination;
    @JsonProperty private long timestamp;

    public HotelRequest() {
    }

    public HotelRequest(String destination, long timestamp) {
        this.destination = destination;
        this.timestamp = timestamp;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
