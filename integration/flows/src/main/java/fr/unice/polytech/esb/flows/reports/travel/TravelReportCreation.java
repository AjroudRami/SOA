package fr.unice.polytech.esb.flows.reports.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.TRAVEL_REPORT_CREATION;
import static fr.unice.polytech.esb.flows.utils.Endpoints.TRAVEL_REPORT_ENDPOINT;

public class TravelReportCreation extends RouteBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        // Travel Report Creation
        from(TRAVEL_REPORT_CREATION)
                .routeId("call-internal-travel-report-service")
                .routeDescription("Call the internal travel report service")

                // Log the current action.
                .log("Create a travel report.")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> {
                    ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
                    node.put("event","create");
                    String businessTravelId = node.get("id").asText();
                    node.put("businessTravelId",businessTravelId);
                    exchange.getIn().setBody(node.toString());
                })
                // Send the request to the internal service.
                .to(TRAVEL_REPORT_ENDPOINT);
    }
}
