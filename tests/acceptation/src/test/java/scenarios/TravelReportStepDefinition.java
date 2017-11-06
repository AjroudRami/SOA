package scenarios;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class TravelReportStepDefinition {

    private String host = "host";
    private int port = 8080;

    private JSONObject travelReport;
    private JSONObject answer;
    private String travelId;
    private boolean returnedProperly;

    /**
     *
     * @return an empty travel report
     */
    private JSONObject newTravelReport(String businessId) {
        JSONObject travel = new JSONObject();
        travel.put("businessTravelId",businessId);
        travel.put("expenses", new JSONArray());
        return travel;
    }

    /**
     * Initiates the travel report service endpoint
     * @param host
     * @param port
     */
    @Given("^a travel report service deployed on (.*):(\\d+)$")
    public void setupAddress(String host, int port) {
        this.host = host;
        this.port = port;
        this.travelReport = newTravelReport("");
    }

    /**
     * Initiates a new travel report
     */
    @Given("^a new travel report for (.+)$")
    public void initTravelReport(String businessId) {
        this.travelReport = newTravelReport(businessId);
    }


    /**
     * Add expenses to current travel report
     * @param cost
     * @param date
     * @param description
     * @throws ParseException
     */
    @When("^the employee add an expense of ([0-9]+) on ([0-9-]+) for (.+)$")
    public void withExpenses(Double cost, String date, String description) throws ParseException {
        JSONArray array = new JSONArray();
        array.put(newExpenses(cost, date,description));
        travelReport.put("expenses",array);
        JSONObject request = new JSONObject(travelReport.toString());;
        request.put("event", "expenses");
        answer = sendRequest(request);
    }

    private JSONObject newExpenses(Double cost, String date, String description){
        JSONObject object = new JSONObject();
        object.put("date",date);
        object.put("amount",cost);
        object.put("description",description);
        return object;
    }

    /**
     * List the current travel report
     */
    @When("^the travel reports are listed$")
    public void theTravelReportsAreListed() {
        JSONObject request = new JSONObject();
        request.put("event", "list");
        answer = sendRequest(request);
    }

    /**
     * assert that an id has been received in the service reply
     */
    @Then("^the list (contains|not contain) the last travel report$")
    public void travelReportsShouldContain(String containedFlag) {
        JSONArray travels = answer.getJSONArray("travels");
        boolean contained = false;
        for (int i = 0; i < travels.length(); i++) {
            JSONObject object = travels.getJSONObject(i);

            if (object.similar(travelReport)) {
                contained = true;
                break;
            }
        }
        if (containedFlag.equals("contains") != contained) {
            fail("Travel report should be '" + containedFlag + "'");
        }
    }

    /**
     * Send the current travel report
     */
    @When("^the travel report is sent$")
    public void theTravelReportIsSent() {
        JSONObject request = new JSONObject(travelReport.toString());
        request.put("event", "create");
        travelReport = sendRequest(request);
    }

    @When("^the employee ends the travel report")
    public void endTravelReport(){
        JSONObject request = new JSONObject(travelReport.toString());
        request.put("event", "end");
        travelReport = sendRequest(request);
    }

    @Then("^the status should change to FINISH")
    public void travelReportEndStatus(){
        assertEquals("Status should be FINISH", travelReport.getString("status"),"FINISH");
    }

    @Then("^an empty travel report is created$")
    public void emptyTravelReport(){
        assertNotEquals("Id should not be empty", travelReport.getString("id"), "");
    }

    @Then("^the travel report (contains|not contains) the correct total amount")
    public void travelReportsAddExpenses(String containedFlag){
        JSONArray exp = answer.getJSONArray("expenses");
        boolean contained = false;
        Double total = 0.0;
        for (int i = 0; i < exp.length(); i++) {
            JSONObject object = exp.getJSONObject(i);
            total += object.getDouble("amount");
        }
        if (total == answer.getDouble("totalAmount")) {contained = true;}
        if (containedFlag.equals("contains") != contained) {
            fail("Business travel should be '" + containedFlag + "'");
        }
    }

    @When("^the travel report is approved")
    public void travelReportApprove(){
        JSONObject request = new JSONObject(travelReport.toString());
        request.put("event","validate");
        System.out.println("validate : " + request.toString());
        travelReport = sendRequest(request);
        System.out.println("answer : " + travelReport.toString());
    }

    @Then("^the status should change to ACCEPTED")
    public void travelReportApproveVerification(){
        Assert.assertEquals("the status changed to ACCEPTED", travelReport.getString("status"), "APPROVED");
    }

    private String getUrl() {
        return "http://" + host + ":" + port + "/tcs-service-travel-report/report/";
    }

    private JSONObject sendRequest(JSONObject request) {
        String url = getUrl();

        Response response = WebClient.create(url)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(request.toString());
        returnedProperly = response.getStatus() < 500 || response.getStatus() >= 600;

        String body = response
                .readEntity(String.class);
        System.out.println("response : " + body);
        return new JSONObject(body);
    }
}
