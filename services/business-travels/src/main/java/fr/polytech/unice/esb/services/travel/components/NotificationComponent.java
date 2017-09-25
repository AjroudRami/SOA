package fr.polytech.unice.esb.services.travel.components;

import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;

/**
 * Notification component
 */
public interface NotificationComponent {

    /**
     * Send a business travel to someone
     * @param email
     * @param travel
     */
    void send(String email, BusinessTravel travel);
}
