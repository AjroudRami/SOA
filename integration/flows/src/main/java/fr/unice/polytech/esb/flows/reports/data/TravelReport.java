package fr.unice.polytech.esb.flows.reports.data;

import java.util.List;

public class TravelReport {

    private String id;
    private String businessTravelId;
    private List<TravelExpense> expenses;

    public TravelReport() {
        // empty ctr
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessTravelId() {
        return businessTravelId;
    }

    public void setBusinessTravelId(String businessTravelId) {
        this.businessTravelId = businessTravelId;
    }

    public List<TravelExpense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<TravelExpense> expenses) {
        this.expenses = expenses;
    }
}
