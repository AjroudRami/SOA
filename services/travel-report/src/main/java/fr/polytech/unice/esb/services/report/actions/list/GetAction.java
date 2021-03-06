package fr.polytech.unice.esb.services.report.actions.list;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.TravelReportComponent;
import fr.polytech.unice.esb.services.report.models.documents.TravelReport;
import fr.polytech.unice.esb.services.report.models.documents.TravelReportStatus;

import javax.ejb.EJB;
import java.util.Date;
import java.util.Optional;

public class GetAction implements DocumentAction<TravelReport,TravelReport> {

    @EJB
    private TravelReportComponent travels;

    @Override
    public TravelReport execute(TravelReport document) throws Exception {
        Optional<TravelReport> travelOpt = travels.get(document);
        return travelOpt.orElse(null);
    }

    @Override
    public Class<TravelReport> getInputType() {
        return null;
    }
}
