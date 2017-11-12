package fr.polytech.unice.esb.services.report.actions.list;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.TravelReportComponent;
import fr.polytech.unice.esb.services.report.models.documents.TravelReport;
import fr.polytech.unice.esb.services.report.models.documents.TravelReportStatus;

import javax.ejb.EJB;
import java.util.Optional;

public class AutoValidationAction implements DocumentAction<TravelReport, TravelReport> {

    // TODO apply the taux chancellerie
    private static final int MAX_EXPENSES_PER_DAY = 300;

    @EJB
    private TravelReportComponent travels;

    @Override
    public TravelReport execute(TravelReport document) throws Exception {
        Optional<TravelReport> travelOpt = travels.get(document);
        if (travelOpt.isPresent()) {
            TravelReport travel = travelOpt.get();
            if (travel.getStatus() == TravelReportStatus.FINISH) {
                setNewStatus(travel);
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

    private void setNewStatus(TravelReport travelReport){
        if (validateTotalExpenses(travelReport)){
            travelReport.setStatus(TravelReportStatus.ACCEPTED);
        }
        else {
            travelReport.setStatus(TravelReportStatus.REJECTED);
        }
    }

    private boolean validateTotalExpenses(TravelReport travelReport){

        long diff = travelReport.getStart().getTime() - travelReport.getFinish().getTime();

        //diff in days
        long days = diff / (24 * 60 * 60 * 1000);

        if (days == 0){
            return MAX_EXPENSES_PER_DAY > travelReport.getTotalAmount();
        }

        return (MAX_EXPENSES_PER_DAY * days) > travelReport.getTotalAmount();
    }
}
