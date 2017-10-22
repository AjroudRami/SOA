package fr.unice.polytech.esb.flows.utils;

public class Endpoints {

    // Provided routes.
    public static final String SEARCH_CHEAPEST_FLIGHT = "servlet://bus-cheapest-flight";

    // Direct inner endpoints.
    public static final String SEARCH_IN_INTERNAL_FLIGHTS_SERVICE = "direct:internal-flight-service";
    public static final String SEARCH_IN_EXTERNAL_FLIGHT_SERVICE = "direct:external-flight-service";

    // External partners.
    public static final String INTERNAL_FLIGHTS_ENDPOINT = "http://localhost:8080/tcs-service-flights/flights/";
    public static final String EXTERNAL_FLIGHTS_ENDPOINT = "http://localhost:8081/tta-service-rpc/FlightBookingService";
}
