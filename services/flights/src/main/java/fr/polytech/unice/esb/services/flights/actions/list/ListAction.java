package fr.polytech.unice.esb.services.flights.actions.list;

import fr.polytech.unice.esb.services.flights.actions.DocumentAction;
import fr.polytech.unice.esb.services.flights.components.FlightComponent;
import fr.polytech.unice.esb.services.flights.models.documents.Flight;
import fr.polytech.unice.esb.services.flights.models.documents.FlightList;
import fr.polytech.unice.esb.services.flights.models.requests.ListRequest;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A submit action
 */
@Any
public class ListAction implements DocumentAction<ListRequest, FlightList> {

    @EJB
    private FlightComponent flightComponent;

    private static final String name = "list";

    /**
     * @param document
     * @return the list of the filtered and sorted flights
     */
    @Override
    public FlightList execute(ListRequest document) {
        List<Flight> flights = flightComponent.getFlights();
        flights = filter(document.filterBy, flights);
        sort(document.orderBy, flights);
        return new FlightList(flights);
    }

    @Override
    public Class<ListRequest> getInputType() {
        return ListRequest.class;
    }

    @Override
    public String getActionName(){
        return this.name;
    }


    private List<Flight> filter(String[] filters, List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>();
        if (flights == null || filters == null) {
            return flights;
        }
        //TODO improve performances
        for(String filter : filters) {
            if("direct".equals(filter)) {
                for (Flight fl : flights) {
                    if (fl.getNumberOfFlights() == 1) {
                        filteredFlights.add(fl);
                    }
                }
            }
        }
        return filteredFlights;
    }

    private List<Flight> filterflights(String destination, String departure, List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>();
        if (flights == null) {
            return flights;
        }
        if (departure == null || destination == null) {
            throw new RuntimeException("Missing fields, destination or departure");
        }
        //TODO improve performances
        for(Flight flight : flights) {
            if(flight.getFrom().equals(departure) && flight.getTo().equals(destination)) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }

    private void sort(String sortBy, List<Flight> flights){
        if (flights == null || sortBy == null) {
            return;
        }
        if("duration".equals(sortBy)) {
            flights.sort(Flight.DURATION_COMPARATOR);
        } else {
            flights.sort(Flight.PRICE_COMPARATOR);
        }
    }
}
