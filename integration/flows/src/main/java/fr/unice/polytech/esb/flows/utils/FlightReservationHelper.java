package fr.unice.polytech.esb.flows.utils;

import fr.unice.polytech.esb.flows.flights.data.FlightInformation;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class FlightReservationHelper {

    private XPath xpath = XPathFactory.newInstance().newXPath();

    public String simpleReservation(FlightInformation flight, String uuid) {

        StringBuilder builder = new StringBuilder();
        builder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soa=\"http://informatique.polytech.unice.fr/soa1/team/3/flightres/\">n");
        builder.append("    <soapenv:Header/>\n");
        builder.append("    <soapenv:Body>\n");
        builder.append("        <soa:simpleReservation>\n");
        builder.append("            <id>"+ uuid +"/id>\n");
        builder.append("            <departureTime>" + flight.getDepartureTimestamp() + "</departureTime>\n");
        builder.append("            <originCountry>" + flight.getStartingAirport() + "</originCountry>\n");
        builder.append("            <destinationCountry>" + flight.getEndingAirport() + "</destinationCountry>\n");
        builder.append("        </soa:simpleReservation>\n");
        builder.append("     </soapenv:Body>\n");
        builder.append("</soapenv:Envelope>");

        return builder.toString();
    }
}
