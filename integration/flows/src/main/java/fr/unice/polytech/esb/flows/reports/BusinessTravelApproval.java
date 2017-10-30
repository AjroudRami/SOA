package fr.unice.polytech.esb.flows.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.reports.data.ApproveTravel;
import fr.unice.polytech.esb.flows.reports.data.BusinessTravel;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.ArrayList;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class BusinessTravelApproval extends RouteBuilder{

    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/approve-business-travel/").post("/search").type(Object.class).to(APPROVE_BUSINESS_TRAVEL);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEATH_POOL)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<BusinessTravel>()));

        // Process to approve business travel.
        from(APPROVE_BUSINESS_TRAVEL)
                .routeId("approve-business-travel")
                .routeDescription("Approve business travel and create travel report")

                .log("Generating a business approval process")

                // Parse the body content from JSON string to ApproveTravel.
                .unmarshal().json(JsonLibrary.Jackson, BusinessTravel.class)

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeBody(exchange.getIn().getBody(BusinessTravel.class))))

                // Send the request to the internal service.
                .inOut(BUSINESS_TRAVEL_ENDPOINT)

                .choice()
                    .when(body().isNotNull())
                        .to(TRAVEL_REPORT_CREATION);
    }

    /**
     * Prepares the body for the internal service request.
     * @param businessTravel approval request
     * @return A JSON object string.
     */
    private static String makeBody(BusinessTravel businessTravel) {
        ObjectNode json = mapper.createObjectNode();
        json.put("event", "approve");
        json.put("id", businessTravel.getId());
        return json.toString();
    }

}
