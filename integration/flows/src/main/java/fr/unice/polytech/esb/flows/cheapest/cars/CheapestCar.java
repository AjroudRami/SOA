package fr.unice.polytech.esb.flows.cheapest.cars;

import fr.unice.polytech.esb.flows.cheapest.cars.data.CarInformation;
import fr.unice.polytech.esb.flows.cheapest.common.CheapestBuyable;

import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_CHEAPEST_CAR;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_CARS_SERVICE;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_INTERNAL_CARS_SERVICE;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class CheapestCar extends CheapestBuyable<CarInformation> {

    @Override
    protected String getFlowName() {
        return SEARCH_CHEAPEST_CAR;
    }

    @Override
    protected String getBuyableName() {
        return "car";
    }

    @Override
    protected String getInternalFlowName() {
        return SEARCH_IN_INTERNAL_CARS_SERVICE;
    }

    @Override
    protected String getExternalFlowName() {
        return SEARCH_IN_EXTERNAL_CARS_SERVICE;
    }
}
