package fr.polytech.unice.esb.services.report.models.errors;

import java.lang.Error;

/**
 * Action not found error
 */
public class ActionNotFound extends Error {

    public ActionNotFound(String action) {
        super("Action " + action + " not found");
    }
}
