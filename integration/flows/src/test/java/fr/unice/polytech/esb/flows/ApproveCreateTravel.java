package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.flights.CheapestFlight;
import fr.unice.polytech.esb.flows.reports.business.BusinessTravelApproval;
import fr.unice.polytech.esb.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApproveCreateTravel  extends CamelTestSupport {
    private static final Logger LOG = LoggerFactory.getLogger(CheapestFlight.class);

    @Override
    public String isMockEndpointsAndSkip() {
        return Endpoints.BUSINESS_TRAVEL_ENDPOINT +
                "|" + Endpoints.TRAVEL_REPORT_ENDPOINT;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new BusinessTravelApproval();
    }

    /**
     * Test the behavior of the route with a valid request
     * No errors should be thrown
     * Both the BUSINESS_TRAVEL_ENDPOINT and TRAVEL_REPORT_ENDPOINT should receive a request
     * @throws Exception
     */
    @Test
    public void FlightSearch() throws Exception {

        String request = "{\"id\":\"0ae1399a-4d3c-4259-91cc-0d5f4d310401\"}";
        String exampleReceivedBusiness = "{\"id\":\"e73a3bb4-1452-4406-816f-47e87ff1bce0\",\"validated\":true,\"tickets\":null,\"nights\":null}";
        String exampleReceivedTravel = "{\"id\":\"2bf70434-88c7-42fc-965b-fb9be12d7b4e\",\"status\":\"INPROGRESS\",\"start\":null,\"finish\":null,\"expenses\":null,\"totalAmount\":null,\"explaination\":null,\"businessTravelId\":\"e73a3bb4-1452-4406-816f-47e87ff1bce0\"}";

        System.out.println("TEST: testValidFlightSearch");
        LOG.debug("TEST");

        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(Endpoints.APPROVE_BUSINESS_TRAVEL));
        assertNotNull(context.hasEndpoint(Endpoints.BUSINESS_TRAVEL_ENDPOINT));
        assertNotNull(context.hasEndpoint(Endpoints.TRAVEL_REPORT_ENDPOINT));

        // Configuring expectations on the mocked endpoint
        String mock_business_endpoint = "mock://" + Endpoints.BUSINESS_TRAVEL_ENDPOINT;
        String mock_travel_endpoint = "mock://" + Endpoints.TRAVEL_REPORT_ENDPOINT;

        assertNotNull(context.hasEndpoint(mock_business_endpoint));
        assertNotNull(context.hasEndpoint(mock_travel_endpoint));

        System.out.println("WAITING FOR MESSAGE ON ENDPOINTS");
        getMockEndpoint(mock_business_endpoint).expectedMessageCount(1);
        getMockEndpoint(mock_travel_endpoint).expectedMessageCount(1);
        System.out.println("SENDING REQUEST");
        template.sendBody(Endpoints.APPROVE_BUSINESS_TRAVEL, request);

        getMockEndpoint(mock_business_endpoint).assertIsSatisfied();
        getMockEndpoint(mock_travel_endpoint).assertIsSatisfied();

        String requestBusiness = getMockEndpoint(Endpoints.BUSINESS_TRAVEL_ENDPOINT).getReceivedExchanges().get(0).getIn().getBody(String.class);
        String requestTravel = getMockEndpoint(Endpoints.TRAVEL_REPORT_ENDPOINT).getReceivedExchanges().get(0).getIn().getBody(String.class);


        JSONAssert.assertEquals(exampleReceivedBusiness, requestBusiness, false);
        JSONAssert.assertEquals(exampleReceivedTravel, requestTravel, false);
    }
}
