package scenarii;

import com.fasterxml.jackson.core.JsonProcessingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static scenarii.Endpoints.*;

public class BusServicesStepDefinition {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    private String host = "localhost";
    private int port = 8181;
    private String group = "tars";
    private String service;
    private String requestName;
    private JSONObject request;

    private String answer;

    @Given("^a bus deployed on (.*):(\\d+)$")
    public void setupAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Given("^a research for the cheapest flight booking$")
    public void cheapestFlightResearch(){
        this.service = CHEAPEST_FLIGHT_ENDPOINT;
        this.requestName = FLIGHT_SEARCH;
        this.request = new JSONObject();
    }

    @Given("^a research for the cheapest hotel$")
    public void cheapestHotelResearch(){
        this.service = CHEAPEST_HOTEL_ENDPOINT;
        this.requestName = HOTEL_SEARCH;
        this.request = new JSONObject();
    }

    @Given("^a research for the cheapest car$")
    public void cheapestCarResearch(){
        this.service = CHEAPEST_CAR_ENDPOINT;
        this.requestName = CAR_SEARCH;
        this.request = new JSONObject();
    }

    @Given("^a departure airport located in (.*)$")
    public void departureAirport(String airportName) {
        request.put("from", airportName);
    }

    @Given("^an arrival airport located in (.*)$")
    public void arrivalAirport(String arrivalAirport) {
        request.put("to", arrivalAirport);
    }

    @Given("^a flight departure date (\\d+)$")
    public void departureDate(Long date) {
        request.put("departure", date);
    }

    @When("^the request is sent$")
    public void send(){
        try {
            sendRequest();
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getUrl() {
        String url = "http://" + host + ":" + port + "/" + group + "/" + service + "/" + requestName;
        return url;
    }

    @Then("^flights are suggested$")
    public void flightsAreSuggested(){
        JSONArray answerArray;
        try {
            JSONObject obj = new JSONObject(answer);
            answerArray = new JSONArray();
            answerArray.put(obj);
        } catch(JSONException e) {
            answerArray = new JSONArray(answer);
        }
        Assert.assertFalse(answerArray.length() == 0);
    }


    private void sendRequest() throws JsonProcessingException, UnsupportedEncodingException {
        String url = getUrl();
        String jsonInString = request.toString();
        LOGGER.log(Level.INFO, "Sending request to: " + url);
        String body = WebClient.create(url)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(jsonInString)
                .readEntity(String.class);
        LOGGER.log(Level.INFO, "Received response body: \n" + body);
        this.answer = body;
    }

}
