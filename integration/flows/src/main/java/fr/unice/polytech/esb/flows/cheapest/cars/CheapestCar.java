package fr.unice.polytech.esb.flows.cheapest.cars;

import fr.unice.polytech.esb.flows.cheapest.cars.data.CarInformation;
import fr.unice.polytech.esb.flows.cheapest.cars.data.CarRequest;
import fr.unice.polytech.esb.flows.cheapest.common.BuyableAggregationStrategy;
import fr.unice.polytech.esb.flows.cheapest.common.KeepCheapest;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.ArrayList;
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
                .multicast(new BuyableAggregationStrategy())
                .parallelProcessing(true)
                .executorService(WORKERS)
                .timeout(1000)
                .to(SEARCH_IN_INTERNAL_CARS_SERVICE, SEARCH_IN_EXTERNAL_CARS_SERVICE)
                .end()

                // Read the whole list and keep the cheapest offer.
                .process(new KeepCheapest<CarInformation>())

                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
    }
}
