package fr.polytech.unice.esb.services.travel.actions.list;

import fr.polytech.unice.esb.services.travel.actions.DocumentAction;
import fr.polytech.unice.esb.services.travel.components.BusinessTravelComponent;
import fr.polytech.unice.esb.services.travel.components.NotificationComponent;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravelEmail;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.Optional;

/**
 * A submit action
 */
@Any
public class SendAction implements DocumentAction<BusinessTravelEmail, Void> {

    @EJB
    private NotificationComponent notifications;

    @EJB
    private BusinessTravelComponent travels;

    /**
     * Approves a business travel
     * @param document
     * @return the business travel
     */
    @Override
    public Void execute(BusinessTravelEmail document) {
        Optional<BusinessTravel> travel = travels.get(document.getContent());
        if (travel.isPresent()) {
            notifications.send(document.getRecipient(), travel.get());
        }
        return null;
    }

    @Override
    public Class<BusinessTravelEmail> getInputType() {
        return BusinessTravelEmail.class;
    }
}
