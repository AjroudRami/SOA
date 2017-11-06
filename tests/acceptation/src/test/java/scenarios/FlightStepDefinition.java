package scenarios;

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
import javax.ws.rs.core.MediaType;
import static org.junit.Assert.assertFalse;

public class FlightStepDefinition {

    public class Filter{
        private String name;
        private String[] args;

        public Filter(String name, String[] args) {
            this.name = name;
            this.args = args;
        }

        public Filter(){}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getArgs() {
            return args;
        }

        public void setArgs(String[] args) {
            this.args = args;
        }
    }

    public class ServiceRequest {

        private String event;
        private String destination;
        private String departure;
        private int departureTimeStamp;
        private Filter[] filterBy;
        private String orderBy;

        public ServiceRequest(){
        }

        public ServiceRequest(String event, String destination, String departure, int departureTimeStamp, Filter[] filterBy, String orderBy) {
            this.event = event;
            this.destination = destination;
            this.departure = departure;
            this.departureTimeStamp = departureTimeStamp;
            this.filterBy = filterBy;
            this.orderBy = orderBy;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getDeparture() {
            return departure;
        }

        public void setDeparture(String departure) {
            this.departure = departure;
        }

        public int getDepartureTimeStamp() {
            return departureTimeStamp;
        }

        public void setDepartureTimeStamp(int departureTimeStamp) {
            this.departureTimeStamp = departureTimeStamp;
        }

        public Filter[] getFilterBy() {
            return filterBy;
        }

        public void setFilterBy(Filter[] filterBy) {
            this.filterBy = filterBy;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public void AddFilter(Filter filter){
            if (this.filterBy == null) {
                filterBy = new Filter[0];
            }
            Filter[] newFilters = Arrays.copyOf(filterBy, filterBy.length + 1);
            newFilters[newFilters.length - 1] = filter;
            this.filterBy = newFilters;
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

    @Given("^ordering by ([a-zA-Z]*)")
    public void orderingBy(String ordering) {
        requestDetail.setOrderBy(ordering);
    }

    @Given("^a simple filter (.*)$")
    public void addSimpleFilter(String filter){
        Filter fl = new Filter(filter, new String[]{});
        requestDetail.AddFilter(fl);
    }

    @Given("^a filter ([a-zA-Z]+(?:_[a-zA-Z]+)*) with value ([a-zA-Z0-9]*)$")
    public void addFilter(String filterName, String filterValue) {
        String[] args = new String[]{filterValue};
        Filter fl = new Filter(filterName, args);
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
        System.out.println("filter value: " + filterValue);
        if (answer.length() == 0) {
            return;
        }
        if ("direct".equals(filterName)){
            for(int i = 0; i < answer.length(); i ++) {
                int nbOfFlights = answer.getJSONObject(i).getInt("numberOfFlights");
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
