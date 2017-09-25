package fr.polytech.unice.esb.services.travel.models.errors;

/**
 * An error
 */
public class Error {

    private final String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
