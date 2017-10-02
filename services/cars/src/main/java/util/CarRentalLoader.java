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
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by danial
 */
public class CarRentalLoader {


    private static final Random RANDOM = new Random();

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
            if(RANDOM.nextBoolean()) filteredCars.add(car);
        }

        return filteredCars;
    }

    public static List<Car> findCarRentals(CarRentalRequest carRentalRequest){

        return filterByDuration(filterByPlace(cars,carRentalRequest),carRentalRequest);
    }
}
