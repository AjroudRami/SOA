package fr.polytech.unice.esb.services.travel.models.exceptions;

/**
 * Business travel not found
 */
public class BusinessTravelNotFound extends Exception {

    public BusinessTravelNotFound(String id) {
        super("Business travel of id " + id + " not found");
    }
}
