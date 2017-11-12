package fr.unice.polytech.esb.flows.reports.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class TravelReportList extends RouteBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/travel-report/").post("/list").type(Object.class).to(TRAVEL_REPORT_LIST);

        // Process to approve business travel.
        from(TRAVEL_REPORT_LIST)
                .routeId("travel-report-list")
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
