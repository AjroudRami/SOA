package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.language.postfixOps



class FlightsSimulation extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:8002/tcs-service-flights/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("Get flights from Paris to New-York")
      .repeat(100)
      {
        exec(
          http("getting flights from Paris to New-York on the 0710171030")
            .post("flights")
            .body(
              StringBody("""{"event":"list", "destination":"Paris", "departure":"New-York", "departureTimeStamp":0710171030}""")
            )
            .check(status.is(200))
        )
      }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}