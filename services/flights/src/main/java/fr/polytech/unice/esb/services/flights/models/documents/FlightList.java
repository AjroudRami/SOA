package fr.polytech.unice.esb.services.flights.models.documents;

import java.util.List;

public class FlightList {
    List<Flight> flights;

    public FlightList(List<Flight> flights){
        this.flights = flights;
    }
    public List<Flight> getFlights() {
        return flights;
    }
}
