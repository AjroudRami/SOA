package fr.unice.polytech.esb.flows.reports.travel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class TravelReportEnd extends RouteBuilder{


    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
            restConfiguration().component("servlet");
            rest("/travel-report/").post("/end").type(Object.class).to(TRAVEL_REPORT_END);

        // End travel report
        from(TRAVEL_REPORT_END)
        .routeId("travel-report-end")
        .routeDescription("End the travel")

        // Log the current action.
        .log("Ending a travel report.")

        // Prepare the POST request to a document service.
        .removeHeaders("*")
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        .setHeader("Content-Type", constant("application/json"))
        .setHeader("Accept", constant("application/json"))
        .process(exchange -> {
            ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
            node.put("event","end");
            exchange.getIn().setBody(node.toString());
        })
        // Send the request to the internal service.
        .inOut(TRAVEL_REPORT_ENDPOINT)

        // Process the returned value.
        .process(exchange -> {
            ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);

            ObjectReader reader = mapper.readerFor(
                    new TypeReference<String>() {
                        // Nothing to implement.
                    });

            String status = reader.readValue(node.get("status"));

            if(status.equals("ACCEPTED")){
                exchange.getIn().setHeader("status","ACCEPTED");
            }
            else {
                exchange.getIn().setHeader("status", "REJECTED");
            }
            exchange.getIn().setBody(node.toString());
        })

        .choice()
            .when(header("status").isEqualTo("ACCEPTED"))
                .multicast()
                .parallelProcessing(true)
                .executorService(WORKERS)
                .to(TRAVEL_REPORT_SAVE, TRAVEL_REFUND)

        .end();
    }
}
