package fr.polytech.unice.esb.services.travel.models.documents;

import java.util.List;

/**
 * A list of the different business travels
 */
public class BusinessTravels {

    private final List<BusinessTravel> travels;

    public BusinessTravels(List<BusinessTravel> travels) {
        this.travels = travels;
    }

    public List<BusinessTravel> getTravels() {
        return travels;
    }
}
