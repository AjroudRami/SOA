package fr.unice.polytech.esb.flows.reports.business;

import fr.unice.polytech.esb.flows.ActiveMQTest;
import fr.unice.polytech.esb.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class BusinessTravelFlowTest extends ActiveMQTest{

    @Override public String isMockEndpointsAndSkip() { return Endpoints.BUSINESS_TRAVEL_ENDPOINT; }

    @Override protected RouteBuilder createRouteBuilder() throws Exception { return new BusinessTravelCreation(); }

    private JSONObject business;

    @Before public void init() throws Exception{
        business = new JSONObject();

        JSONArray tickets = new JSONArray();
        JSONObject ticket = new JSONObject();
        ticket.put("ticketNumber","123");
        ticket.put("departureAirport","NCE");
        ticket.put("departureTimestamp","123");
        ticket.put("arrivalAirport","AMS");
        ticket.put("arrivalTimestamp","123");
        tickets.put(ticket);

        business.put("tickets",tickets);
    }
    @Test public void testBusinessTravelCreation() throws Exception {
        // Asserting endpoints existence
        assertNotNull(context.hasEndpoint(Endpoints.BUSINESS_TRAVEL_CREATE));
        assertNotNull(context.hasEndpoint(Endpoints.BUSINESS_TRAVEL_ENDPOINT));

        // Configuring expectations on the mocked endpoint
        String mock = "mock://"+Endpoints.BUSINESS_TRAVEL_ENDPOINT;
        assertNotNull(context.hasEndpoint(mock));
        getMockEndpoint(mock).expectedMessageCount(1);
        getMockEndpoint(mock).expectedHeaderReceived("Content-Type", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("Accept", "application/json");
        getMockEndpoint(mock).expectedHeaderReceived("CamelHttpMethod", "POST");

        // Sending business for creation
        template.sendBody(Endpoints.BUSINESS_TRAVEL_CREATE, business);

        getMockEndpoint(mock).assertIsSatisfied();


        // As the assertions are now satisfied, one can access to the contents of the exchanges
        String request = getMockEndpoint(mock).getReceivedExchanges().get(0).getIn().getBody(String.class);

        System.out.println(request);
    }

}
