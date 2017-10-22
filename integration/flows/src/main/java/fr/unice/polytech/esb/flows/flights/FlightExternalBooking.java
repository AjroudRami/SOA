package fr.unice.polytech.esb.flows.flights;

import fr.unice.polytech.esb.flows.data.FlightInformation;
import fr.unice.polytech.esb.flows.utils.FlightReservationHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import javax.xml.transform.Source;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;
import static fr.unice.polytech.esb.flows.utils.Endpoints.EXTERNAL_FLIGHTS_ENDPOINT;

public class FlightExternalBooking extends RouteBuilder {
    
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

    private static Processor result2FlightInformation = (Exchange exc) -> {
        XPath xpath = XPathFactory.newInstance().newXPath();
        Source response = (Source) exc.getIn().getBody();
        FlightInformation result = new FlightInformation(exc.getProperty("flight-info", FlightInformation.class));
        result.setDate(xpath.evaluate("//date/text()", response));
        result.setEndingAirport(xpath.evaluate("//endingAirport/text()", response));
        result.setStartingAirport(xpath.evaluate("//startingAirport/text()", response));
        result.setPrice(Float.parseFloat(xpath.evaluate("//price/text()", response)));
        exc.getIn().setBody(result);
    };
}
