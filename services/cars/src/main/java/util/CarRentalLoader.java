package util;

import business.Car;
import business.CarRentalRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by danial
 */
public class CarRentalLoader {
    /**
     * Temporary variable
     */
    private static List<Car> cars = new ArrayList<>();

    /**
     * temporary method to fill the car list
     * @param car
     */
    private static void build(Car car){
        cars.add(car);
    }

    /**
     * temporary implemented to retrieve data from csv file
     */
    static {
        ClassLoader classLoader = CarRentalLoader.class.getClassLoader();
        File carData = new File(classLoader.getResource("car-rental-data.csv").getFile());


        try {
            CSVParser parser = CSVParser.parse(carData, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());

            parser.forEach(item->{
                Car car = new Car(
                        item.get("Brand"),
                        item.get("Model"),
                        item.get("Place"),
                        Integer.parseInt((item.get("Price"))),
                        Integer.parseInt((item.get("Availability")))
                );

                build(car);
            });

            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
      Data Loader methods
     */

    private static List<Car> filterByPlace(List<Car> carList, CarRentalRequest carRentalRequest){
        if (carRentalRequest.getPlace().map(String::isEmpty).orElse(true)){
            return carList;
        }

        List<Car> filteredCars = carList.stream()
                .filter(car -> {
                    return carRentalRequest.getPlace().map(place -> place.equals(car.getPlace())).orElse(true);
                }).collect(Collectors.toList());

        return filteredCars;
    }

    private static List<Car> filterByDuration(List<Car> carList, CarRentalRequest carRentalRequest) {
        if (carRentalRequest.getDuration().map(duration -> duration == 0).orElse(true)){
            return carList;
        }

        List<Car> filteredCars = new ArrayList<>();

        for (Car car: carList) {
            if(carRentalRequest.getDuration().orElse(0) < car.isAvailability()) filteredCars.add(car);
        }

        return filteredCars;
    }

    /**
     * Method to retrieve all car rental that suits the given argument
     * @param carRentalRequest
     * @return a list of car object
     */
    public static List<Car> findCarRentals(CarRentalRequest carRentalRequest){

        return filterByDuration(filterByPlace(cars,carRentalRequest),carRentalRequest);
    }
}
