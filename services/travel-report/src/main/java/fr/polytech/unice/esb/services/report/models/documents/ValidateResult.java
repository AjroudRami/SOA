package fr.polytech.unice.esb.services.report.models.documents;

import java.io.Serializable;

public class ValidateResult implements Serializable {

    private String id;

    private String businessTravelId;

    private TravelReportStatus status;

    public ValidateResult(TravelReport travelReport){
        this.id = travelReport.getId();
        this.businessTravelId = travelReport.getBusinessTravelId();
        this.status = travelReport.getStatus();
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

    public void setBusinessTravelId(String employee_id) {
        this.businessTravelId = employee_id;
    }

    public TravelReportStatus getStatus() {
        return status;
    }

    public void setStatus(TravelReportStatus status) {
        this.status = status;
    }
}
