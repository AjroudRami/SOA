package fr.unice.polytech.esb.flows.reports.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import java.util.ArrayList;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class BusinessTravelCreation extends RouteBuilder{


    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/business-travel/").post("/create").type(Object.class).to(CREATE_BUSINESS_TRAVEL);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEATH_POOL)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<String>()));

        // Process to approve business travel.
        from(CREATE_BUSINESS_TRAVEL)
                .routeId("create-business-travel")
                .routeDescription("Create business travel for an employee")

                .log("Generating a business creation process")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                // Send the request to the internal service.
                .process(exchange -> {
                    ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
                    node.put("event","submit");
                    exchange.getIn().setBody(node.toString());
                })

                .inOut(BUSINESS_TRAVEL_ENDPOINT);
    }

}
