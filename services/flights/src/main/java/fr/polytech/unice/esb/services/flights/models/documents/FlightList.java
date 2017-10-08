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

    @Override
    public String toString(){
        String res = "";
        if (flights != null ) {
            for(Flight fl : flights) {
                res += flights.toString();
            }
        }
        return res;
    }
}
