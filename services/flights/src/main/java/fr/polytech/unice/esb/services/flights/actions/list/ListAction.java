package fr.polytech.unice.esb.services.flights.actions.list;

import fr.polytech.unice.esb.services.flights.actions.DocumentAction;
import fr.polytech.unice.esb.services.flights.components.FlightComponent;
import fr.polytech.unice.esb.services.flights.models.documents.FlightList;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.ArrayList;

/**
 * A submit action
 */
@Any
public class ListAction implements DocumentAction<Void, FlightList> {

    @EJB
    private FlightComponent travels;

    private static final String name = "list";

    /**
     * List the different non-approved business travels
     * @param document
     * @return the list of the business travels
     */
    @Override
    public FlightList execute(Void document) {
        //return new BusinessTravels(travels.list().stream().filter(travel -> !travel.isValidated()).collect(Collectors.toList()));
        return new FlightList(new ArrayList<>());
    }

    @Override
    public Class<Void> getInputType() {
        return Void.class;
    }

    @Override
    public String getActionName(){
        return this.name;
    }
}
