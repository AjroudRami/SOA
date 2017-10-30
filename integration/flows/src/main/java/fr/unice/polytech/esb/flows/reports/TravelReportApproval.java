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

public class TravelReportApproval extends RouteBuilder{


    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/approve-travel-report/").post("/search").type(Object.class).to(APPROVE_TRAVEL_REPORT);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEATH_POOL)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<BusinessTravel>()));

        // Process to approve business travel.
        from(APPROVE_TRAVEL_REPORT)
                .routeId("approve-travel-report")
                .routeDescription("Approve travel report")

                .log("Generating a travel approval process")

                // Parse the body content from JSON string to ApproveTravel.
                .unmarshal().json(JsonLibrary.Jackson, ApproveTravel.class)

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeBody(exchange.getIn().getBody(ApproveTravel.class))))

                // Send the request to the internal service.
                .to(TRAVEL_REPORT_ENDPOINT);
    }

    private static String makeBody(ApproveTravel approveTravel) {
        ObjectNode json = mapper.createObjectNode();
        json.put("event", "validate");
        json.put("id", approveTravel.getId());
        return json.toString();
    }
}
