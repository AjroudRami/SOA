package fr.unice.polytech.esb.flows.cars;

import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_CHEAPEST_CAR;

public class CheapestCar extends RouteBuilder{


    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/cheapest-car/").post("/search").type(Object.class).to(SEARCH_CHEAPEST_CAR);

    }
}
