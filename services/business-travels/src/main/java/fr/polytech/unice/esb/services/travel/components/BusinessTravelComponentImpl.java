package fr.polytech.unice.esb.services.travel.components;

import fr.polytech.unice.esb.services.travel.models.documents.BusinessTravel;

import javax.ejb.Singleton;
import java.util.*;

@Singleton // For now we will store our business travel in a map
public class BusinessTravelComponentImpl implements BusinessTravelComponent {

    private Map<String, BusinessTravel> travels;

    public BusinessTravelComponentImpl() {
        travels = new HashMap<>();
    }

    @Override
    public BusinessTravel put(BusinessTravel travel) {
        travel.setId(UUID.randomUUID().toString());
        travels.put(travel.getId(), travel);
        return travel;
    }

    @Override
    public List<BusinessTravel> list() {
        return new ArrayList<>(travels.values());
    }

    @Override
    public Optional<BusinessTravel> get(BusinessTravel travel) {
        return Optional.ofNullable(travels.get(travel.getId()));
    }
}
