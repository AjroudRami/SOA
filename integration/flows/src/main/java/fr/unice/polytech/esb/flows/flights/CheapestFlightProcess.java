package fr.unice.polytech.esb.flows.flights;

import fr.unice.polytech.esb.flows.flights.data.FlightInformation;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_CHEAPEST_FLIGHT;

public class CheapestFlightProcess extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        /*
         * Flight Booking.
         * Read message from input and multicast the message
         */
        from(SEARCH_CHEAPEST_FLIGHT)
                .routeId("search-the-cheapest-flight")
                .routeDescription("Provides the most interesting flight given the user request")

                .log("Generating a flight booking process")

                // TODO: Set the real values.
                .setProperty("from", simple("Paris"))
                .setProperty("to", simple("Lyon"))
                .setProperty("departureTimestamp", simple("123", Long.class))

                // Send the request to all services.
                // With the custom aggregation strategy, the incoming flights
                // information lists will be merged.
                // All requests are awaited thanks to "parallelProcessing".
                .multicast(new JoinFlightInformationsAggregationStrategy())
                    .parallelProcessing(true)
                    .executorService(WORKERS)
                    .timeout(1000)
                // Forwards to service and wait for responses
                .to(SEARCH_IN_INTERNAL_FLIGHTS_SERVICE, SEARCH_IN_EXTERNAL_FLIGHT_SERVICE)
                .end()
                // Process flight price
                // TODO
                .process("");
    }

    private class JoinFlightInformationsAggregationStrategy implements AggregationStrategy {

        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
            if (oldExchange != null) {
                newExchange.getIn().getBody(List.class)
                        .addAll(oldExchange.getIn().getBody(List.class));
            }

            return newExchange;
        }
    }
}
