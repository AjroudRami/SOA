package fr.unice.polytech.esb.flows.cheapest.common;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;

/**
 * It is assumed that the incoming exchange contains a {@code List} of {@code Buyable}.
 *
 * If the {@code List} is empty, it puts an empty {@code Object} in the body.
 * Otherwise, it puts the cheapest from the {@code List}.
 *
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class KeepCheapest<T extends Buyable> implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        List<T> results = exchange.getIn().getBody(List.class);

        if (results == null || results.isEmpty()) {
            exchange.getIn().setBody(new Object());
        } else {
            T kept = results.get(0);
            for (T product : results) {
                if (kept.getPrice() > product.getPrice()) {
                    kept = product;
                }
            }

            exchange.getIn().setBody(kept);
        }
    }
}
