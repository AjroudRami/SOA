package fr.unice.polytech.esb.flows.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.reports.data.ApproveTravel;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class BusinessApproval extends RouteBuilder{

    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void configure() throws Exception {


        // Business travel approval
        from(BUSINESS_APPROVAL_QUEUE)
                .routeId("call-internal-business-travel-service")
                .routeDescription("Call the internal business travel service")

                // Log the current action.
                .log("Approve a business travel.")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeBody(exchange.getIn().getBody(ApproveTravel.class))))

                // Send the request to the internal service.
                .inOut(BUSINESS_TRAVEL_ENDPOINT)

                .choice()
                    .when(body().isNotNull())
                        .to(TRAVEL_REPORT_CREATE_QUEUE);
    }

    /**
     * Prepares the body for the internal service request.
     * @param approveTravel approval request
     * @return A JSON object string.
     */
    private static String makeBody(ApproveTravel approveTravel) {
        ObjectNode json = mapper.createObjectNode();
        json.put("event", "approve");
        json.put("id", approveTravel.getId());
        return json.toString();
    }

}
