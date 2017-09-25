package fr.polytech.unice.esb.services.travel.components;

import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;

import java.util.List;
import java.util.Optional;

/**
 * A component that handles business travels
 */
public interface BusinessTravelComponent {

    /**
     * Creates a business travel
     * @param travel
     * @return
     */
    BusinessTravel put(BusinessTravel travel);

    /**
     * List the different business travels
     * @return
     */
    List<BusinessTravel> list();

    /**
     *
     * @param travel
     * @return the most recent business travel document
     */
    Optional<BusinessTravel> get(BusinessTravel travel);

}
