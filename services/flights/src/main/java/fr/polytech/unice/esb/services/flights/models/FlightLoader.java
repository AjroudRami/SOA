package fr.polytech.unice.esb.services.flights.models;

import fr.polytech.unice.esb.services.flights.models.documents.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlightLoader {

    private List<Flight> flights;

    public FlightLoader(){
        flights = new ArrayList<>();
    }

    public List<Flight> load(){
        List<Flight> flights = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            int departure = random.nextInt(1000);
            int arrival = random.nextInt(1000) + 1000;
            int ticketNo = random.nextInt(10000);
            int numberOfFlights = random.nextInt(3) + 1;
            String from = "Paris";
            String to = "New-York";
            String seatClass = "cosy";
            double price = random.nextDouble() * 400.0f + 800;
            Flight flight = new Flight(ticketNo, from, to, departure, arrival, seatClass, price, numberOfFlights);
            flights.add(flight);
        }
        return flights;
    }

    public List<Flight> getFlights(){
        return this.flights;
    }
}
