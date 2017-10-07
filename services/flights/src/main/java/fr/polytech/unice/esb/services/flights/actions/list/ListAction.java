package fr.polytech.unice.esb.services.flights.actions.list;

import fr.polytech.unice.esb.services.flights.actions.DocumentAction;
import fr.polytech.unice.esb.services.flights.components.FlightComponent;
import fr.polytech.unice.esb.services.flights.models.documents.Flight;
import fr.polytech.unice.esb.services.flights.models.documents.FlightList;
import fr.polytech.unice.esb.services.flights.models.requests.ListRequest;
import fr.polytech.unice.esb.services.flights.models.requests.Filter;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.ArrayList;
import java.util.List;

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
        flights = userFilter(document.filterBy, flights);
        flights = filterflights(document.departureTimeStamp,
                document.departure, document.destination, flights);
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


    private List<Flight> userFilter(Filter[] filters, List<Flight> flights) {
        if (filters == null || filters.length == 0) {
            return flights;
        }
        List<Flight> filteredFlights = new ArrayList<>();
        if (flights == null || filters == null) {
            return flights;
        }
        //TODO improve performances
        for(Filter filter : filters) {
            if("direct".equals(filter.getName())) {
                for (Flight fl : flights) {
                    if (fl.getNumberOfFlights() == 1) {
                        filteredFlights.add(fl);
                    }
                }
            }
        }
        return filteredFlights;
    }

    private List<Flight> filterflights(int depTS, String destination, String departure, List<Flight> flights) {
        List<Flight> filteredFlights = new ArrayList<>();
        if (flights == null) {
            return flights;
        }
        if (departure == null || destination == null) {
            throw new RuntimeException("Missing fields, destination or departure");
        }
        if (depTS == 0) {
            throw new RuntimeException("Missing or Invalid fields: departureTimeStamp, arrivalTimeStamp");
        }
        //TODO improve performances
        for(Flight flight : flights) {
            if(flight.getFrom().equals(departure) && flight.getTo().equals(destination)) {
                if ((flight.getDeparture() / 10000) == (depTS /10000)) {
                    filteredFlights.add(flight);
                }
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
