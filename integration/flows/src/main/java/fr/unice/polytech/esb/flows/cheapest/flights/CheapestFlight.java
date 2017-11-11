package fr.unice.polytech.esb.flows.cheapest.flights;

import fr.unice.polytech.esb.flows.cheapest.common.CheapestBuyable;
import fr.unice.polytech.esb.flows.cheapest.flights.data.FlightInformation;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

/**
 *
 */
public class CheapestFlight extends CheapestBuyable<FlightInformation> {

    @Override
    protected String getFlowName() {
        return SEARCH_CHEAPEST_FLIGHT;
    }

    @Override
    protected String getBuyableName() {
        return "flight";
    }

    @Override
    protected String getInternalFlowName() {
        return SEARCH_IN_INTERNAL_FLIGHTS_SERVICE;
    }

    @Override
    protected String getExternalFlowName() {
        return SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;
    }

    /*@Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/cheapest-flights/").post("/search").type(Object.class).to(SEARCH_CHEAPEST_FLIGHT);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<FlightInformation>()));

        // Process to find the cheapest buyable.
        from(SEARCH_CHEAPEST_FLIGHT)
                .routeId("search-the-cheapest-flight")
                .routeDescription("Provides the most interesting flight given the user request")

                .log("Generating a flight booking process")

                // Parse the body content from JSON string to FlightRequest.
                .unmarshal().json(JsonLibrary.Jackson)

                // Send the request to all services.
                // With the custom aggregation strategy, the incoming flights
                // information lists will be merged.
                // All requests are awaited thanks to "parallelProcessing".
                .multicast(new BuyableAggregationStrategy())
                    .parallelProcessing(true)
                    .executorService(WORKERS)
                    .timeout(1000)
                    .to(SEARCH_IN_INTERNAL_FLIGHTS_SERVICE, SEARCH_IN_EXTERNAL_FLIGHT_SERVICE)
                    .end()

                // Read the whole list and keep the cheapest offer.
                .process(new KeepCheapest<FlightInformation>())

                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
    }*/
}
