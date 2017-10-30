package fr.unice.polytech.esb.flows.reports.data;

public class TravelExpense {

    private String date;
    private double amount;
    private String description;

    public TravelExpense() {
        // empty ctr
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
