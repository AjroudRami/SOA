package scenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.jfxmedia.logging.Logger;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import javax.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FlightStepDefinition {

    private static final String ROOM_COST_FIELD = "roomCost";

    private static class ServiceRequest {

        private static class Filter{
            private String name;
            private String value;

            public Filter(String name, String value) {
                this.name = name;
                this.value = value;
            }
        }
        private String destination;
        private String departure;
        private int depTimestamp;
        private Filter[] filters;
        private String ordering;

        public ServiceRequest(){
            filters = new Filter[0];
        }

        public void AddFilter(Filter filter){
            Filter[] newFilters = Arrays.copyOf(filters, filters.length + 1);
            newFilters[newFilters.length - 1] = filter;
            this.filters = newFilters;
        }
    }

    private String host = "localhost";
    private int port = 8080;

    private ServiceRequest requestDetail;
    private JSONArray answer;

    @Given("^a flights service deployed on (.*):(\\d+)$")
    public void setupAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Given("^a research for a flight booking$")
    public void bookflight(){
        requestDetail = new ServiceRequest();
    }

    @Given("^a departure airport located in (.*)$")
    public void departureAirport(String airportName) {
        requestDetail.departure = airportName;
    }

    @Given("^an arrival airport located in (.*)$")
    public void arrivalAirport(String arrivalAirport) {
        requestDetail.destination = arrivalAirport;
    }

    @Given("^a departure date (\\d+)$")
    public void departureDate(int date) {
        requestDetail.depTimestamp = date;
    }

    @Given("^ordering by (price|duration)$")
    public void orderingBy(String ordering) {
        requestDetail.ordering = ordering;
    }

    @Given("^a simple filter (.*)$")
    public void addSimpleFilter(String filter){
        ServiceRequest.Filter fl = new ServiceRequest.Filter(filter, "");
        requestDetail.AddFilter(fl);
    }

    @Given("^a filter ([a-zA-Z]+(?:_[a-zA-Z]+)*) with value ([a-zA-Z0-9]*)$")
    public void addFilter(String filterName, String filterValue) {
        ServiceRequest.Filter fl = new ServiceRequest.Filter(filterName, filterValue);
        requestDetail.AddFilter(fl);
    }


    @When("^the flight research is sent$")
    public void send(){
        try {
            sendRequest();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Then("^the flights are filtered : ([a-zA-Z]+(?:_[a-zA-Z]+)*) ([a-zA-Z0-9]*)$")
    public void theFlightsAreFiltered(String filterName, String filterValue) {
        if (answer.length() == 0) {
            return;
        }
        if ("direct".equals(filterName)){
            for(int i = 0; i < answer.length(); i ++) {
                int nbOfFlights = answer.getJSONObject(i).getInt("number_of_flights");
                Assert.assertEquals(1, nbOfFlights);
            }
        } else if ("max_duration".equals(filterName)) {
            for(int i = 0; i < answer.length(); i ++) {
                int currentArrival = answer.getJSONObject(i).getInt("arrival");
                int currentDeparture = answer.getJSONObject(i).getInt("departure");
                int currentDuration = currentArrival - currentDeparture;

                Assert.assertTrue(currentDuration < Integer.parseInt(filterValue));
            }
        }
    }

    @Then("^flights are suggested$")
    public void flightsAreSuggested(){
        Assert.assertFalse(answer.length() == 0);
    }

    @Then("^the flights are ordered by (price|duration)$")
    public void theFlightsAreOrderedBy(String ordering) {
        if (answer.length() == 0) {
            return;
        }
        if ("price".equals(ordering)) {
            double previousPrice = answer.getJSONObject(0).getDouble("price");
            for (int i = 1; i < answer.length(); i++) {
                double currentPrice = answer.getJSONObject(i).getDouble("price");
                assertFalse(currentPrice < previousPrice);
                previousPrice = currentPrice;
            }
        }else if ("duration".equals(ordering)) {
            int previousArrival = answer.getJSONObject(0).getInt("arrival");
            int previousDeparture = answer.getJSONObject(0).getInt("departure");
            int previousDuration = previousArrival - previousDeparture;
            for (int i = 1; i < answer.length(); i++) {
                int currentArrival = answer.getJSONObject(i).getInt("arrival");
                int currentDeparture = answer.getJSONObject(i).getInt("departure");
                int currentDuration = currentArrival - currentDeparture;
                assertFalse(currentDuration < previousDuration);
                previousDuration = currentDuration;
            }
        }
    }


    private String getUrl() {
        String url = "http://" + host + ":" + port + "/tcs-service-flights/flights/";
        return url;
    }

    private void sendRequest() throws JsonProcessingException {
        String url = getUrl();

        ObjectMapper mapper = new ObjectMapper();

        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(requestDetail);

        String body = (String) WebClient.create(url)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(jsonInString)
                .readEntity(String.class);
        Logger.logMsg(Logger.INFO, jsonInString);

        answer = new JSONArray(body);
    }
}
