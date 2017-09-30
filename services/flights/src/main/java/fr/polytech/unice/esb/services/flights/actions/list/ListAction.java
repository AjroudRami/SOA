package fr.polytech.unice.esb.services.flights.actions.list;

import fr.polytech.unice.esb.services.flights.actions.DocumentAction;
import fr.polytech.unice.esb.services.flights.components.FlightComponent;
import fr.polytech.unice.esb.services.flights.models.documents.Flight;
import fr.polytech.unice.esb.services.flights.models.requests.ListRequest;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.ArrayList;
import java.util.List;

/**
 * A submit action
 */
@Any
public class ListAction implements DocumentAction<ListRequest, List<Flight>> {

    @EJB
    private FlightComponent travels;

    private static final String name = "list";

    /**
     * List the different non-approved business travels
     * @param document
     * @return the list of the business travels
     */
    @Override
    public List<Flight> execute(ListRequest document) {
        return new ArrayList<>();
    }

    @Override
    public Class<ListRequest> getInputType() {
        return ListRequest.class;
    }

    @Override
    public String getActionName(){
        return this.name;
    }
}
