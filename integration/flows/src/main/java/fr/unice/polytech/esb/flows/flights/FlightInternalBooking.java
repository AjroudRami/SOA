package fr.unice.polytech.esb.flows.flights;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import fr.unice.polytech.esb.flows.flights.data.FlightInformation;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.INTERNAL_FLIGHTS_ENDPOINT;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE;

/**
 * Route for getting a list of flights from the internal service (Team #1).
 *
 * When entering the route, the properties "from", "to" and "departure" are
 * supposed to be already set up.
 */
public class FlightInternalBooking extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(SEARCH_IN_INTERNAL_FLIGHTS_SERVICE)
                // Route description.
                .routeId("internal-flight-service-booking")
                .routeDescription("Get booking results from the internal flight service")

                // Log the current action.
                .log("Make a research in the INTERNAL flight service with parameters: [ " +
                        "from: ${exchangeProperty[from]}; " +
                        "to: ${exchangeProperty[to]}; " +
                        "departure: ${exchangeProperty[departureTimestamp]} " +
                        "]")

                // Prepare the POST request to a document service.
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> {
                    String request = makeBody(
                            exchange.getProperty("from", String.class),
                            exchange.getProperty("to", String.class),
                            exchange.getProperty("departureTimeStamp", Long.class));

                    exchange.getIn().setBody(request);
                })

                // Send the request to the internal service.
                .inOut(INTERNAL_FLIGHTS_ENDPOINT)

                // Process the returned value.
                .process(exchange -> {
                    ObjectMapper objectMapper = new ObjectMapper();

                    JsonNode flightsNode = objectMapper
                            .readTree(exchange.getIn().getBody(String.class))
                            .get("flights");

                    ObjectReader reader = objectMapper.readerFor(
                            new TypeReference<List<FlightInformation>>() {
                                // Nothing to implement.
                            });

                    List<FlightInformation> flightsInformation = reader.readValue(flightsNode);

                    exchange.getIn().setBody(flightsInformation);
                });
    }

    /**
     * Prepares the body for the internal service request.
     * @param from The departure airport.
     * @param to The destination airport.
     * @param departureTimestamp The date of departure.
     * @return A JSON object string.
     */
    private static String makeBody(String from, String to, long departureTimestamp) {
        return String.format(
                "{" +
                "event: list," +
                "departure: \"%s\"," +
                "destination: \"%s\"," +
                "departureTimestamp: %d" +
                "}",
                from, to, departureTimestamp);
    }

}

