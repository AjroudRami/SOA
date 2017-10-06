package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.parsing.json.{JSON, JSONArray, JSONObject}


class BusinessTravelSimulation extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:8000/tcs-service-business-travels/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  /**
    * A scenario to create and validate business travels
    */
  val stressCreateAndValidateTravels =
    scenario("Create and validate business travels")
      .repeat(100)
      {
        // We create a business model
        exec(
          http("Create a business travel")
            .post("registry")
            .body(
              StringBody("""{"event":"submit","tickets":[{"ticketNumber":"123","departureAirport":"NCE","departureTimestamp":123,"arrivalAirport":"AMS","arrivalTimestamp":1234}],"nights":[{"hotelId":"AMS-123","nights":[{"date":13456,"room":"BIG"}]}]}""")
            )
            .check(status.is(200))
            .check(bodyString.saveAs("travel-submission"))
        )
        // We store the id of the business travel
        .exec{session =>
          session.set("id", JSON.parseFull(session("travel-submission").as[String]).get match {
            case m : Map[String,_] => m("submissionId")
            case _ => ""
          })
        }
        // We validate the business travel we just created
        .exec(
          http("Validate business travel")
            .post("registry")
            .body(StringBody(JSONObject(Map(
                "event"-> "approve",
                "id"-> "${id}"
              )).toString())
            )
            .check(status.is(200))
        )
      }

  /**
    * A scenario to retrieve all the business travels (unapproved onces)
    */
  val stressListHotels =
    scenario("Get all travels")
        .repeat(100)
        {
          // We simply list the non-approved business travels
          exec(
            http("getting all travels")
              .post("registry")
                .body(
                  StringBody(JSONObject(Map(
                    "event" -> "list"
                  )).toString())
                )
              .check(status.is(200))
          )
        }

  setUp(stressCreateAndValidateTravels.inject(rampUsers(100) over (30 seconds)).protocols(httpConf),
        stressListHotels.inject(rampUsers(100) over (30 seconds)).protocols(httpConf))
}