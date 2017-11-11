package fr.polytech.unice.esb.services.report.components;

import fr.polytech.unice.esb.services.report.models.documents.Expense;
import fr.polytech.unice.esb.services.report.models.documents.TravelReport;
import fr.polytech.unice.esb.services.report.models.documents.TravelReportStatus;

import javax.ejb.Singleton;
import java.util.*;

@Singleton
public class TravelReportImpl implements TravelReportComponent{

    private static final int TOTAL_EXPENSE_LIMIT = 1000;

    private Map<String, TravelReport> travels;

    public TravelReportImpl() {
        travels = new HashMap<>();
    }

    @Override
    public TravelReport put(TravelReport travel) {
        Optional<List<Expense>> expenses = Optional.ofNullable(travel.getExpenses());
        double amount = 0.0;
        if (expenses.isPresent()){
            for (Expense expense : expenses.get()){
                amount += expense.getAmount();
            }
        }
        travel.setTotalAmount(amount);
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

    @Override
    public boolean validateTotalExpense(TravelReport travelReport) {
        return TOTAL_EXPENSE_LIMIT > travelReport.getTotalAmount();
    }
}
