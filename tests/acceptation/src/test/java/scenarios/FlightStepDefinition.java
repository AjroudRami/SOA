package scenarios;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

import java.util.Arrays;

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

    @Given("^a departure date (\\d+)")
    public void departureDate(int date) {
        requestDetail.depTimestamp = date;
    }

    @Given("^ordering by (price|duration)")
    public void orderingBy(String ordering) {
        requestDetail.ordering = ordering;
    }

    @Given("^a simple filter (.*)$")
    public void addSimpleFilter(String filter){
        ServiceRequest.Filter fl = new ServiceRequest.Filter(filter, "");
        requestDetail.AddFilter(fl);
    }

    @Given("^a filter (.*) with value (.*)$ ")
    public void addFilter(String filterName, String filterValue) {
        ServiceRequest.Filter fl = new ServiceRequest.Filter(filterName, filterValue);
        requestDetail.AddFilter(fl);
    }

    private String getUrl() {
        String url = "http://" + host + ":" + port + "/tcs-hotel-service/hotels?";
        return url;
    }

    private void sendRequest() {
        String url = getUrl();

        String body = (String) WebClient.create(url)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .get()
                .readEntity(String.class);

        answer = new JSONArray(body);
    }
}
