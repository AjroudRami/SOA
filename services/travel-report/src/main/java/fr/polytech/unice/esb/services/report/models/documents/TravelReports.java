package fr.polytech.unice.esb.services.report.models.documents;

import java.util.List;

public class TravelReports {
    private final List<TravelReport> travels;

    public TravelReports(List<TravelReport> travels) {
        this.travels = travels;
    }

    public List<TravelReport> getTravels() {
        return travels;
    }
}
