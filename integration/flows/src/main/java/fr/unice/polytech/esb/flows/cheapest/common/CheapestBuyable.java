package fr.unice.polytech.esb.flows.cheapest.common;

import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fr.unice.polytech.esb.flows.utils.Endpoints.DEAD_PARTNER;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public abstract class CheapestBuyable<T extends Buyable> extends RouteBuilder {

    private static final ExecutorService WORKERS = Executors.newFixedThreadPool(5);

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/" + getBuyableName() + "s/").post("/search").type(Object.class).to(getFlowName());

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<T>()));

        // Process to find the cheapest car.
        from(getFlowName())
                .routeId("search-the-cheapest-" + getBuyableName())
                .routeDescription("Provides the most interesting " + getBuyableName() + " given the user request")

                .log("Generating a " + getBuyableName() + " research process")

                // Parse the body content from JSON string to the buyable type.
                .unmarshal().json(JsonLibrary.Jackson)

                // Send the request to all services.
                // With the custom aggregation strategy, the incoming buyable
                // information lists will be merged.
                // All requests are awaited thanks to "parallelProcessing".
                .multicast(new BuyableAggregationStrategy())
                    .parallelProcessing(true)
                    .executorService(WORKERS)
                    .timeout(1000)
                    .to(getInternalFlowName(), getExternalFlowName())
                    .end()

                // Read the whole list and keep the cheapest offer.
                .process(new KeepCheapest<T>())

                // Marshal the body into a json array.
                .setHeader("Content-Type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
    }

    protected abstract String getFlowName();
    protected abstract String getBuyableName();
    protected abstract String getInternalFlowName();
    protected abstract String getExternalFlowName();
}
