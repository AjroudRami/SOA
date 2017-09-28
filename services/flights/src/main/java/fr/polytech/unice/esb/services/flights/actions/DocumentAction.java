package fr.polytech.unice.esb.services.flights.actions;

import java.util.Map;

/**
 * A document action
 * @param <I> input type
 * @param <O> output type
 */
public interface DocumentAction<I,O> {

    /**
     * Executes the given action
     * @param document
     * @return
     */
    O execute(I document);

    /**
     *
     * @return the input type
     */
    Class<I> getInputType();
}
