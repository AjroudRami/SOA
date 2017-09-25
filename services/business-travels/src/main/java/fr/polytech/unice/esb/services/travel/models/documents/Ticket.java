package fr.polytech.unice.esb.services.travel.models.documents;

/**
 * A ticket
 */
public class Ticket {

    private String ticketNumber;
    private String departureAirport;
    private String arrivalAirport;
    private long departureTimestamp;
    private long arivalTimestamp;

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public long getDepartureTimestamp() {
        return departureTimestamp;
    }

    public void setDepartureTimestamp(long departureTimestamp) {
        this.departureTimestamp = departureTimestamp;
    }

    public long getArivalTimestamp() {
        return arivalTimestamp;
    }

    public void setArivalTimestamp(long arivalTimestamp) {
        this.arivalTimestamp = arivalTimestamp;
    }
}
