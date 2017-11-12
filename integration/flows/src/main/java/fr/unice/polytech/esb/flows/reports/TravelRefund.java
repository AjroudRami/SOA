package fr.unice.polytech.esb.flows.reports;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import static fr.unice.polytech.esb.flows.utils.Endpoints.*;

public class TravelRefund extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        from(TRAVEL_REFUND)
                .routeId("refund a travel")
                .routeDescription("Refund the employee travel fees")

                .log("The travel expenses will be refund")

                .log(body().toString());
    }

}
