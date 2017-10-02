package scenarios;

import cucumber.api.java.en.*;
import fr.unice.polytech.informatique.soa.*;

import javax.xml.ws.BindingProvider;
import java.net.URL;
import java.util.List;


import static org.junit.Assert.*;

public class CarStepDefinition {


    private String host = "localhost";
    private int port = 8080;

    private String place;

    private List<Car> response;


    @Given("^a cars service deployed on (.*):(\\d+)$")
    public void setupAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Given("^a research for a car rental")
    public void rentCar() {
        place = "";
    }

    @Given("^at (.*)$")
    public void specifyRentalplace(String destination) {
        this.place = destination;
    }


    @When("^the search is sent$")
    public void call_service() {
        CarRental carRental = getWS();
        response = carRental.getCarRentalList(this.place, 0);
    }

    @Then("^cars are suggested$")
    public void carsAreSuggested() {
        assertNotNull(response);
    }


    @Then("^the cars are located in (.*)$")
    public void carsAreCorrectlyLocated(String destination) {
        for (Car car: response) {
            assertEquals(car.getPlace(), destination);
        }
    }


    private CarRental getWS() {
        URL wsdl = CarStepDefinition.class.getResource("ExternalCarRentalService.wsdl");
        ExternalCarRentalService factory = new ExternalCarRentalService(wsdl);
        CarRental ws = factory.getExternalCarRentalPort();
        String address = "http://"+this.host+":"+this.port+"/tcs-cars-service/ExternalCarRentalService";
        ((BindingProvider) ws).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        return ws;
    }

}
