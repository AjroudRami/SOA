package flightservice;

import org.joda.time.DateTime;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Flight {

    public static String KEY_FLIGHTNO = "flight_no";
    public static String KEY_FROM = "from";
    public static String KEY_TO = "to";
    public static String KEY_DEPARTURE = "departure";
    public static String KEY_ARRIVAL = "arrival";

    private int flightNo;
    private String from;
    private String to;
    private DateTime departure;
    private DateTime arrival;

    @MongoObjectId
    String _id;

    public Flight(){};

    public Flight(JSONObject data) {
        this.flightNo = data.getInt(KEY_FLIGHTNO);
        this.from = data.getString(KEY_FROM);
        this.to = data.getString(KEY_TO);
        this.departure = DateTime.parse(data.getString(KEY_DEPARTURE));
        this.arrival = DateTime.parse(data.getString(KEY_ARRIVAL));
    }


    JSONObject toJson() {
        return new JSONObject()
                .put(KEY_FLIGHTNO, flightNo)
                .put(KEY_FROM, from)
                .put(KEY_TO, to)
                .put(KEY_ARRIVAL, arrival.toString())
                .put(KEY_DEPARTURE, departure.toString());
    }

}