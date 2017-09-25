package fr.polytech.unice.esb.services.travel.models.errors;

/**
 * Action not found error
 */
public class InternalServerError extends Error {

    public InternalServerError(String error) {
        super("Server error: " + error);
    }
}
