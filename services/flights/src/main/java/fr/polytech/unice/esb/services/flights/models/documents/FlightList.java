package fr.polytech.unice.esb.services.flights.models.documents;

import java.util.List;

public class FlightList {

    private List<Flight> flightList;

    public FlightList(List<Flight> flights) {
        this.flightList = flights;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }
}
