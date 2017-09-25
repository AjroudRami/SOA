package fr.polytech.unice.esb.services.travel.components;

import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;

import java.util.List;

/**
 * A component that handles business travels
 */
public interface BusinessTravelComponent {

    /**
     * Creates a business travel
     * @param travel
     * @return
     */
    BusinessTravel create(BusinessTravel travel);

    /**
     * List the different business travels
     * @return
     */
    List<BusinessTravel> list();
}
