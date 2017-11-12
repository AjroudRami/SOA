package fr.unice.polytech.esb.flows.utils;

public class Endpoints {

    // Servlet endpoints.
    public static final String SEARCH_CHEAPEST_FLIGHT = "activemq:bus-cheapest-flight";
    public static final String SEARCH_CHEAPEST_CAR = "activemq:bus-cheapest-car";
    public static final String SEARCH_CHEAPEST_HOTEL = "activemq:bus-cheapest-hotel";

    // Error handling.
    public static final String DEATH_POOL = "activemq:global:dead";

    // Error channels.
    public static final String DEAD_PARTNER = "activemq:deadPartners";

    // Business travel
    public static final String BUSINESS_TRAVEL_APPROVE = "activemq:business-travel-approve";
    public static final String BUSINESS_TRAVEL_CREATE = "activemq:business-travel-create";
    public static final String BUSINESS_TRAVEL_LIST = "activemq:business-travel-list";
    public static final String BUSINESS_TRAVEL_REFUND = "activemq:business-travel-refund";
    // Travel report
    public static final String TRAVEL_REPORT_LIST = "activemq:travel-report-list";
    public static final String TRAVEL_REPORT_APPROVE = "activemq:travel-report-approve";
    public static final String TRAVEL_REPORT_EXPENSE = "activemq:travel-report-expense";
    public static final String TRAVEL_REPORT_EXPLAIN = "activemq:travel-report-explain";
    public static final String TRAVEL_REPORT_CREATION = "activemq:travel-report-create";
    public static final String TRAVEL_REPORT_END = "activemq:travel-report-end";
    public static final String TRAVEL_REPORT_SAVE = "activemq:travel-report-save-file";
    public static final String TRAVEL_REPORT_FOLDER = "file:camel/output/";

    // Direct inner endpoints.
    public static final String SEARCH_IN_INTERNAL_FLIGHTS_SERVICE = "direct:internal-flight-service";
    public static final String SEARCH_IN_EXTERNAL_FLIGHTS_SERVICE = "direct:external-flight-service";

    public static final String SEARCH_IN_INTERNAL_HOTELS_SERVICE = "direct:internal-hotel-service";
    public static final String SEARCH_IN_EXTERNAL_HOTELS_SERVICE = "direct:external-hotel-service";

    public static final String SEARCH_IN_INTERNAL_CARS_SERVICE = "direct:internal-car-service";
    public static final String SEARCH_IN_EXTERNAL_CARS_SERVICE = "direct:external-car-service";


    // External partners.
    public static final String INTERNAL_FLIGHTS_ENDPOINT = "http://internal-flights:8080/tcs-service-flights/flights/";
    public static final String EXTERNAL_FLIGHTS_ENDPOINT = "http://external-flights:8080/tta-service-rpc/FlightBookingService";

    public static final String INTERNAL_HOTELS_ENDPOINT = "http://internal-hotels:8080/tcs-hotel-service/hotels";
    public static final String EXTERNAL_HOTELS_ENDPOINT = "http://external-hotels:8080/hotel-rpc/ExternalHotelFinderService";

    public static final String INTERNAL_CARS_ENDPOINT = "http://internal-cars:8080/tcs-cars-service/ExternalCarRentalService";
    public static final String EXTERNAL_CARS_ENDPOINT = "http://external-cars:8080/car-service-document/car";

    public static final String TRAVEL_REPORT_ENDPOINT = "http://travel-report:8080/tcs-service-travel-report/report/";
    public static final String BUSINESS_TRAVEL_ENDPOINT = "http://business-travels:8080/tcs-service-business-travels/registry/";

}
