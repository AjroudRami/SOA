package service;

import business.Car;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by danial
 */
@WebService(name = "CarRental")
public interface CarRentalService {

    @WebMethod(operationName = "GetCarRentals")
    @WebResult(name = "car_rentals")
    List<Car> getCarRentalList(
            @WebParam(name = "place") String place,
            @WebParam(name = "duration") int duration);

}