package fr.polytech.unice.esb.services.report.actions.list;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.TravelReportComponent;
import fr.polytech.unice.esb.services.report.models.documents.TravelReport;

import javax.ejb.EJB;
import java.util.Optional;

public class AddExplainationAction implements DocumentAction<TravelReport, TravelReport> {

    @EJB
    private TravelReportComponent travels;

    @Override
    public TravelReport execute(TravelReport document) throws Exception {
        Optional<TravelReport> travelOpt = travels.get(document);
        if (travelOpt.isPresent()) {
            TravelReport travel = travelOpt.get();
            travel.setExplaination(document.getExplaination());
            travels.put(travel);
            return travel;
        } else {
            return null;
        }
    }

    @Override
    public Class<TravelReport> getInputType() {
        return TravelReport.class;
    }
}
