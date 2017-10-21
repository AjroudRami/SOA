package service;


import com.google.gson.Gson;
import model.Flight;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/cheapestflight")
@Consumes(MediaType.APPLICATION_JSON)
public class CheapestFlightService {
    public static final okhttp3.MediaType JSON
            = okhttp3.MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    private String busEndpointURL = "";


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Flight getCheapestFlight(@QueryParam("departureDate") int depDate,
                                    @QueryParam("departureAirport") String depAirport,
                                    @QueryParam("arrivalAirport") String arrivalAirport){
        String jsonRequest = "";
        try {
            String response = post(busEndpointURL, jsonRequest);
            Gson gson = new Gson();
            Flight result = gson.fromJson(response, Flight.class);
            return result;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @author OKHttp tutorial
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
