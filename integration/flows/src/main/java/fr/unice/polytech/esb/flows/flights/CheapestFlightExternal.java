package fr.unice.polytech.esb.flows.flights;

import fr.unice.polytech.esb.flows.flights.data.FlightInformation;
import fr.unice.polytech.esb.flows.flights.data.FlightRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.EXTERNAL_FLIGHTS_ENDPOINT;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_FLIGHT_SERVICE;

public class CheapestFlightExternal extends RouteBuilder {

    private static String DEFAULT_USER = "DEFAULT";
    
    @Override
    public void configure() throws Exception {
        from(SEARCH_IN_EXTERNAL_FLIGHT_SERVICE)
                // Route description.
                .routeId("call-external-flight-reservation-service")
                .routeDescription("Call the external flight reservation service")

                // Log the current action.
                .log("Make a research in the EXTERNAL flights service.")

                // Prepare the POST request to a RPC service.
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/soap+xml"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeRequestBody(exchange.getIn().getBody(FlightRequest.class))))

                // Send the request to the external service.
                .inOut(EXTERNAL_FLIGHTS_ENDPOINT)

                // Parse the SOAP response into a list of flights and
                // put it as the body.
                .process(translateFromResponse);
    }

    private static Processor translateFromResponse = exchange -> {
        List<FlightInformation> flightsInformation = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new InputSource(new StringReader(exchange.getIn().getBody(String.class))));

        NodeList xmlFlights = document.getElementsByTagName("booking_info");

        for (int i = 0; i < xmlFlights.getLength(); i++) {
            // TODO: Make this step better (other way to parse XML?).
            flightsInformation.add(fromNode(xmlFlights.item(i)));
        }

        exchange.getIn().setBody(flightsInformation);
    };

    private static FlightInformation fromNode(Node xmlFlight) throws ParseException {
        NodeList xmlFlightNodes = xmlFlight.getChildNodes();

        Float price = null;
        String from = null, to = null;
        Long departureTimestamp = null;

        for (int j = 0; j < xmlFlightNodes.getLength(); j++) {
            Node xmlNode = xmlFlightNodes.item(j);

            switch (xmlNode.getNodeName()) {
                case "date":
                    departureTimestamp = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
                            .parse(xmlNode.getNodeValue())
                            .getTime();
                    break;
                case "endingAirport":
                    to = xmlNode.getNodeValue();
                    break;
                case "price":
                    price = Float.parseFloat(xmlNode.getNodeValue());
                    break;
                case "startingAirport":
                    from = xmlNode.getNodeValue();
                    break;
            }
        }

        return new FlightInformation(departureTimestamp, from, price, to);
    }

    private static String makeRequestBody(FlightRequest flightRequest) {
        // Translate the timestamp into a date in format "YYYY-MM-DD".
        Date date = new Date(flightRequest.getDeparture());
        String departureTime = new SimpleDateFormat("yyyy-MM-dd").format(date);

        // TODO: Add something to translate the city of the FlightRequest to the corresponding country.
        String originCountry = flightRequest.getFrom();
        String destinationCountry = flightRequest.getTo();

        return String.format(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:flig=\"http://informatique.polytech.unice.fr/soa1/team/3/flightres/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <flig:listPossibleReservation>\n" +
                "         <itineraryInfo>\n" +
                "            <departureTime>%s</departureTime>\n" +
                "            <destinationCountry>%s</destinationCountry>\n" +
                "            <id>%s</id>\n" +
                "            <originCountry>%s</originCountry>\n" +
                "         </itineraryInfo>\n" +
                "      </flig:listPossibleReservation>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>",
                departureTime, originCountry, DEFAULT_USER, destinationCountry);
    }
}
