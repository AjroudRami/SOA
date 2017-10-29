package scenarii;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.Gson;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import request.ServiceRequest;

import javax.ws.rs.core.MediaType;

public class CheapestFlightStepDefinition {

    private String host = "localhost";
    private int port = 8080;

    private JSONArray answer;
    private ServiceRequest requestDetail;

    @Given("^a cheapest flight service deployed on (.*):(\\d+)$")
    public void setupAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Given("^a research for a flight booking$")
    public void bookflight(){
        requestDetail = new ServiceRequest();
        requestDetail.setEvent("list");
    }

    @Given("^a departure airport located in (.*)$")
    public void departureAirport(String airportName) {
        requestDetail.setDeparture(airportName);
    }

    @Given("^an arrival airport located in (.*)$")
    public void arrivalAirport(String arrivalAirport) {
        requestDetail.setDestination(arrivalAirport);
    }

    @Given("^a departure date (\\d+)$")
    public void departureDate(int date) {
        requestDetail.setDepartureTimeStamp(date);
    }

    @When("^the flight research is sent$")
    public void send(){
        try {
            sendRequest();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getUrl() {
        String url = "http://" + host + ":" + port + "/tcs-service-flights/flights/";
        return url;
    }

    @Then("^flights are suggested$")
    public void flightsAreSuggested(){
        Assert.assertFalse(answer.length() == 0);
    }


    private void sendRequest() throws JsonProcessingException {
        String url = getUrl();

        ObjectMapper mapper = new ObjectMapper();
        Gson gson = new Gson();
        //Object to JSON in String
        String jsonInString = gson.toJson(requestDetail);

        String body = (String) WebClient.create(url)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(jsonInString)
                .readEntity(String.class);

        System.out.println(body);
        answer = new JSONObject(body).getJSONArray("flights");
    }

}
