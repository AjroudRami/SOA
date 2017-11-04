
package fr.unice.polytech.informatique.soa;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.unice.polytech.informatique.soa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCarRentalList_QNAME = new QName("http://informatique.polytech.unice.fr/soa/", "getCarRentalList");
    private final static QName _GetCarRentalListResponse_QNAME = new QName("http://informatique.polytech.unice.fr/soa/", "getCarRentalListResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.unice.polytech.informatique.soa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCarRentalList }
     * 
     */
    public GetCarRentalList createGetCarRentalList() {
        return new GetCarRentalList();
    }

    /**
     * Create an instance of {@link GetCarRentalListResponse }
     * 
     */
    public GetCarRentalListResponse createGetCarRentalListResponse() {
        return new GetCarRentalListResponse();
    }

    /**
     * Create an instance of {@link Car }
     * 
     */
    public Car createCar() {
        return new Car();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCarRentalList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://informatique.polytech.unice.fr/soa/", name = "getCarRentalList")
    public JAXBElement<GetCarRentalList> createGetCarRentalList(GetCarRentalList value) {
        return new JAXBElement<GetCarRentalList>(_GetCarRentalList_QNAME, GetCarRentalList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCarRentalListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://informatique.polytech.unice.fr/soa/", name = "getCarRentalListResponse")
    public JAXBElement<GetCarRentalListResponse> createGetCarRentalListResponse(GetCarRentalListResponse value) {
        return new JAXBElement<GetCarRentalListResponse>(_GetCarRentalListResponse_QNAME, GetCarRentalListResponse.class, null, value);
    }

}
