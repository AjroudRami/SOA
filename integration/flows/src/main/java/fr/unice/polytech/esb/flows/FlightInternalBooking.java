package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.data.FlightInformation;
import fr.unice.polytech.esb.flows.data.Person;
import fr.unice.polytech.esb.flows.utils.FlightReservationHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class FlightInternalBooking extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {

        /*
         * Directs message to INTERNAL SERVICE
         */
        from(DIRECT_INTERNAL_FLIGHT_SERVICE)
                .routeId("call-internal-flight-reservation-service")
                .routeDescription("Call the internal flight reservation service")
                // process the message
                // TODO
                .setProperty("flight-reservation", simple("${body}"))
                .log("Creating retrieval request for citizen #${exchangeProperty[flight-reservation]}")

                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process((Exchange exchange) -> {
                    String request = "{\n" +
                            "  \"event\": \"list\",\n"+
                            "}";
                    exchange.getIn().setBody(request);
                })

                .inOut(INTERNAL_FLIGHT_SERVICE)
                // process the return message
                // TODO
                .unmarshal().json(JsonLibrary.Jackson, FlightInformation.class);
    }


}

