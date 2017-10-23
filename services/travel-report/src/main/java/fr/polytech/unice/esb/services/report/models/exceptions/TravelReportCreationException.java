package fr.polytech.unice.esb.services.report.models.exceptions;

public class TravelReportCreationException extends Exception {

    private String error;

    public TravelReportCreationException(){
        super("Information not complete");
        this.error = "Information not complete, Travel Report not created.";
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
