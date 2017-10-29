package fr.unice.polytech.esb.flows.hotels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import fr.unice.polytech.esb.flows.hotels.data.HotelInformation;
import fr.unice.polytech.esb.flows.hotels.data.HotelRequest;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.INTERNAL_HOTELS_ENDPOINT;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_INTERNAL_HOTELS_SERVICE;

public class CheapestHotelInternal extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(SEARCH_IN_INTERNAL_HOTELS_SERVICE)
                // Route description.
                .routeId("call-external-hotel-reservation-service")
                .routeDescription("Call the external hotel reservation service")

                // Log the current action.
                .log("Make a research in the EXTERNAL hotels service.")

                // Prepare the query parameters in the exchange properties.
                .process(exchange -> {
                    HotelRequest request = exchange.getIn().getBody(HotelRequest.class);

                    // Query parameters of the GET request.
                    String queryParameters = String.format(
                            "?destination=%s&date=%s",
                            request.getDestination(),
                            new SimpleDateFormat("dd-MM-yyyy").format(new Date(request.getTimestamp())));

                    exchange.setProperty("queryParameters", queryParameters);
                })

                // Prepare the POST request to a RPC service.
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setHeader("Content-Type", constant("application/soap+xml"))
                .setHeader("Accept", constant("application/json"))
                .setBody(simple(""))

                // Send the request to the external service.
                .inOut(INTERNAL_HOTELS_ENDPOINT + "${exchangeProperty[queryParameters]}")

                // Remove the query parameters from the exchange properties.
                .removeProperty("queryParameters")

                // Parse the JSON response into a list of hotels and
                // put it as the body.
                .process(exchange -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    JsonNode hotelsNode = objectMapper
                            .readTree(exchange.getIn().getBody(String.class));

                    ObjectReader reader = objectMapper.readerFor(
                            new TypeReference<List<HotelInformation>>() {
                                // Nothing to implement.
                            });

                    List<HotelInformation> hotelsInformation = reader.readValue(hotelsNode);

                    exchange.getIn().setBody(hotelsInformation);
                });
    }
}
