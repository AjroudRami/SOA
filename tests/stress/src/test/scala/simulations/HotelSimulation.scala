package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps


class HotelSimulation extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:8003/tcs-hotel-service/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("Get all hotels")
        .repeat(100)
        {
          exec(
            http("getting all hotels")
              .get("hotels")
              .check(status.is(200))
          )
        }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}