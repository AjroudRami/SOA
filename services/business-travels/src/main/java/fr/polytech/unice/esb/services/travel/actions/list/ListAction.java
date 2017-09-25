package fr.polytech.unice.esb.services.travel.actions.list;

import fr.polytech.unice.esb.services.travel.actions.DocumentAction;
import fr.polytech.unice.esb.services.travel.components.BusinessTravelComponent;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravels;
import fr.polytech.unice.esb.services.travel.models.documents.SubmissionResult;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;

/**
 * A submit action
 */
@Any
public class ListAction implements DocumentAction<Void, BusinessTravels> {

    @EJB
    private BusinessTravelComponent travels;

    /**
     * Executes a document submission
     * @param document
     * @return the result of the submission
     */
    @Override
    public BusinessTravels execute(Void document) {
        return new BusinessTravels(travels.list());
    }

    @Override
    public Class<Void> getInputType() {
        return Void.class;
    }
}
