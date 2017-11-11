package fr.unice.polytech.esb.flows.cheapest.common;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class BuyableAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange != null) {
            List responses = oldExchange.getIn().getBody(List.class);

            newExchange.getIn().getBody(List.class).addAll(responses);
        }

        return newExchange;
    }
}
