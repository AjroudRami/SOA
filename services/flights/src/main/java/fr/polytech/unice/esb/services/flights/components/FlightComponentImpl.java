package fr.polytech.unice.esb.services.flights.components;

import fr.polytech.unice.esb.services.flights.models.documents.Flight;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class FlightComponentImpl implements FlightComponent {

    private List<Flight> flights;

    public FlightComponentImpl() {
        flights = new ArrayList<>();
        Flight fl1 = new Flight();
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
