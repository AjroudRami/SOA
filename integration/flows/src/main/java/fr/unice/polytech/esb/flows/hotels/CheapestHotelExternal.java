package fr.unice.polytech.esb.flows.hotels;

import fr.unice.polytech.esb.flows.hotels.data.HotelInformation;
import fr.unice.polytech.esb.flows.hotels.data.HotelRequest;
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
import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.EXTERNAL_HOTELS_ENDPOINT;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_EXTERNAL_HOTELS_SERVICE;

public class CheapestHotelExternal extends RouteBuilder {

    private static final int DEFAULT_DURATION = 1;
    private static final boolean DEFAULT_ORDER = true;

    @Override
    public void configure() throws Exception {
        from(SEARCH_IN_EXTERNAL_HOTELS_SERVICE)
                // Route description.
                .routeId("call-external-hotel-reservation-service")
                .routeDescription("Call the external hotel reservation service")

                // Log the current action.
                .log("Make a research in the EXTERNAL hotels service.")

                // Prepare the POST request to a RPC service.
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/soap+xml"))
                .setHeader("Accept", constant("application/json"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeRequestBody(exchange.getIn().getBody(HotelRequest.class))))

                // Send the request to the external service.
                .inOut(EXTERNAL_HOTELS_ENDPOINT)

                // Parse the SOAP response into a list of hotels and
                // put it as the body.
                .process(translateFromResponse);
    }

    private static Processor translateFromResponse = exchange -> {
        List<HotelInformation> hotelsInformation = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new InputSource(new StringReader(exchange.getIn().getBody(String.class))));

        NodeList xmlHotels = document.getElementsByTagName("recherche_hotel");

        for (int i = 0; i < xmlHotels.getLength(); i++) {
            // TODO: Make this step better (other way to parse XML?).
            hotelsInformation.add(fromNode(xmlHotels.item(i)));
        }

        exchange.getIn().setBody(hotelsInformation);
    };

    private static HotelInformation fromNode(Node xmlFlight) throws ParseException {
        NodeList xmlFlightNodes = xmlFlight.getChildNodes();

        String place = null;
        String name = null;
        float price = -1;

        for (int j = 0; j < xmlFlightNodes.getLength(); j++) {
            Node xmlNode = xmlFlightNodes.item(j);

            switch (xmlNode.getNodeName()) {
                case "lieu":
                    place = xmlNode.getNodeValue();
                    break;
                case "nom":
                    name = xmlNode.getNodeValue();
                    break;
                case "prix":
                    price = Float.parseFloat(xmlNode.getNodeValue());
                    break;
            }
        }

        return new HotelInformation(place, name, price);
    }

    private static String makeRequestBody(HotelRequest hotelRequest) {
        // Translate the timestamp into a date in format "YYYY-MM-DD".
        String destination = hotelRequest.getDestination();

        return String.format(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:flig=\"http://informatique.polytech.unice.fr/soa1/cookbook/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <flig:recherche>\n" +
                        "         <lieu>%s</lieu>\n" +
                        "         <dure>%d</dure>\n" +
                        "         <sortedAsc>%s</sortedAsc>\n" +
                        "      </flig:recherche>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>",
                destination, DEFAULT_DURATION, DEFAULT_ORDER);
    }
}
