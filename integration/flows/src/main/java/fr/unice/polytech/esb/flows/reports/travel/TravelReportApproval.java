package fr.unice.polytech.esb.flows.reports.travel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.reports.travel.processor.TravelReportValidationProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class TravelReportApproval extends RouteBuilder{


    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/travel-report/").post("/approve").type(Object.class).to(TRAVEL_REPORT_APPROVE);

        // Process to approve business travel.
        from(TRAVEL_REPORT_APPROVE)
                .routeId("travel-report-approval")
                .routeDescription("Approve travel report")

                .log("Generating a travel approval process")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> {
                    ObjectNode node = mapper.readValue(exchange.getIn().getBody(String.class),ObjectNode.class);
                    node.put("event","validate");
                    exchange.getIn().setBody(node.toString());
                })

                // Send the request to the internal service.
                .to(TRAVEL_REPORT_ENDPOINT)

                // Process the returned value.
                .process(new TravelReportValidationProcessor())

                .choice()
                .when(header("status").isEqualTo("ACCEPTED"))
                    .multicast()
                        .parallelProcessing(true)
                        .executorService(WORKERS)
                        .to(TRAVEL_REPORT_SAVE, TRAVEL_REFUND)

                .end();
    }

}
