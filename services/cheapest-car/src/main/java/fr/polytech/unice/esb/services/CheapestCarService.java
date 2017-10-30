package fr.polytech.unice.esb.services;

import com.google.gson.Gson;
import fr.polytech.unice.esb.services.model.CarRental;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/cheapestcar")
@Consumes(MediaType.APPLICATION_JSON)
public class CheapestCarService {
    public static final okhttp3.MediaType JSON
            = okhttp3.MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    private String busEndpointURL = "http://localhost:80/bus-cheapest-car";


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public CarRental getCheapestFlight(@QueryParam("place") String place,
                                       @QueryParam("startDate") int startDate,
                                       @QueryParam("endDate") int endDate){
        String jsonRequest = "{ \"place\": \"" + place + "\"," +
                "\"startDate\":" + startDate + "," +
                "\"endDate\":" + endDate + "}";
        try {
            String response = post(busEndpointURL, jsonRequest);
            Gson gson = new Gson();
            CarRental result = gson.fromJson(response, CarRental.class);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
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