package service;

import business.Car;
import business.CarRentalRequest;
import util.CarRentalLoader;

import javax.jws.WebService;
import java.util.List;

/**
 * Created by danial
 */

@WebService(
        targetNamespace = "http://informatique.polytech.unice.fr/soa/",
        portName = "ExternalCarRentalPort",
        serviceName = "ExternalCarRentalService",
        endpointInterface = "service.CarRentalService")
public class CarRentalImpl implements CarRentalService {

    /**
     * Method to retrieve a list of rental cars
     * @param place
     * @param duration
     * @return a list of car
     */
    @Override
    public List<Car> getCarRentalList(String place, int duration) {
        if (place == null){ place = ""; }
        return buildResponse(new CarRentalRequest(place, duration));
    }

    private List<Car> buildResponse(CarRentalRequest carRentalRequest){
        return CarRentalLoader.findCarRentals(carRentalRequest);
    }
}
