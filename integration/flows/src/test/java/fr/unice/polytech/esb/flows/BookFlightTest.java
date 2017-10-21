package fr.unice.polytech.esb.flows;

import fr.unice.polytech.esb.flows.utils.Endpoints;
import org.apache.camel.builder.RouteBuilder;

public class BookFlightTest extends ActiveMQTest {

    @Override public String isMockEndpointsAndSkip() {
        return Endpoints.REGISTRATION_ENDPOINT;
    }

    @Override protected RouteBuilder createRouteBuilder() throws Exception {
        return new FlightBookingProcess();
    }

}
