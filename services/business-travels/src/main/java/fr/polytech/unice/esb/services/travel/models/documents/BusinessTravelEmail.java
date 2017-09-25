package fr.polytech.unice.esb.services.travel.models.documents;

public class BusinessTravelEmail extends Email<BusinessTravel> {

    public BusinessTravelEmail() {
        // empty ctr
    }

    public BusinessTravelEmail(String recipient, BusinessTravel content) {
        super(recipient, content);
    }
}
