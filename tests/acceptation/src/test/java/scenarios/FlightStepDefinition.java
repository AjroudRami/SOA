package scenarios;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FlightStepDefinition {

    private static final String ROOM_COST_FIELD = "roomCost";

    private static class ServiceRequest {
        private String destination;
        private String priceOrdering;
    }

    private String host = "localhost";
    private int port = 8080;

    private ServiceRequest requestDetail;
    private JSONArray answer;

    @Given("^a hotel service deployed on (.*):(\\d+)$")
    public void setupAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Given("^a research for a hotel booking$")
    public void bookHotel() {
        requestDetail = new ServiceRequest();
    }

    @Given("^located in (.*)$")
    public void specifyDestination(String destination) {
        requestDetail.destination = destination;
    }

    @Given("^with prices (ascendingly|descendingly) ordered$")
    public void specifyPriceOrdering(String ordering) {
        if ("ascendingly".equals(ordering)) {
            requestDetail.priceOrdering = "asc";
        } else {
            requestDetail.priceOrdering = "desc";
        }
    }

    @When("^the research is sent$")
    public void the_research_is_sent() {
        sendRequest();
    }

    @Then("^hotels are suggested$")
    public void hotelsAreSuggested() {
        assertFalse(answer.length() == 0);
    }

    @Then("^the prices are (ascendingly|descendingly) ordered$")
    public void pricesAreCorrectlyOrdered(String ordering) {
        if ("ascendingly".equals(ordering)) {
            int previousPrice = answer.getJSONObject(0).getInt(ROOM_COST_FIELD);
            for (int i = 1; i < answer.length(); i++) {
                int currentPrice = answer.getJSONObject(i).getInt(ROOM_COST_FIELD);
                assertFalse(currentPrice < previousPrice);
                previousPrice = currentPrice;
            }
        } else {
            int previousPrice = answer.getJSONObject(0).getInt(ROOM_COST_FIELD);
            for (int i = 1; i < answer.length(); i++) {
                int currentPrice = answer.getJSONObject(i).getInt(ROOM_COST_FIELD);
                assertFalse(currentPrice > previousPrice);
                previousPrice = currentPrice;
            }
        }
    }

    @Then("^the hotels are located in (.*)$")
    public void hotelsAreCorrectlyLocated(String destination) {
        for (int i = 0; i < answer.length(); i++) {
            JSONObject jsonHotel = answer.getJSONObject(i);

            assertEquals(destination, jsonHotel.getString("city"));
        }
    }

    private String getUrl() {
        String url = "http://" + host + ":" + port + "/tcs-hotel-service/hotels?";

        if (requestDetail.destination != null) {
            url += "destination=" + requestDetail.destination + "&";
        }

        if (requestDetail.priceOrdering != null) {
            url += "price_ordering=" + requestDetail.priceOrdering + "&";
        }

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
