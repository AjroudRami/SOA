package fr.unice.polytech.informatique.soa;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.10
 * 2017-11-06T17:44:43.694+01:00
 * Generated source version: 3.1.10
 * 
 */
@WebService(targetNamespace = "http://informatique.polytech.unice.fr/soa/", name = "CarRental")
@XmlSeeAlso({ObjectFactory.class})
public interface CarRental {

    @WebMethod
    @RequestWrapper(localName = "getCarRentalList", targetNamespace = "http://informatique.polytech.unice.fr/soa/", className = "fr.unice.polytech.informatique.soa.GetCarRentalList")
    @ResponseWrapper(localName = "getCarRentalListResponse", targetNamespace = "http://informatique.polytech.unice.fr/soa/", className = "fr.unice.polytech.informatique.soa.GetCarRentalListResponse")
    @WebResult(name = "car_rentals", targetNamespace = "")
    public java.util.List<fr.unice.polytech.informatique.soa.Car> getCarRentalList(
        @WebParam(name = "place", targetNamespace = "")
        java.lang.String place,
        @WebParam(name = "duration", targetNamespace = "")
        int duration
    );
}
