package fr.unice.polytech.esb.flows.cars;

import fr.unice.polytech.esb.flows.cars.data.CarInformation;
import fr.unice.polytech.esb.flows.cars.data.CarRequest;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class CheapestCar extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/cheapest-cars/").post("/search").type(Object.class).to(SEARCH_CHEAPEST_CAR);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<CarInformation>()));

        // Process to find the cheapest car.
        from(SEARCH_CHEAPEST_CAR)
                .routeId("search-the-cheapest-car")
                .routeDescription("Provides the most interesting car given the user request")

                .log("Generating a car research process")

                // Parse the body content from JSON string to CarRequest.
                .unmarshal().json(JsonLibrary.Jackson, CarRequest.class)

                // Send the request to all services.
                // With the custom aggregation strategy, the incoming cars
                // information lists will be merged.
                // All requests are awaited thanks to "parallelProcessing".
                .multicast(new JoinCarsAggregationStrategy())
                .parallelProcessing(true)
                .executorService(WORKERS)
                .timeout(1000)
                .to(SEARCH_IN_INTERNAL_CARS_SERVICE, SEARCH_IN_EXTERNAL_CARS_SERVICE)
                .end()

                // Read the whole list and keep the cheapest offer.
                .process(exchange -> {
                    List<CarInformation> cars = (List<CarInformation>) exchange.getIn().getBody(List.class);

                    if (cars.isEmpty()) {
                        // TODO: Do something if the list is empty.
                        exchange.getIn().setBody("{}");
                    } else {
                        // Sort by the price.
                        cars.sort(Comparator.comparingDouble(CarInformation::getPrice));

                        // Keep the cheapest car.
                        CarInformation cheapestFlight = cars.get(0);

                        // Write the car into the body.
                        exchange.getIn().setBody(cheapestFlight);
                    }
                })

                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
    }

    private class JoinCarsAggregationStrategy implements AggregationStrategy {

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
