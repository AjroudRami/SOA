package fr.polytech.unice.esb.services.travel.components;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

/**
 * Implementation of our response component interface
 */
@Stateless
public class ResponseComponentImpl implements ResponseComponent {

    @Override
    public Response reponse(Response.Status code, Object response) {
        return Response.status(code).entity(response).build();
    }
}
