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

public class FlightReservation extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {

        /**
         * Directs message to INTERNAL SERVICE
         */
        from(DIRECT_INTERNAL_FLIGHT_SERVICE)
                .routeId("call-internal-flight-reservation-service")
                .routeDescription("Call the internal flight reservation service")
                // process the message
                // TODO
                .setProperty("flight-reserveation", simple("${body}"))
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



        /**
         * Directs message to EXTERNAL SERVICE
         */
        from(DIRECT_EXTERNAL_FLIGHT_SERVICE)
            .routeId("call-external-flight-reservation-service")
            .routeDescription("Call the external flight reservation service")
            // process the message
                .bean(FlightReservationHelper.class, "simpleReservation(${body}, ${exchangeProperty[req-uuid]})")
                .inOut(EXTERNAL_FLIGHT_SERVICE)
            // process the return message
            .process(result2FlightInformation);



    }

    private static Processor result2FlightInformation = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        Source response = (Source) exc.getIn().getBody();
        FlightInformation result = new FlightInformation(exc.getProperty("flight-info",FlightInformation.class));
        result.setDate(xpath.evaluate("//date/text()", response));
        result.setEndingAirport(xpath.evaluate("//endingAirport/text()", response));
        result.setStartingAirport(xpath.evaluate("//startingAirport/text()", response));
        result.setPrice(Float.parseFloat(xpath.evaluate("//price/text()", response)));
        exc.getIn().setBody(result);
    };
}
