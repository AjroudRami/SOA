package fr.polytech.unice.esb.services.travel.actions.list;

import fr.polytech.unice.esb.services.travel.actions.DocumentAction;
import fr.polytech.unice.esb.services.travel.components.BusinessTravelComponent;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;
import fr.polytech.unice.esb.services.travel.models.documents.SubmissionResult;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;

/**
 * A submit action
 */
@Any
public class SubmitAction implements DocumentAction<BusinessTravel, SubmissionResult> {

    @EJB
    private BusinessTravelComponent travels;

    /**
     * Executes a document submission
     * @param document
     * @return the result of the submission
     */
    @Override
    public SubmissionResult execute(BusinessTravel document) {
        BusinessTravel created = travels.create(document);
        return new SubmissionResult(created.getId());
    }

    @Override
    public Class<BusinessTravel> getInputType() {
        return BusinessTravel.class;
    }
}
