package fr.polytech.unice.esb.services.report;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.ActionComponent;
import fr.polytech.unice.esb.services.report.components.ResponseComponent;
import fr.polytech.unice.esb.services.report.models.errors.ActionNotFound;
import fr.polytech.unice.esb.services.report.models.errors.InternalServerError;

import java.util.Map;
import java.util.Optional;

@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
public class Registry {

    @EJB
    private ResponseComponent responses;

    @EJB
    private ActionComponent actions;

    /**
     * We execute different actions according to the document event
     * @param input the document
     * @return The result of the action execution. Returns a 404 if the action is not found
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(Map<String, Object> input) {
        String event = (String) input.getOrDefault("event","");
        Optional<DocumentAction<?, ?>> action = actions.get(event);

        if (action.isPresent()) {
            try {
                return responses.reponse(Response.Status.OK, actions.execute(action.get(), input));
            } catch (Exception e) {
                return responses.reponse(Response.Status.INTERNAL_SERVER_ERROR, new InternalServerError(e.getMessage()));
            }
        } else {
            return responses.reponse(Response.Status.NOT_FOUND, new ActionNotFound(event));
        }
    }
}
