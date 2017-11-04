
package fr.unice.polytech.informatique.soa;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getCarRentalListResponse complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getCarRentalListResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="car_rentals" type="{http://informatique.polytech.unice.fr/soa/}car" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCarRentalListResponse", propOrder = {
    "carRentals"
})
public class GetCarRentalListResponse {

    @XmlElement(name = "car_rentals")
    protected List<Car> carRentals;

    /**
     * Gets the value of the carRentals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carRentals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarRentals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Car }
     * 
     * 
     */
    public List<Car> getCarRentals() {
        if (carRentals == null) {
            carRentals = new ArrayList<Car>();
        }
        return this.carRentals;
    }

}
