package scenarii;

import com.fasterxml.jackson.core.JsonProcessingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.deps.com.google.gson.Gson;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheapestFlightStepDefinition {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());
    private String host = "localhost";
    private int port = 8181;
    private String endpointUrl = "/tars/cheapest-flights/";
    private String requestName = "search";
    private FlightRequest request;

    private JSONArray answer;

    private class FlightRequest{
        private String from;
        private String to;
        private Long departure;
        public FlightRequest(){}

        public String getDepartureAirport() {
            return from;
        }

        public void setDepartureAirport(String departureAirport) {
            this.from = departureAirport;
        }

        public String getArrivalAirport() {
            return to;
        }

        public void setArrivalAirport(String arrivalAirport) {
            this.to = arrivalAirport;
        }

        public Long getDepartureDate() {
            return departure;
        }

        public void setDepartureDate(Long departureDate) {
            this.departure = departureDate;
        }
    }

    @Given("^a cheapest flight service deployed on (.*):(\\d+)$")
    public void setupAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Given("^a research for a flight booking$")
    public void bookflight(){
        this.request = new FlightRequest();
    }

    @Given("^a departure airport located in (.*)$")
    public void departureAirport(String airportName) {
        request.setDepartureAirport(airportName);
    }

    @Given("^an arrival airport located in (.*)$")
    public void arrivalAirport(String arrivalAirport) {
        request.setArrivalAirport(arrivalAirport);
    }

    @Given("^a departure date (\\d+)$")
    public void departureDate(Long date) {
        request.setDepartureDate(date);
    }

    @When("^the flight research is sent$")
    public void send(){
        try {
            sendRequest();
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getUrl() {
        String url = "http://" + host + ":" + port + endpointUrl + requestName;
        return url;
    }

    @Then("^flights are suggested$")
    public void flightsAreSuggested(){
        Assert.assertFalse(answer.length() == 0);
    }


    private void sendRequest() throws JsonProcessingException, UnsupportedEncodingException {
        String url = getUrl();
        Gson gson = new Gson();
        String jsonInString = gson.toJson(request);
        LOGGER.log(Level.INFO, "Sending request to: " + url);
        String body = WebClient.create(url)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(jsonInString)
                .readEntity(String.class);
        LOGGER.log(Level.INFO, "Received response body: \n" + body);
        try {
            JSONObject obj = new JSONObject(body);
            answer = new JSONArray();
            answer.put(obj);
        } catch(JSONException e) {
            answer = new JSONArray(body);
        }

    }

}
