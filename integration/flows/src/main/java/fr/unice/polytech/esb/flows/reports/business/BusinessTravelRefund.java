package fr.unice.polytech.esb.flows.reports.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class BusinessTravelRefund extends RouteBuilder{

    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/business-travel/").post("/refund").type(Object.class).to(BUSINESS_TRAVEL_REFUND);

        // Process to approve business travel.
        from(BUSINESS_TRAVEL_REFUND)
                .routeId("refund a business travel")
                .routeDescription("Refund the employee travel fees")

                .log("Analysing travel expenses")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> {
                });


    }

}
