package fr.unice.polytech.esb.flows.utils;

public class Endpoints {

    // Servlet endpoints.
    public static final String SEARCH_CHEAPEST_FLIGHT = "servlet://bus-cheapest-flight";

    // Error handling.
    public static final String DEATH_POOL = "activemq:global:dead";

    // Error channels.
    public static final String DEAD_PARTNER = "activemq:deadPartners";

    // Direct inner endpoints.
    public static final String SEARCH_IN_INTERNAL_FLIGHTS_SERVICE = "direct:internal-flight-service";
    public static final String SEARCH_IN_EXTERNAL_FLIGHT_SERVICE = "direct:external-flight-service";

    // External partners.
    public static final String INTERNAL_FLIGHTS_ENDPOINT = "http://localhost:8080/tcs-service-flights/flights/";
    public static final String EXTERNAL_FLIGHTS_ENDPOINT = "http://localhost:8081/tta-service-rpc/FlightBookingService";
    public static final String TRAVEL_REPORT_ENDPOINT = "";
    public static final String BUSINESS_TRAVEL_ENDPOINT = "";
}
