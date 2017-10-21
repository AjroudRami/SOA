package fr.polytech.unice.esb.services.report.components;

import javax.ws.rs.core.Response;

/**
 * Component that handles service responses
 */
public interface ResponseComponent {

    /**
     * Get a reponse object from a given code and reponse object
     * @param code
     * @param response
     * @return the built reponse
     */
    Response reponse(Response.Status code, Object response);
}
