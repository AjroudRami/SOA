package fr.unice.polytech.esb.flows.flights;

import fr.unice.polytech.esb.flows.flights.data.FlightInformation;
import fr.unice.polytech.esb.flows.utils.FlightReservationHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.EXTERNAL_FLIGHTS_ENDPOINT;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;

public class CheapestFlightExternal extends RouteBuilder {
    
    @Override
    public void configure() throws Exception {
        from(SEARCH_IN_EXTERNAL_FLIGHT_SERVICE)
                .routeId("call-external-flight-reservation-service")
                .routeDescription("Call the external flight reservation service")
                // process the message
                .bean(FlightReservationHelper.class, "simpleReservation(${body}, ${exchangeProperty[req-uuid]})")
                .inOut(EXTERNAL_FLIGHTS_ENDPOINT)
                // process the return message
                .process(result2FlightInformation);
    }

    private static Processor translateIntoFlightsInformation = exchange -> {
        XPath xPath = XPathFactory.newInstance().newXPath();
        Source response = (Source) exchange.getIn().getBody();

        List<FlightInformation> flightsInformation = new ArrayList<>();

        // TODO: Fill the list with the body.

        exchange.getIn().setBody(flightsInformation);
    };

    // TODO: Should be removed because it does not parse the right response type. (it parses the booking one, not the research response)
    @Deprecated
    private static Processor result2FlightInformation = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        Source response = (Source) exc.getIn().getBody();
        FlightInformation result = new FlightInformation(exc.getProperty("flight-info", FlightInformation.class));
        result.setDepartureTimestamp(Long.parseLong(xpath.evaluate("//date/text()", response)));
        result.setEndingAirport(xpath.evaluate("//endingAirport/text()", response));
        result.setStartingAirport(xpath.evaluate("//startingAirport/text()", response));
        result.setPrice(Float.parseFloat(xpath.evaluate("//price/text()", response)));
        exc.getIn().setBody(result);
    };
}
