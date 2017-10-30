package fr.unice.polytech.esb.flows.utils;

public class Endpoints {

    // Servlet endpoints.
    public static final String SEARCH_CHEAPEST_FLIGHT = "activemq:bus-cheapest-flight";
    public static final String SEARCH_CHEAPEST_CAR = "activemq:bus-cheapest-car";
    public static final String SEARCH_CHEAPEST_HOTEL = "activemq:bu-cheapest-hotel";

    // Error handling.
    public static final String DEATH_POOL = "activemq:global:dead";

    // Error channels.
    public static final String DEAD_PARTNER = "activemq:deadPartners";

    // Business / Travel report
    public static final String APPROVE_BUSINESS_TRAVEL = "activemq:approve-business-travel";
    public static final String CREATE_BUSINESS_TRAVEL = "activemq:create-business-travel";
    public static final String LIST_BUSINESS_TRAVEL = "activemq:list-business-travel";
    public static final String LIST_TRAVEL_REPORT = "activemq:list-travel-report";
    public static final String APPROVE_TRAVEL_REPORT = "activemq:approve-travel-report";
    public static final String TRAVEL_REPORT_CREATION = "activemq:create-travel-report";

    // Direct inner endpoints.
    public static final String SEARCH_IN_INTERNAL_FLIGHTS_SERVICE = "direct:internal-flight-service";
    public static final String SEARCH_IN_EXTERNAL_FLIGHT_SERVICE = "direct:external-flight-service";


    public static final String SEARCH_IN_INTERNAL_HOTELS_SERVICE = "direct:internal-hotel-service";
    public static final String SEARCH_IN_EXTERNAL_HOTELS_SERVICE = "direct:external-hotel-service";

    // External partners.
    public static final String INTERNAL_FLIGHTS_ENDPOINT = "http://localhost:8080/tcs-service-flights/flights/";
    public static final String EXTERNAL_FLIGHTS_ENDPOINT = "http://localhost:8081/tta-service-rpc/FlightBookingService";

    public static final String INTERNAL_HOTELS_ENDPOINT = "http://travel-report:8080/tcs-service-travel-report/report/";
    public static final String EXTERNAL_HOTELS_ENDPOINT = "http://travel-report:8080/tcs-service-travel-report/report/";

    public static final String TRAVEL_REPORT_ENDPOINT = "http://travel-report:8080/tcs-service-travel-report/report/";
    public static final String BUSINESS_TRAVEL_ENDPOINT = "http://business-travels:8080/tcs-service-business-travels/registry/";
}
