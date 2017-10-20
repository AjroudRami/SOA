package service;


import model.Flight;
import model.FlightRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/cheapestflight")
@Consumes(MediaType.APPLICATION_JSON)
public class CheapestFlightService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Flight getCheapestFlight(FlightRequest request){

        return null;
    }
}
