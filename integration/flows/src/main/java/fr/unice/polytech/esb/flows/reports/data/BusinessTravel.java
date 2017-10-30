package fr.unice.polytech.esb.flows.reports.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BusinessTravel {

    @JsonProperty private String id;
    @JsonProperty private List<Ticket> tickets;

    @JsonProperty("nights")
    private List<Hotel> hotel;
    @JsonProperty private boolean isValidated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Hotel> getHotel() {
        return hotel;
    }

    public void setHotel(List<Hotel> hotel) {
        this.hotel = hotel;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }
}
