package computerdatabase



import java.util.UUID

import io.gatling.http.Predef._
import io.gatling.core.Predef._

import scala.language.postfixOps
import scala.concurrent.duration._

class CarRentalSimulation extends Simulation {

  val httpConf = http
      .baseURL("http://localhost:8001/tcs-cars-service")
      .acceptHeader("application/xml")
      .header("Content-Type", "application/xml")

  val stressSample =
    scenario("Retrieve all car rental")
      .repeat(100)
      {
        exec(session =>
          session.set("ssn", UUID.randomUUID().toString)
        )
          .exec(
            http("getting all car rental")
              .post("/ExternalCarRentalService")
              .body(StringBody(session => retrieveAllCar(session)))
              .check(status.is(200))
          )
          .exec(
            http("getting car rental in France")
              .post("/ExternalCarRentalService")
              .body(StringBody(session => retrieveCarAtAGivenPlace(session)))
              .check(status.is(200))
          )
          .exec(
            http("getting car rental in France for a duration of 5 days")
              .post("/ExternalCarRentalService")
              .body(StringBody(session => retrieveCarAtAGivenPlaceForAGivenDuration(session)))
              .check(status.is(200))
          )
      }

  def retrieveAllCar(session: Session): String = {
    val ssn = session("ssn").as[String]
    raw"""<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soa="http://informatique.polytech.unice.fr/soa/"><soapenv:Header/><soapenv:Body><soa:getCarRentalList><place></place><duration>0</duration></soa:getCarRentalList></soapenv:Body></soapenv:Envelope>""""
  }

  def retrieveCarAtAGivenPlace(session: Session): String = {
    val ssn = session("ssn").as[String]
    raw"""<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soa="http://informatique.polytech.unice.fr/soa/"><soapenv:Header/><soapenv:Body><soa:getCarRentalList><place>France</place><duration>0</duration></soa:getCarRentalList></soapenv:Body></soapenv:Envelope>""""
  }

  def retrieveCarAtAGivenPlaceForAGivenDuration(session: Session): String = {
    val ssn = session("ssn").as[String]
    raw"""<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soa="http://informatique.polytech.unice.fr/soa/"><soapenv:Header/><soapenv:Body><soa:getCarRentalList><place>France</place><duration>5</duration></soa:getCarRentalList></soapenv:Body></soapenv:Envelope>""""
  }

  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}