package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.flights.CheapestFlightProcess;
import fr.unice.polytech.esb.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CheapestFlightProcessTest extends CamelTestSupport {

    @Override public String isMockEndpointsAndSkip() { return Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE
            + "|" + Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE; }

    @Override protected RouteBuilder createRouteBuilder() throws Exception { return new CheapestFlightProcess(); }

    @Test
    public void testFlightResearch() throws Exception {

        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_CHEAPEST_FLIGHT));
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE));
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE));
        /**assertNotNull(context.hasEndpoint(Endpoints.INTERNAL_FLIGHTS_ENDPOINT));
        assertNotNull(context.hasEndpoint(Endpoints.EXTERNAL_FLIGHTS_ENDPOINT));
        **/

        // Configuring expectations on the mocked endpoint
        String mock_internal = "mock://" + Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE;
        String mock_external = "mock://" + Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;

        assertNotNull(context.hasEndpoint(mock_internal));
        assertNotNull(context.hasEndpoint(mock_external));

        getMockEndpoint(mock_internal).expectedMessageCount(1);
        getMockEndpoint(mock_internal).expectedHeaderReceived("Content-Type", "application/json");
        getMockEndpoint(mock_internal).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(mock_internal).expectedHeaderReceived("CamelHttpMethod", "POST");

        String depAirport = "Paris";
        String arrivalAirport = "New-York";
        long depDate = 1235423;

        String jsonRequest = "{ \"from\": \"" + depAirport + "\"," +
                "\"to\":\"" + arrivalAirport + "\"," +
                "\"departure\":" + depDate + "}";

        // Sending Johm for registration
        template.sendBody(Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE, jsonRequest);
        template.sendBody(Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE, "");
        getMockEndpoint(mock_internal).assertIsSatisfied();
        /**

        // As the assertions are now satisfied, one can access to the contents of the exchanges
        String request = getMockEndpoint(mock_internal).getReceivedExchanges().get(0).getIn().getBody(String.class);

        String expected = "{\n" +
                "  \"event\": \"REGISTER\",\n" +
                "  \"citizen\": {\n" +
                "    \"last_name\": \"Doe\",\n" +
                "    \"first_name\": \"John\",\n" +
                "    \"ssn\": \"1234567890\",\n" +
                "    \"zip_code\": \"06543\",\n" +
                "    \"address\": \"nowhere, middle of\",\n" +
                "    \"birth_year\": \"1970\"\n" +
                "  }\n" +
                "}";
        JSONAssert.assertEquals(expected, request, false);
         **/
    }


}
