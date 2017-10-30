package fr.unice.polytech.esb.flows.reports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import fr.unice.polytech.esb.flows.reports.data.ApproveTravel;
import fr.unice.polytech.esb.flows.reports.data.BusinessTravel;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.ArrayList;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class BusinessTravelCreation extends RouteBuilder{


    private static ObjectMapper mapper = new ObjectMapper();
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");
        rest("/create-business-travel/").post("/search").type(Object.class).to(CREATE_BUSINESS_TRAVEL);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEATH_POOL)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<ApproveTravel>()));

        // Process to approve business travel.
        from(CREATE_BUSINESS_TRAVEL)
                .routeId("create-business-travel")
                .routeDescription("Create business travel for an employee")

                .log("Generating a business creation process")
                .unmarshal().json(JsonLibrary.Jackson, BusinessTravel.class)

                // Prepare the POST request to a document service.
                .removeHeaders("*")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeBody(exchange.getIn().getBody(BusinessTravel.class))))

                // Send the request to the internal service.
                .inOut(BUSINESS_TRAVEL_ENDPOINT);
    }

    private static String makeBody(BusinessTravel businessTravel) throws JsonProcessingException {
        ObjectNode json = mapper.createObjectNode();
        json.put("event", "submit");
        json.put("nights", mapper.writeValueAsString(businessTravel.getHotel()));
        json.put("tickets", mapper.writeValueAsString(businessTravel.getTickets()));
        return json.toString();
    }
}
