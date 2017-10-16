package fr.unice.polytech.esb.flows.utils;

import fr.unice.polytech.esb.flows.data.Flight;
import fr.unice.polytech.esb.flows.data.TaxInfo;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.text.SimpleDateFormat;

public class FlightsCallerHelper {

    private XPath xpath = XPathFactory.newInstance().newXPath();

    public String simpleReservation(Flight flight) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder builder = new StringBuilder();
        builder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soa=\"http://informatique.polytech.unice.fr/soa1/team/3/flightres/\">n");
        builder.append("<soapenv:Header/>\n");
        builder.append("<soapenv:Body>\n");
        builder.append("<soa:simpleReservation>\n");
        builder.append("<id>palatine-234</id>\n");
        builder.append("<departureTime>" + formatter.format(flight.getDeparture()) + "</departureTime>\n");
        builder.append("<originCountry>" + flight.getFrom() + "</originCountry>\n");
        builder.append("<destinationCountry>" + flight.getTo() + "</destinationCountry>\n");
        builder.append("</soa:simpleReservation>\n");
        builder.append("</soapenv:Body>\n");
        builder.append("</soapenv:Envelope>");

        return builder.toString();
    }
}
