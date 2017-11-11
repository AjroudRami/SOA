package fr.unice.polytech.esb.flows.cheapest.cars;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import fr.unice.polytech.esb.flows.cheapest.cars.data.CarInformation;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.EXTERNAL_CARS_ENDPOINT;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_CARS_SERVICE;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class CheapestCarExternal extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(SEARCH_IN_EXTERNAL_CARS_SERVICE)
                // Route description.
                .routeId("external-car-service-booking")
                .routeDescription("Get booking results from the external car service")

                // Log the current action.
                .log("Make a research in the EXTERNAL cars service.")

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))

                .marshal().json(JsonLibrary.Jackson)

                // Send the request to the internal service.
                .inOut(EXTERNAL_CARS_ENDPOINT)

                // Process the returned value.
                .process(exchange -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    JsonNode carsNode = objectMapper
                            .readTree(exchange.getIn().getBody(String.class))
                            .get("result");

                    ObjectReader reader = objectMapper.readerFor(
                            new TypeReference<List<CarInformation>>() {
                                // Nothing to implement.
                            });

                    List<CarInformation> carsInformation = reader.readValue(carsNode);

                    exchange.getIn().setBody(carsInformation);
                });
    }
}
