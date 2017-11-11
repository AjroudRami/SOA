package fr.unice.polytech.esb.flows.cheapest.flights;

import fr.unice.polytech.esb.flows.cheapest.common.CheapestBuyable;
import fr.unice.polytech.esb.flows.cheapest.flights.data.FlightInformation;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
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
}
