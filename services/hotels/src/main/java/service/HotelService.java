package service;

import business.Hotel;
import business.HotelRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.HotelStorage;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Antoine Aubé <aube.antoine@gmail.com>
 */
@Path("/hotels")
@Produces(MediaType.APPLICATION_JSON)
public class HotelService {

  private static String ASC = "asc";
  private static String DESC = "desc";

  @GET
  public Response getTargetedHotels(
          @QueryParam("destination") String destination,
          @QueryParam("date") String date,
          @QueryParam("price_ordering") String priceOrdering) {
    Boolean ascendingOrder;
    if (ASC.equals(priceOrdering)) {
      ascendingOrder = true;
    } else if (DESC.equals(priceOrdering)) {
      ascendingOrder = false;
    } else {
      ascendingOrder = null;
    }

    HotelRequest request = new HotelRequest(destination, null, ascendingOrder);
    List<Hotel> hotels = HotelStorage.findAllBy(request);

    JSONArray jsonHotels = new JSONArray();
    for (Hotel hotel : hotels) {
      JSONObject jsonHotel = new JSONObject();

      jsonHotel.put("name", hotel.getName());
      jsonHotel.put("city", hotel.getCity());
      jsonHotel.put("roomCost", hotel.getRoomCost());

      jsonHotels.put(jsonHotel);
    }

    return Response.ok().entity(jsonHotels.toString(2)).build();
  }
}
