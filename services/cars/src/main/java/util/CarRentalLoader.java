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
//        File carData = new File("/Volumes/Macintosh HD/Users/danialaswad/Documents/Polytech/Final year/SOA/Lab#1/polytech-soa/services/cars/src/main/resources/car-rental-data1.csv");


        try {
            CSVParser parser = CSVParser.parse(carData, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());

            parser.forEach(item->{
                Car car = new Car(
                        item.get("Brand"),
                        item.get("Model"),
                        item.get("City"),
                        Integer.parseInt((item.get("Price")))
                );

                build(car);
            });

            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Car> findCarRentals(CarRentalRequest carRentalRequest){
        List<Car> filteredCars = cars.stream()
                .filter(car -> {
                    return carRentalRequest.getPlace().map(place -> place.equals(car.getPlace())).orElse(true);
                }).collect(Collectors.toList());


        return filteredCars;
    }
}
