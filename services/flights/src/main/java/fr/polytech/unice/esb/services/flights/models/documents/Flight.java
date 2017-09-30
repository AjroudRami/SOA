package fr.polytech.unice.esb.services.flights.models.documents;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;
import java.util.Comparator;

public class Flight {

    public static String KEY_TICKET_NO = "ticket_no";
    public static String KEY_NUMBER_OF_FLIGHTS = "number_of_flights";
    public static String KEY_FROM = "from";
    public static String KEY_TO = "to";
    public static String KEY_DEPARTURE = "departure";
    public static String KEY_ARRIVAL = "arrival";
    public static String KEY_SEATCLASS = "seat_class";
    public static String KEY_PRICE = "price";

    private int ticketNo;
    private String from;
    private String to;
    private int departure;
    private int arrival;
    private String seatClass;
    private double price;
    private int numberOfFlights;

    @MongoObjectId
    String _id;

    public Flight(){};

    public Flight(JSONObject data) {
        this.ticketNo = data.getInt(KEY_TICKET_NO);
        this.from = data.getString(KEY_FROM);
        this.to = data.getString(KEY_TO);
        this.departure = data.getInt(KEY_DEPARTURE);
        this.arrival = data.getInt(KEY_ARRIVAL);
        this.seatClass = data.getString(KEY_SEATCLASS);
        this.price = data.getDouble(KEY_PRICE);
        this.numberOfFlights = data.getInt(KEY_NUMBER_OF_FLIGHTS);
    }

    JSONObject toJson() {
        return new JSONObject()
                .put(KEY_TICKET_NO, ticketNo)
                .put(KEY_FROM, from)
                .put(KEY_TO, to)
                .put(KEY_ARRIVAL, arrival)
                .put(KEY_DEPARTURE, departure)
                .put(KEY_SEATCLASS, seatClass)
                .put(KEY_PRICE, price);
    }

    public static Comparator<Flight> PRICE_COMPARATOR = new Comparator<Flight>() {
        @Override
        public int compare(Flight o1, Flight o2) {
            Double p1 = o1.price;
            Double p2 = o2.price;
            return p1.compareTo(p2);
        }
    };

    public static Comparator<Flight> DURATION_COMPARATOR = new Comparator<Flight>() {
        @Override
        public int compare(Flight o1, Flight o2) {
            Integer diff1 = o1.arrival - o1.departure;
            Integer diff2 = o2.arrival - o2.departure;
            return diff1.compareTo(diff2);
        }
    };

}