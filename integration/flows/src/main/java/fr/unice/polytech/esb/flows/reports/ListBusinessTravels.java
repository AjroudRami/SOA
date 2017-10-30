package fr.unice.polytech.esb.flows.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.reports.data.ApproveTravel;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class ListBusinessTravels extends RouteBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/business-travel/").post("/list").type(Object.class).to(LIST_BUSINESS_TRAVEL);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEATH_POOL)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<ApproveTravel>()));

        // Process to approve business travel.
        from(LIST_BUSINESS_TRAVEL)
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
