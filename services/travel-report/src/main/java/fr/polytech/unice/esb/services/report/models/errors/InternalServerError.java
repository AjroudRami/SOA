package fr.polytech.unice.esb.services.report.models.errors;

import java.lang.Error;

/**
 * Action not found error
 */
public class InternalServerError extends Error {

    public InternalServerError(String error) {
        super("Server error: " + error);
    }
}
