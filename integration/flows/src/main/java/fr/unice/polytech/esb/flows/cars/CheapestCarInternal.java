package fr.unice.polytech.esb.flows.cars;

import fr.unice.polytech.esb.flows.cars.data.CarInformation;
import fr.unice.polytech.esb.flows.cars.data.CarRequest;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static fr.unice.polytech.esb.flows.utils.Endpoints.EXTERNAL_CARS_ENDPOINT;
import static fr.unice.polytech.esb.flows.utils.Endpoints.SEARCH_IN_INTERNAL_CARS_SERVICE;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class CheapestCarInternal extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(SEARCH_IN_INTERNAL_CARS_SERVICE)
                // Route description.
                .routeId("call-internal-car-reservation-service")
                .routeDescription("Call the internal car reservation service")

                // Log the current action.
                .log("Make a research in the INTERNAL car service.")

                // Prepare the POST request to a RPC service.
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader("Content-Type", constant("application/soap+xml"))
                .process(exchange -> exchange.getIn()
                        .setBody(makeRequestBody(exchange.getIn().getBody(CarRequest.class))))

                // Send the request to the external service.
                .inOut(EXTERNAL_CARS_ENDPOINT)

                // Parse the SOAP response into a list of flights and
                // put it as the body.
                .process(translateFromResponse);
    }

    private static Processor translateFromResponse = exchange -> {
        List<CarInformation> carsInformation = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new InputSource(new StringReader(exchange.getIn().getBody(String.class))));

        NodeList xmlCars = document.getElementsByTagName("ns2:getCarRentalListResponse");
        if (xmlCars != null) {
            for (int i = 0; i < xmlCars.getLength(); i++) {
                // TODO: Make this step better (other way to parse XML?).
                carsInformation.add(fromNode(xmlCars.item(i)));
            }
        }

        exchange.getIn().setBody(carsInformation);
    };

    private static CarInformation fromNode(Node xmlCar) throws ParseException {
        NodeList xmlCarNode = xmlCar.getChildNodes();

        String brand = null;
        String model = null;
        String place = null;
        Float price = null;

        for (int j = 0; j < xmlCarNode.getLength(); j++) {
            Node xmlNode = xmlCarNode.item(j);

            switch (xmlNode.getNodeName()) {
                case "brand":
                    brand = xmlNode.getNodeValue();
                    break;
                case "price":
                    price = Float.parseFloat(xmlNode.getNodeValue());
                    break;
                case "model":
                    model = xmlNode.getNodeValue();
                    break;
                case "place":
                    place = xmlNode.getNodeValue();
                    break;
            }
        }

        assert price != null;

        return new CarInformation(brand, model, place, price);
    }

    private static String makeRequestBody(CarRequest carRequest) {
        return String.format(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soa=\"http://informatique.polytech.unice.fr/soa/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <soa:getCarRentalList>\n" +
                        "         <place>%s</place>\n" +
                        "         <duration>%d</duration>\n" +
                        "      </soa:getCarRentalList>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>",
                carRequest.getPlace(),
                ChronoUnit.DAYS.between(carRequest.getFrom().toInstant(), carRequest.getTo().toInstant()));
    }
}
