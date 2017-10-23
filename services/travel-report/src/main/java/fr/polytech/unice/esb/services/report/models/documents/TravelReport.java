package fr.polytech.unice.esb.services.report.models.documents;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TravelReport implements Serializable{

    private String id;
    private String employee_id;
    private Date start;
    private Date finish;
    private List<Expense> expenses;
    private Double totalAmount;
    private String explaination;
    private TravelReportStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getExplaination() {
        return explaination;
    }

    public void setExplaination(String explaination) {
        this.explaination = explaination;
    }

    public TravelReportStatus getStatus() {
        return status;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public void setStatus(TravelReportStatus status) {
        this.status = status;
    }
}
