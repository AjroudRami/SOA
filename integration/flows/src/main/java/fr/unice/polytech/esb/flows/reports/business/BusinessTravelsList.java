package fr.unice.polytech.esb.flows.reports.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class BusinessTravelsList extends RouteBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/business-travel/").post("/list").type(Object.class).to(BUSINESS_TRAVEL_LIST);

        // Process to approve business travel.
        from(BUSINESS_TRAVEL_LIST)
                .routeId("list-business-travel")
                .routeDescription("List business travel ")

                .log("Generating a business travel query")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                // Send the request to the internal service.
                .process(exchange -> {
                    ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
                    node.put("event","list");
                    exchange.getIn().setBody(node.toString());
                })

                .inOut(BUSINESS_TRAVEL_ENDPOINT);
    }
}
