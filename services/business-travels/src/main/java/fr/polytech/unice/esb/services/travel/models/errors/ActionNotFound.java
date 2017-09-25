package fr.polytech.unice.esb.services.travel.models.errors;

/**
 * Action not found error
 */
public class ActionNotFound extends Error {

    public ActionNotFound(String action) {
        super("Action " + action + " not found");
    }
}
