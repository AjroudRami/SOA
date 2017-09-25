package fr.polytech.unice.esb.services.travel.models.documents;

/**
 * A submission result
 */
public class SubmissionResult {

    private String submissionId;

    public SubmissionResult(String submissionId) {
        this.submissionId = submissionId;
    }

    public void setSubmissionId(String submissionId) {

        this.submissionId = submissionId;
    }

    public String getSubmissionId() {

        return submissionId;
    }
}
