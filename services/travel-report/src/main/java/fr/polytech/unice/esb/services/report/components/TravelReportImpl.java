package fr.polytech.unice.esb.services.report.components;

import fr.polytech.unice.esb.services.report.models.documents.TravelReport;
import fr.polytech.unice.esb.services.report.models.documents.TravelReportStatus;

import javax.ejb.Singleton;
import java.util.*;

@Singleton
public class TravelReportImpl implements TravelReportComponent{

    private Map<String, TravelReport> travels;

    public TravelReportImpl() {
        travels = new HashMap<>();
    }

    @Override
    public TravelReport put(TravelReport travel) {
        travels.put(travel.getId(), travel);
        return travel;
    }

    @Override
    public List<TravelReport> list() {
        return new ArrayList<>(travels.values());
    }

    @Override
    public Optional<TravelReport> get(TravelReport travel) {
        return Optional.ofNullable(travels.get(travel.getId()));
    }
}
