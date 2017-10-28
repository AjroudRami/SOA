package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.flights.CheapestFlight;
import fr.unice.polytech.esb.flows.flights.CheapestFlightExternal;
import fr.unice.polytech.esb.flows.flights.CheapestFlightInternal;
import fr.unice.polytech.esb.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheapestFlightTest extends CamelTestSupport {
    private static final Logger LOG = LoggerFactory.getLogger(CheapestFlight.class);

    @Override
    public String isMockEndpointsAndSkip() {
        return Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE +
                "|" + Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new CheapestFlight();
    }

    /**
     * Test the behavior of the route with a valid request
     * No errors should be thrown
     * Both the SEARCH_IN_INTERNAL_FLIGHTS_SERVICE and SEARCH_IN_EXTERNAL_FLIGHT_SERVICE should receive a request
     * @throws Exception
     */
    @Test
    public void FlightSearch() throws Exception {

        String request = "{\"from\":\"Paris\", \"to\":\"New-York\", \"departure\":1234567890 }"; //TODO replace with request send to the route (received by the bus)
        String exampleReceivedInternal = ""; //TODO replace with real value received by SEARCH_IN_EXT ... endpoint
        String exampleReceivedExternal = ""; //TODO replace with real value received by SEARCH_IN_INT ... endpoint

        System.out.println("TEST: testValidFlightSearch");
        LOG.debug("TEST");
        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_CHEAPEST_FLIGHT));
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE));
        assertNotNull(context.hasEndpoint(Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE));

        // Configuring expectations on the mocked endpoint
        String mock_internal_endpoint = "mock://" + Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE;
        String mock_external_endpoint = "mock://" + Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;


        assertNotNull(context.hasEndpoint(mock_internal_endpoint));
        assertNotNull(context.hasEndpoint(mock_external_endpoint));
        System.out.println("WAITING FOR MESSAGE ON ENDPOINTS");
        getMockEndpoint(mock_internal_endpoint).expectedMessageCount(1);
        getMockEndpoint(mock_external_endpoint).expectedMessageCount(1);
        System.out.println("SENDING REQUEST");
        template.sendBody(Endpoints.SEARCH_CHEAPEST_FLIGHT, request);

        getMockEndpoint(mock_internal_endpoint).assertIsSatisfied();
        getMockEndpoint(mock_external_endpoint).assertIsSatisfied();

        String requestInternal = getMockEndpoint(Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE).getReceivedExchanges().get(0).getIn().getBody(String.class);
        String requestExternal = getMockEndpoint(Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE).getReceivedExchanges().get(0).getIn().getBody(String.class);


        JSONAssert.assertEquals(exampleReceivedInternal, requestInternal, false);
        JSONAssert.assertEquals(exampleReceivedExternal, requestExternal, false);
    }
}
