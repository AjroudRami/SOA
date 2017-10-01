package fr.polytech.unice.esb.services.flights.components;

import fr.polytech.unice.esb.services.flights.models.documents.Flight;

import java.util.List;

public interface FlightComponent {
    public List<Flight> getFlights();
}
