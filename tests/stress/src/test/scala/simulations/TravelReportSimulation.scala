package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.parsing.json.{JSON, JSONArray, JSONObject}

class TravelReportSimulation extends Simulation {

    val httpConf =
            http
                    .baseURL("http://localhost:8005/tcs-service-travel-report/")
                    .acceptHeader("application/json")
                    .header("Content-Type", "application/json")

    /**
     * A scenario to create and validate travel report
     */
    val stressCreateAndValidateTravels =
            scenario("Create and validate travel reports")
                    .repeat(100)
    {
        // We create a business model
        exec(
            http("Create a travel report")
                    .post("report/")
                    .body(
                            StringBody("""{"event": "create","businessTravelId":"robert_1992"}""")
                    )
                    .check(status.is(200))
                    .check(bodyString.saveAs("travel-submission"))
        )
        // We store the id of the travel report
        .exec{session =>
            session.set("id", JSON.parseFull(session("travel-submission").as[String]).get match {
                case m : Map[String,_] => m("id")
                case _ => ""
            })
        }
        // We validate the travel report we just created
        .exec(
            http("Validate travel reports")
                    .post("report/")
                    .body(StringBody(JSONObject(Map(
                            "event"-> "validate",
                            "id"-> "${id}"
              )).toString())
            )
            .check(status.is(200))
        )
    }

    /**
     * A scenario to retrieve all the travel reports (unapproved onces)
     */
    val stressListHotels =
            scenario("Get all travel reports")
                    .repeat(100)
    {
        // We simply list the non-approved business travels
        exec(
                http("getting all travels")
                        .post("report/")
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
