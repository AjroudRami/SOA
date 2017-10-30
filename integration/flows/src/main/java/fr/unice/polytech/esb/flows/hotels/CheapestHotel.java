package fr.unice.polytech.esb.flows.hotels;

import fr.unice.polytech.esb.flows.flights.data.FlightInformation;
import fr.unice.polytech.esb.flows.hotels.data.HotelInformation;
import fr.unice.polytech.esb.flows.hotels.data.HotelRequest;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class CheapestHotel extends RouteBuilder{

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");
        rest("/cheapest-hotels/").post("/search").type(Object.class).to(SEARCH_CHEAPEST_HOTEL);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));

        // Process to find the cheapest flight.
        from(SEARCH_CHEAPEST_HOTEL)
                .routeId("search-the-cheapest-hotel")
                .routeDescription("Provides the most interesting hotel given the user request")

                .log("Generating a hotel booking process")

                // Parse the body content from JSON string to FlightRequest.
                .unmarshal().json(JsonLibrary.Jackson, HotelRequest.class)

                // Send the request to all services.
                // With the custom aggregation strategy, the incoming hotels
                // information lists will be merged.
                // All requests are awaited thanks to "parallelProcessing".
                .multicast(new JoinHotelInfoAggregationStrategy())
                .parallelProcessing(true)
                .executorService(WORKERS)
                .timeout(1000)
                .to(SEARCH_IN_INTERNAL_HOTELS_SERVICE, SEARCH_IN_EXTERNAL_HOTELS_SERVICE)
                .end()

                // Read the whole list and keep the cheapest offer.
                .process(exchange -> {
                    List<HotelInformation> hotels = (List<HotelInformation>) exchange.getIn().getBody(List.class);

                    if (hotels.isEmpty()) {
                        // TODO: Do something if the list is empty.
                        exchange.getIn().setBody("{}");
                    } else {
                        // Sort by the price.
                        hotels.sort((left, right) -> Float.compare(left.getPrice(), right.getPrice()));

                        // Keep the cheapest flight.
                        HotelInformation cheapestHotel = hotels.get(0);

                        // Write the flight into the body.
                        exchange.getIn().setBody(cheapestHotel);
                    }
                })

                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
    }

    private class JoinHotelInfoAggregationStrategy implements AggregationStrategy {

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
