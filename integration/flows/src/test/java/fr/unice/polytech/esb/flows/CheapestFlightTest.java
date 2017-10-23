package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.flights.CheapestFlight;
import fr.unice.polytech.esb.flows.flights.CheapestFlightExternal;
import fr.unice.polytech.esb.flows.flights.CheapestFlightInternal;
import fr.unice.polytech.esb.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheapestFlightTest extends CamelTestSupport {
    private static final Logger LOG = LoggerFactory.getLogger(CheapestFlight.class);

    @Override
    public String isMockEndpointsAndSkip() {
        return Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE +
                "|" + Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE +
                "|" + Endpoints.INTERNAL_FLIGHTS_ENDPOINT +
                "|" + Endpoints.EXTERNAL_FLIGHTS_ENDPOINT;

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new CheapestFlight();
    }

    @Test
    public void testFlightResearch() throws Exception {

        CheapestFlightExternal routeExternal = new CheapestFlightExternal();
        CheapestFlightInternal routeInternal = new CheapestFlightInternal();
        routeInternal.configure();
        routeExternal.configure();
        context.addRouteDefinitions(routeExternal.getRouteCollection().getRoutes());
        context.addRouteDefinitions(routeInternal.getRouteCollection().getRoutes());

        assertNotNull(context.hasEndpoint(Endpoints.EXTERNAL_FLIGHTS_ENDPOINT));
        assertNotNull(context.hasEndpoint(Endpoints.INTERNAL_FLIGHTS_ENDPOINT));

        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_CHEAPEST_FLIGHT));
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE));
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE));

        // Configuring expectations on the mocked endpoint
        //String mock_internal = "mock://" + Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE;
        //String mock_external = "mock://" + Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;

        // Configuring expectations on the mocked endpoint
        String mock_internal_endpoint = "mock://" + Endpoints.INTERNAL_FLIGHTS_ENDPOINT;
        String mock_external_endpoint = "mock://" + Endpoints.EXTERNAL_FLIGHTS_ENDPOINT;

        //assertNotNull(context.hasEndpoint(mock_internal));
        //assertNotNull(context.hasEndpoint(mock_external));

        assertNotNull(context.hasEndpoint(mock_internal_endpoint));
        assertNotNull(context.hasEndpoint(mock_external_endpoint));


         getMockEndpoint(mock_internal_endpoint).expectedMessageCount(1);

         /**getMockEndpoint(mock_internal).expectedHeaderReceived("Content-Type", "application/json");
         getMockEndpoint(mock_internal).expectedHeaderReceived("Accept", "application/json");
         getMockEndpoint(mock_internal).expectedHeaderReceived("CamelHttpMethod", "POST");

         getMockEndpoint(mock_external).expectedMessageCount(1);
         getMockEndpoint(mock_external).expectedHeaderReceived("Content-Type", "application/json");
         getMockEndpoint(mock_external).expectedHeaderReceived("Accept", "application/json");
         getMockEndpoint(mock_external).expectedHeaderReceived("CamelHttpMethod", "POST");
         **/

        //getMockEndpoint(mock_external).expect
        // Sending Johm for registration
        template.sendBody(Endpoints.INTERNAL_FLIGHTS_ENDPOINT, exampleResponseInternal);

        getMockEndpoint(mock_internal_endpoint).assertIsSatisfied();
        //getMockEndpoint(mock_external).assertIsSatisfied();

        /**
         // As the assertions are now satisfied, one can access to the contents of the exchanges
         String request = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);

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


    private String exampleResponseInternal = "{ \"flights\":[" +
    "{" +
        "\"departure\":710171030," +
            "\"numberOfFlights\":1," +
            "\"ticketNo\":66," +
            "\"arrival\":710171730," +
            "\"seatClass\":\"Green\"," +
            "\"price\":1188.39," +
            "\"_id\":null," +
            "\"from\":\"Paris\"," +
            "\"to\":\"New-York\"," +
            "\"duration\":700" +
    "} ] }";
}
