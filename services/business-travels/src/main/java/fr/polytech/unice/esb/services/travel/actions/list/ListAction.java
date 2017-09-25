package fr.polytech.unice.esb.services.travel.actions.list;

import fr.polytech.unice.esb.services.travel.actions.DocumentAction;
import fr.polytech.unice.esb.services.travel.components.BusinessTravelComponent;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravels;
import fr.polytech.unice.esb.services.travel.models.documents.SubmissionResult;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.stream.Collectors;

/**
 * A submit action
 */
@Any
public class ListAction implements DocumentAction<Void, BusinessTravels> {

    @EJB
    private BusinessTravelComponent travels;

    /**
     * List the different non-approved business travels
     * @param document
     * @return the list of the business travels
     */
    @Override
    public BusinessTravels execute(Void document) {
        return new BusinessTravels(travels.list().stream().filter(travel -> !travel.isValidated()).collect(Collectors.toList()));
    }

    @Override
    public Class<Void> getInputType() {
        return Void.class;
    }
}
