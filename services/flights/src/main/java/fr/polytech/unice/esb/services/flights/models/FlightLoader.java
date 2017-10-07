package fr.polytech.unice.esb.services.flights.models;

import fr.polytech.unice.esb.services.flights.models.documents.Flight;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FlightLoader {

    /**
     * Temporary variable
     */
    private static List<Flight> flights = new ArrayList<>();

    /**
     * temporary method to fill the Flight list
     * @param Flight
     */
    private static void build(Flight Flight){
        flights.add(Flight);
    }

    /**
     * temporary implemented to retrieve data from csv file
     */
    static {
        ClassLoader classLoader = FlightLoader.class.getClassLoader();
        File FlightData = new File(classLoader.getResource("flight-data.csv").getFile());

        try {
            CSVParser parser = CSVParser.parse(FlightData, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader());

            parser.forEach(item -> {
                Flight Flight = new Flight(
                        Integer.parseInt(item.get(0)),
                        item.get(2),
                        item.get(3),
                        Integer.parseInt(item.get(4)),
                        Integer.parseInt(item.get(5)),
                        item.get(6),
                        Double.parseDouble(item.get(7)),
                        Integer.parseInt(item.get(1))
                );

                build(Flight);
            });

            parser.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Flight> getFlights(){
        return flights;
    }
}
