package fr.polytech.unice.esb.services.travel.actions.list;

import fr.polytech.unice.esb.services.travel.actions.DocumentAction;
import fr.polytech.unice.esb.services.travel.components.BusinessTravelComponent;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravels;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A submit action
 */
@Any
public class ApproveAction implements DocumentAction<BusinessTravel, BusinessTravel> {

    @EJB
    private BusinessTravelComponent travels;

    /**
     * Approves a business travel
     * @param document
     * @return the business travel
     */
    @Override
    public BusinessTravel execute(BusinessTravel document) {
        Optional<BusinessTravel> travelOpt = travels.get(document);
        if (travelOpt.isPresent()) {
            BusinessTravel travel = travelOpt.get();
            travel.setValidated(true);
            travels.put(travel);
            return travel;
        } else {
            return null;
        }
    }

    @Override
    public Class<BusinessTravel> getInputType() {
        return BusinessTravel.class;
    }
}
