package fr.unice.polytech.esb.flows.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.reports.data.BusinessTravel;
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
                .process(exchange -> exchange.getIn()
                        .setBody(makeBody(exchange.getIn().getBody(BusinessTravel.class))))

                // Send the request to the internal service.
                .to(TRAVEL_REPORT_ENDPOINT);
    }

    /**
     * Prepares the body for the internal service request.
     * @param businessTravel approval request
     * @return A JSON object string.
     */
    private static String makeBody(BusinessTravel businessTravel) {
        ObjectNode json = mapper.createObjectNode();
        json.put("event", "create");
        json.put("businessTravelId", businessTravel.getId());
        return json.toString();
    }
}
