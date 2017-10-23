package fr.polytech.unice.esb.services.report.actions.list;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.TravelReportComponent;
import fr.polytech.unice.esb.services.report.models.documents.TravelReport;
import fr.polytech.unice.esb.services.report.models.documents.TravelReportStatus;
import fr.polytech.unice.esb.services.report.models.documents.ValidateResult;

import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import java.util.Optional;

@Any
public class ValidateAction implements DocumentAction<TravelReport, ValidateResult>{


    @EJB
    private TravelReportComponent travels;

    @Override
    public ValidateResult execute(TravelReport document) throws Exception {
        Optional<TravelReport> travelOpt = travels.get(document);
        if (travelOpt.isPresent()) {
            TravelReport travel = travelOpt.get();
            travel.setStatus(TravelReportStatus.ACCEPTED);
            travels.put(travel);
            return new ValidateResult(travel);
        } else {
            return null;
        }
    }

    @Override
    public Class<TravelReport> getInputType() {
        return TravelReport.class;
    }
}
