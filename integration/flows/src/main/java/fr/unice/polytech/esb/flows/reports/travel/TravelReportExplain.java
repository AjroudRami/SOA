package fr.unice.polytech.esb.flows.reports.travel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

/**
 * Add explanation to the travel report
 */
public class TravelReportExplain extends RouteBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/travel-report/").post("/explain").type(Object.class).to(TRAVEL_REPORT_EXPLAIN);

        // Process to add explanation travel report.
        from(TRAVEL_REPORT_EXPLAIN)
                .routeId("travel-report-explanation")
                .routeDescription("Add explanation to a travel report")

                .log("Generating a travel report explain")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> {
                    ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
                    node.put("event","explain");
                    exchange.getIn().setBody(node.toString());
                })
                // Send the request to the internal service.
                .to(TRAVEL_REPORT_ENDPOINT);
    }
}
