package fr.unice.polytech.esb.flows.flights;

import fr.unice.polytech.esb.flows.flights.data.FlightInformation;
import fr.unice.polytech.esb.flows.flights.data.FlightRequest;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class CheapestFlight extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        // Process to find the cheapest flight.
        from(SEARCH_CHEAPEST_FLIGHT)
                .routeId("search-the-cheapest-flight")
                .routeDescription("Provides the most interesting flight given the user request")

                .log("Generating a flight booking process")

                // Parse the body content from JSON string to FlightRequest.
                .unmarshal().json(JsonLibrary.Jackson, FlightRequest.class)

                // Send the request to all services.
                // With the custom aggregation strategy, the incoming flights
                // information lists will be merged.
                // All requests are awaited thanks to "parallelProcessing".
                .multicast(new JoinFlightInformationsAggregationStrategy())
                    .parallelProcessing(true)
                    .executorService(WORKERS)
                    .timeout(1000)
                    .to(SEARCH_IN_INTERNAL_FLIGHTS_SERVICE, SEARCH_IN_EXTERNAL_FLIGHT_SERVICE)
                    .end()

                // Read the whole list and keep the cheapest offer.
                .process(exchange -> {
                    List<FlightInformation> flights = (List<FlightInformation>) exchange.getIn().getBody(List.class);

                    if (flights.isEmpty()) {
                        // TODO: Do something is the list is empty.
                        exchange.getIn().setBody("{}");
                    } else {
                        // Sort by the price.
                        flights.sort((left, right) -> Float.compare(left.getPrice(), right.getPrice()));

                        // Keep the cheapest flight.
                        FlightInformation cheapestFlight = flights.get(0);

                        // Write the flight into the body.
                        exchange.getIn().setBody(cheapestFlight);
                    }
                })

                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
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
