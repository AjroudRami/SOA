package scenarios;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class BusinessTravelStepDefinition {

    private String host = "host";
    private int port = 8080;

    private JSONObject answer;
    private JSONObject currentBusinessTravel;
    private String lastTravelId;
    private boolean returnedProperly;

    /**
     * Initiates the business travel service endpoint
     * @param host
     * @param port
     */
    @Given("^a business travel service deployed on (.*):(\\d+)$")
    public void setupAddress(String host, int port) {
        this.host = host;
        this.port = port;
        this.currentBusinessTravel = newBusinessTravel();
        this.lastTravelId = "";
    }

    /**
     *
     * @return an empty business travel
     */
    private JSONObject newBusinessTravel() {
        JSONObject travel = new JSONObject();
        travel.put("tickets", new JSONArray());
        travel.put("nights", new JSONArray());
        return travel;
    }

    /**
     * Initiates a new business travel
     */
    @Given("^a new business travel$")
    public void initBusinessTravel() {
        this.currentBusinessTravel = newBusinessTravel();
    }

    /**
     * Add a flight to the current business travel
     * @param flightId
     * @param departureAirport
     * @param arrivalAirport
     * @param departureDateString
     * @param arrivalDateString
     * @throws ParseException
     */
    @And("^a flight #([0-9]+) from ([A-Z]+) to ([A-Z]+) on ([0-9\\/\\-:]+) arriving at ([0-9\\/\\-:]+)$")
    public void withFlight(String flightId, String departureAirport, String arrivalAirport, String departureDateString, String arrivalDateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy-hh:mm");
        long timestampDeparture = dateFormat.parse(departureDateString).getTime();
        long timestampArrival = dateFormat.parse(arrivalDateString).getTime();
        currentBusinessTravel.getJSONArray("tickets").put(newFlight(flightId, departureAirport, arrivalAirport, timestampDeparture, timestampArrival));
    }

    /**
     * Add a hotel to the current business travel
     * @param hotelId
     * @param nights
     */
    @And("^a hotel #([0-9]+) with the given nights$")
    public void withFlight(String hotelId, List<String> nights) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        // Create our hotel nights
        JSONObject hotelNight = new JSONObject();
        hotelNight.put("hotelId", hotelId);
        JSONArray nightsArray = new JSONArray();
        hotelNight.put("nights", nightsArray);

        // create our different nights
        nights.forEach(night -> {
            String[] nightArguments = night.split(" ");
            String room = nightArguments[1];
            try {
                long date = dateFormat.parse(nightArguments[3]).getTime();
                nightsArray.put(newNight(room, date));
            } catch (ParseException e) {
                fail("Invalid date format : " + e.getMessage());
            }
        });

        currentBusinessTravel.getJSONArray("nights").put(hotelNight);
    }

    @When("^the business travel is validated$")
    public void theBusinessTravelIsValidated() {
        JSONObject request = new JSONObject();
        request.put("event", "approve");
        request.put("id", lastTravelId);
        sendRequest(request);
    }

    /**
     *
     * @param room
     * @param date
     * @return a new hotel night
     */
    private JSONObject newNight(String room, long date) {
        JSONObject night = new JSONObject();
        night.put("room", room);
        night.put("date", date);
        return night;
    }

    /**
     * Send the current business travel
     */
    @When("^the business travel is sent$")
    public void theBusinessTravelIsSent() {
        JSONObject request = new JSONObject(currentBusinessTravel.toString());
        request.put("event", "submit");
        sendRequest(request);
        lastTravelId = answer.getString("submissionId");
        currentBusinessTravel.put("id", lastTravelId);
    }

    @When("^the business travel is sent to ([^\\s]+)$")
    public void theBusinessTravelIsSentToSomeone(String email) {
        sendBusinessTravel(email, lastTravelId);
    }

    /**
     * Send a business travel of given id to someone
     * @param email
     * @param travelId
     */
    private void sendBusinessTravel(String email, String travelId) {
        JSONObject request = new JSONObject();
        request.put("event", "send");
        request.put("recipient", email);
        JSONObject content = new JSONObject();
        content.put("id", travelId);
        request.put("content", content);
        sendRequest(request);
    }

    @When("^the business travel #([0-9]+) is sent to ([^\\s]+)$")
    public void theBusinessTravelOfIdIsSentToSomeone(String travelId, String email) {
        sendBusinessTravel(email, travelId);
    }


    /**
     * Send the current business travel
     */
    @When("^the business travels are listed$")
    public void theBusinessTravelsAreListed() {
        JSONObject request = new JSONObject();
        request.put("event", "list");
        sendRequest(request);
    }

    /**
     * assert that an id has been received in the service reply
     */
    @Then("^the list (contains|not contain) the last business travel$")
    public void businessTravelsShouldContainTheBusinessTravel(String containedFlag) {
        JSONArray travels = answer.getJSONArray("travels");
        boolean contained = false;
        for (int i = 0; i < travels.length(); i++) {
            JSONObject object = travels.getJSONObject(i);
            JSONObject businessTravel = new JSONObject();
            businessTravel.put("tickets", object.get("tickets"));
            businessTravel.put("nights", object.get("nights"));
            businessTravel.put("id", object.get("id"));

            if (businessTravel.similar(currentBusinessTravel)) {
                contained = true;
                break;
            }
        }
        if (containedFlag.equals("contains") != contained) {
            fail("Business travel should be '" + containedFlag + "'");
        }
    }

    /**
     * assert that an id has been received in the service reply
     */
    @Then("^an id is received$")
    public void hotelsAreSuggested() {
        assertNotEquals("Id should not be empty", lastTravelId, "");
    }

    /**
     *
     * @param flightId
     * @param departureAirport
     * @param arrivalAirport
     * @param timestampDeparture
     * @param timestampArrival
     * @return a new flight
     */
    private JSONObject newFlight(String flightId, String departureAirport, String arrivalAirport, long timestampDeparture, long timestampArrival) {
        JSONObject flight = new JSONObject();
        flight.put("ticketNumber", flightId);
        flight.put("departureAirport", departureAirport);
        flight.put("arrivalAirport", arrivalAirport);
        flight.put("departureTimestamp", timestampDeparture);
        flight.put("arrivalTimestamp", timestampArrival);
        return flight;
    }

    /**
     * The service should return properly
     */
    @Then("^the service (should|should not) return properly$")
    public void theServiceShouldReturnProperly(String should) {
        assertEquals("The service "+should+" return properly", should.equals("should"), returnedProperly);
    }


    private String getUrl() {
        return "http://" + host + ":" + port + "/tcs-service-business-travels/registry";
    }

    private void sendRequest(JSONObject request) {
        String url = getUrl();

        Response response = WebClient.create(url)
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .post(request.toString());
        returnedProperly = response.getStatus() < 500 || response.getStatus() >= 600;

        String body = response
                .readEntity(String.class);

        if (body.isEmpty()) {
            answer = new JSONObject();
        } else {
            answer = new JSONObject(body);
        }
    }
}
