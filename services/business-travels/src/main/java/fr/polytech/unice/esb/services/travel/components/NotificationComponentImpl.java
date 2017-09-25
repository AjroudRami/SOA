package fr.polytech.unice.esb.services.travel.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;

import javax.ejb.Stateless;

/**
 * Notification component implementation
 */
@Stateless
public class NotificationComponentImpl implements NotificationComponent {

    private ObjectMapper mapper;

    public NotificationComponentImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public void send(String email, BusinessTravel travel) {
        try {
            System.out.println("Sending an email to " + email + " with content: " + mapper.writeValueAsString(travel));
        } catch (JsonProcessingException e) {
            // TODO: add logger
            e.printStackTrace();
        }
    }
}
