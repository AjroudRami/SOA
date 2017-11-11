package fr.unice.polytech.esb.flows.cheapest.hotels;

import fr.unice.polytech.esb.flows.cheapest.common.CheapestBuyable;
import fr.unice.polytech.esb.flows.cheapest.hotels.data.HotelInformation;

import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_CHEAPEST_HOTEL;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_HOTELS_SERVICE;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_INTERNAL_HOTELS_SERVICE;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class CheapestHotel extends CheapestBuyable<HotelInformation> {

    @Override
    protected String getFlowName() {
        return SEARCH_CHEAPEST_HOTEL;
    }

    @Override
    protected String getBuyableName() {
        return "hotel";
    }

    @Override
    protected String getInternalFlowName() {
        return SEARCH_IN_INTERNAL_HOTELS_SERVICE;
    }

    @Override
    protected String getExternalFlowName() {
        return SEARCH_IN_EXTERNAL_HOTELS_SERVICE;
    }
}
