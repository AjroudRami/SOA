package service;

import business.Car;
import business.CarRentalRequest;
import util.CarRentalLoader;

import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by danial
 */
@Stateless(name = "CarRentalService")
@WebService(
        portName = "CarRentalPort",
        serviceName = "CarRentalService",
        endpointInterface = "service.CarRentalService")
public class CarRentalImpl implements CarRentalService {

    @Override
    public List<Car> getCarRentalList(String place, int duration) {

        return buildResponse(new CarRentalRequest(place, duration));
    }


    private List<Car> buildResponse(CarRentalRequest carRentalRequest){

        return CarRentalLoader.findCarRentals(carRentalRequest);
    }
}
