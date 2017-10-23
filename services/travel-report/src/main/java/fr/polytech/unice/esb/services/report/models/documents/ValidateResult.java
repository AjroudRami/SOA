package fr.polytech.unice.esb.services.report.models.documents;

import java.io.Serializable;

public class ValidateResult implements Serializable {

    private String id;

    private String employee_id;

    private TravelReportStatus status;

    public ValidateResult(TravelReport travelReport){
        this.id = travelReport.getId();
        this.employee_id = travelReport.getEmployee_id();
        this.status = travelReport.getStatus();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public TravelReportStatus getStatus() {
        return status;
    }

    public void setStatus(TravelReportStatus status) {
        this.status = status;
    }
}
