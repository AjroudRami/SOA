package fr.polytech.unice.esb.services.travel.models.documents;

import java.util.List;

/**
 * A business travel submission
 */
public class BusinessTravel {

    private String id;
    private List<Ticket> tickets;
    private List<HotelNights> nights;
    private boolean isValidated;

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

    public List<HotelNights> getNights() {
        return nights;
    }

    public void setNights(List<HotelNights> nights) {
        this.nights = nights;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }
}
