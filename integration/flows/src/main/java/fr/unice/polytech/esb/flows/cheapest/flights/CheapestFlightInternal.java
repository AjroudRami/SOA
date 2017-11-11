package fr.unice.polytech.esb.flows.cheapest.flights;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.unice.polytech.esb.flows.cheapest.flights.data.FlightInformation;
import fr.unice.polytech.esb.flows.cheapest.flights.data.FlightRequest;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.INTERNAL_FLIGHTS_ENDPOINT;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_INTERNAL_FLIGHTS_SERVICE;

/**
 * Route for getting a list of flights from the internal service (Team #1).
 *
 * It is assumed that the body is a FlightRequest.
 */
public class CheapestFlightInternal extends RouteBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure() throws Exception {
        from(SEARCH_IN_INTERNAL_FLIGHTS_SERVICE)
                // Route description.
                .routeId("internal-flight-service-booking")
                .routeDescription("Get booking results from the internal flight service")

                // Log the current action.
                .log("Make a research in the INTERNAL flights service.")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeBody(exchange.getIn().getBody(FlightRequest.class))))

                // Send the request to the internal service.
                .inOut(INTERNAL_FLIGHTS_ENDPOINT)

                // Process the returned value.
                .process(exchange -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
     * @param flightRequest The flight request which is translated.
     * @return A JSON object string.
     */
    private static String makeBody(FlightRequest flightRequest) {
        ObjectNode json = mapper.createObjectNode();
        json.put("event", "list");
        json.put("departure", flightRequest.getFrom());
        json.put("departureTimeStamp", flightRequest.getDeparture());
        json.put("destination", flightRequest.getTo());
        return json.toString();
    }
}

