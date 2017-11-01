package fr.polytech.unice.esb.services.report.actions.list;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.TravelReportComponent;
import fr.polytech.unice.esb.services.report.models.documents.TravelReport;
import fr.polytech.unice.esb.services.report.models.documents.TravelReportStatus;
import fr.polytech.unice.esb.services.report.models.documents.ValidateResult;

import javax.ejb.EJB;
import java.util.Date;
import java.util.Optional;

public class EndAction implements DocumentAction<TravelReport, TravelReport> {

    @EJB
    private TravelReportComponent travels;

    @Override
    public TravelReport execute(TravelReport document) throws Exception {
        Optional<TravelReport> travelOpt = travels.get(document);
        if (travelOpt.isPresent()) {
            TravelReport travel = travelOpt.get();
            if (travel.getStatus() == TravelReportStatus.INPROGRESS) {
                travel.setStatus(TravelReportStatus.FINISH);
                travel.setFinish(new Date());
                travels.put(travel);
                return travel;
            }
        }
        return null;
    }

    @Override
    public Class<TravelReport> getInputType() {
        return TravelReport.class;
    }
}
