# Car Rental Service
Authors: 
* Rami Ajroud [(email)](rami.ajroud@etu.unice.fr)
* Antoine Aubé [(email)](antoine.aube@etu.unice.fr)
* Danial Aswad Bin Ahmad Fazlan [(email)](danial-aswad.bin-ahmad-fazlan@etu.unice.fr)
* Günther Jungbluth [(email)](gunther.jungbluth@etu.unice.fr)

## Dependencies
This project requires :
- maven
- java 8
- docker

## Project structure
This service was made with Maven in Java. It follows the given file structure:
```
README.md
pom.xml
Dockerfile
src
    main
        java
            # Service code
        webapp
            WEB-INF
                web.xml
``` 

## Building the project
This project can be built by running the following command: `mvn clean package`
The docker image can be built from the Dockerfile file.

## Developing the service

### Declaring the interface

The service declares 1 operation in the [CarRentalService](https://github.com/scipio3000/polytech-soa/blob/develop/services/cars/src/main/java/service/CarRentalService.java) interface, which is associated to a data loader method.

```java
@WebService(name = "CarRental",
        targetNamespace = "http://informatique.polytech.unice.fr/soa1/cookbook/")
public interface CarRentalService {

    @WebResult(name = "car_rentals")
    List<Car> getCarRentalList(
            @WebParam(name = "place") String place,
            @WebParam(name = "duration") int duration);

}
```
The associated request and response classes are available in the [business](https://github.com/scipio3000/polytech-soa/tree/develop/services/cars/src/main/java/business) package.

### Implementing the interface

The interface is implemented in the [CarRentalImpl](https://github.com/scipio3000/polytech-soa/blob/develop/services/cars/src/main/java/service/CarRentalImpl.java) class.

## Starting the service

* Compiling: mvn clean package will create the file target/tcs-cars-service.war
* Running: mvn tomee:run will deploy the created war inside a TomEE+ server, available on localhost:8080
* The WSDL interface is available at http://localhost:8080/tcs-cars-service/ExternalCarRentalService?wsdl

### Request example

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:soa="http://informatique.polytech.unice.fr/soa/">
   <soapenv:Header/>
   <soapenv:Body>
      <soa:getCarRentalList>
         <place>France</place> <-- Place of rental -->
         <duration>2</duration> <-- Duration of rental in days -->
      </soa:getCarRentalList>
   </soapenv:Body>
</soapenv:Envelope>
```

### Response example

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:getCarRentalListResponse xmlns:ns2="http://informatique.polytech.unice.fr/soa/">
         <car_rentals>
            <availability>2</availability>
            <brand>MINI</brand>
            <model>Cooper</model>
            <place>France</place>
            <rentPricePerDay>34</rentPricePerDay>
         </car_rentals>
         <car_rentals>
            <availability>10</availability>
            <brand>Lincoln</brand>
            <model>Zephyr</model>
            <place>France</place>
            <rentPricePerDay>254</rentPricePerDay>
         </car_rentals>
      </ns2:getCarRentalListResponse>
    </soap:Body>
 </soap:Envelope>
```