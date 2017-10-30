package fr.polytech.unice.esb.services.report.actions.list;

import fr.polytech.unice.esb.services.report.actions.DocumentAction;
import fr.polytech.unice.esb.services.report.components.TravelReportComponent;
import fr.polytech.unice.esb.services.report.models.documents.Expense;
import fr.polytech.unice.esb.services.report.models.documents.TravelReport;

import javax.ejb.EJB;
import java.util.List;
import java.util.Optional;

public class AddExpenses implements DocumentAction<TravelReport, TravelReport> {

    @EJB
    private TravelReportComponent travels;

    @Override
    public TravelReport execute(TravelReport document) throws Exception {
        Optional<TravelReport> travelOpt = travels.get(document);
        if (travelOpt.isPresent()) {
            TravelReport travel = travelOpt.get();
            Optional<List<Expense>> expenses = Optional.ofNullable(travel.getExpenses());
            if (expenses.isPresent()) {
                travel.getExpenses().addAll(document.getExpenses());
            }else {
                travel.setExpenses(document.getExpenses());
            }
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
