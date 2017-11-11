package fr.unice.polytech.esb.flows.reports.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class BusinessTravelApproval extends RouteBuilder{

    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/business-travel/").post("/approve").type(Object.class).to(BUSINESS_TRAVEL_APPROVE);

        // Process to approve business travel.
        from(BUSINESS_TRAVEL_APPROVE)
                .routeId("approve-business-travel")
                .routeDescription("Approve business travel and create travel report")

                .log("Generating a business approval process")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> {
                    ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
                    node.put("event","approve");
                    exchange.getIn().setBody(node.toString());
                })

                // Send the request to the internal service.
                .inOut(BUSINESS_TRAVEL_ENDPOINT)

                .choice()
                    .when(body().isNotNull())
                        .to(TRAVEL_REPORT_CREATION);
    }


}
