package fr.unice.polytech.esb.flows.reports;

import fr.unice.polytech.esb.flows.flights.data.FlightRequest;
import fr.unice.polytech.esb.flows.reports.data.ApproveTravel;
import org.apache.camel.ExchangeTimedOutException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.util.ArrayList;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class ReportManagement extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");
        rest("/approve-travel-report/").post("/search").type(Object.class).to(APPROVE_BUSINESS_TRAVEL);

        onException(ExchangeTimedOutException.class)
                .handled(true)
                .to(DEAD_PARTNER)
                // If the service does not respond, fill the body with an empty list.
                .process(exchange -> exchange.getIn().setBody(new ArrayList<ApproveTravel>()));

        // Process to approve business travel.
        from(APPROVE_BUSINESS_TRAVEL)
                .routeId("approve-business-travel")
                .routeDescription("Approve business travel and create travel report")

                .log("Generating a business approval process")

                // Parse the body content from JSON string to ApproveTravel.
                .unmarshal().json(JsonLibrary.Jackson, ApproveTravel.class)
                .to(BUSINESS_APPROVAL_QUEUE);
    }
}
