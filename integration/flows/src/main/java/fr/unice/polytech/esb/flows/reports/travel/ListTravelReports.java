package fr.unice.polytech.esb.flows.reports.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class ListTravelReports extends RouteBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/travel-report/").post("/list").type(Object.class).to(LIST_TRAVEL_REPORT);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEATH_POOL)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<String>()));

        // Process to approve business travel.
        from(LIST_TRAVEL_REPORT)
                .routeId("list-travel-report")
                .routeDescription("List travel report")

                .log("Generating a travel query")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> {
                    ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
                    node.put("event","list");
                    exchange.getIn().setBody(node.toString());
                })

                // Send the request to the internal service.
                .to(TRAVEL_REPORT_ENDPOINT);
    }
}
