package fr.polytech.unice.esb.services.flights.components;

import fr.polytech.unice.esb.services.flights.models.FlightLoader;
import fr.polytech.unice.esb.services.flights.models.documents.Flight;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Singleton
public class FlightComponentImpl implements FlightComponent {

    private List<Flight> flights;

    public FlightComponentImpl() {
        flights = load();
    }

    @Override
    public List<Flight> getFlights() {
        return flights;
    }

    public List<Flight> load(){
        return FlightLoader.getFlights();
    }
}
