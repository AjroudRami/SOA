package fr.unice.polytech.esb.flows.flights;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE;
import static fr.unice.polytech.esb.flows.utils.Endpoints.INPUT_FLIGHT_SEARCH;

public class FlightBookingProcess extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        /*
         * Flight Booking.
         * Read message from input and multicast the message
         */
        from(INPUT_FLIGHT_SEARCH)
                .routeId("calling-flight-booking")
                .routeDescription("Send requests to Flight Booking Services")

                .log("Generating a flight booking process")

                // TODO: Set the real values.
                .setProperty("from", simple("Paris"))
                .setProperty("to", simple("Lyon"))
                .setProperty("departureTimestamp", simple("123", Long.class))

                // Sends message to different endpoints with GroupedExchangeAggregationStrategy()
                // The strategy will allow the aggregator to wait for all reply
                .multicast(new GroupedExchangeAggregationStrategy())
                    .parallelProcessing()
                    .executorService(WORKERS)
                    .timeout(1000)
                // Forwards to service and wait for responses
                .inOut(SEARCH_IN_INTERNAL_FLIGHTS_SERVICE,
                        SEARCH_IN_EXTERNAL_FLIGHT_SERVICE)
                .end()
                // Process flight price
                // TODO
                .process("");
    }
}
