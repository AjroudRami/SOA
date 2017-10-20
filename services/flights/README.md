# Flight Service
---
Authors:
 - AJROUD Rami
 - JUNGBLUTH Gunther
 
# Service structure

This service is implemented using J2EE. It is structured using Maven.
The descriptor is the pom.xml file.
The file system hierarchy is the following.  
  
  ```
  rami@Icy:~/Polytech/SOA/polytech-soa/services/flights$ tree
  .
  ├── flights.iml
  ├── pom.xml
  ├── README.md
  └── src
      └── main
          ├── java
          │   ├── flightservice
          │   │   ├── ## Deprecated package
          │   └── fr
          │       └── polytech
          │           └── unice
          │               └── esb
          │                   └── services
          │                       └── flights
          │                           ├── actions
          │                           │   ## Action available for this service
          │                           ├── components
          │                           │   ## Components are here
          │                           ├── models
          │                           │   ├── documents
          │                           │   │   ## Object the service returns
          │                           │   └── errors
          │                           │       ## Request errors
          │                           └── Registry.java ## Entry Point
          ├── resources
          └── webapp
```
# Developing the service

The service is implemented following a factory pattern. Each action has its own class.
The service identifies the action to perform and load the corresponding object to handle it.
This design pattern is used and comes from an other service (Business) made by Gunther Junbluth.
The entry point is the `Registry.class`.

```java
@Path("/flights")
@Produces(MediaType.APPLICATION_JSON)
public class Registry {

    @EJB
    private ResponseComponent responses;

    @EJB
    private ActionComponent actions;

    /**
     * We execute different actions according to the document event
     * @param input the document
     * @return The result of the action execution. Returns a 404 if the action is not found
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(Map<String, Object> input) {
        String event = (String) input.getOrDefault("event","");
        Optional<DocumentAction<?, ?>> action = actions.get(event);

        if (action.isPresent()) {
            try {
                return responses.reponse(Response.Status.OK, actions.execute(action.get(), input));
            } catch (Exception e) {
                return responses.reponse(Response.Status.INTERNAL_SERVER_ERROR, new InternalServerError(e.getMessage()));
            }
        } else {
            return responses.reponse(Response.Status.NOT_FOUND, new ActionNotFound(event));
        }
    }
}
```
# API Documentation

The service url is: tcs-service-flights/flights/

## Request
 All request must be send  using the POST method.
 The server accept `JSON` only. The request must contains an `"event"` field, available actions are:
### Events
 
 * `list` : list all the flights
   * parameters:
     * `departure`: mandatory, a String. The departure airport
     * `destination`: mandatory, a String. The destination airport.
     * `departureTimeStamp`: mandatory, time when the user wants to go represented by an integer.
     * `orderBy`: not mandatory, a String that can be either "price" or "duration"
     * `filterBy`: not mandatory, an Array of Filter objects.
        `Filter`:
        * Structure: A filter is composed by:
            * `name` : a String representing the filter name
            * `args` : an array of String representing the argument of the filter
        * Available filters are:
            * `price` : args is not mandatory
            * `max_duration` : args is an array containing one String value representing an integer. This is integer is the max duration.
      
### Example requests:
```json
 {
   "event":"list",
   "destination":"Paris",
   "departure":"New-York",
   "departureTimeStamp":710171030
 }
``` 

```json
{
  "event":"list",
  "destination":"Paris",
  "departure":"New-York",
  "departureTimeStamp":710171030,
  
  "filterBy":[
               {
                "name":"max_duration", 
                "args":["1000"]
                }
             ]
}
```

## Response

The response is a json object composed of a field `"flights"` which is an array of flights.

### Example Response (the second example request's response)

```json
{
    "flights": [
        {
            "departure": 710171030,
            "numberOfFlights": 1,
            "ticketNo": 66,
            "arrival": 710171730,
            "seatClass": "Green",
            "price": 1188.39,
            "_id": null,
            "from": "Paris",
            "to": "New-York",
            "duration": 700
        },
        {
            "departure": 710171130,
            "numberOfFlights": 1,
            "ticketNo": 49,
            "arrival": 710171630,
            "seatClass": "Aquamarine",
            "price": 1662,
            "_id": null,
            "from": "Paris",
            "to": "New-York",
            "duration": 500
        },
        {
            "departure": 710170930,
            "numberOfFlights": 1,
            "ticketNo": 91,
            "arrival": 710171530,
            "seatClass": "Orange",
            "price": 1449.74,
            "_id": null,
            "from": "Paris",
            "to": "New-York",
            "duration": 600
        },
        {
            "departure": 710171530,
            "numberOfFlights": 1,
            "ticketNo": 20,
            "arrival": 710172030,
            "seatClass": "Maroon",
            "price": 537.02,
            "_id": null,
            "from": "Paris",
            "to": "New-York",
            "duration": 500
        },
        {
            "departure": 710170930,
            "numberOfFlights": 1,
            "ticketNo": 81,
            "arrival": 710171530,
            "seatClass": "Purple",
            "price": 1554.62,
            "_id": null,
            "from": "Paris",
            "to": "New-York",
            "duration": 600
        },
        {
            "departure": 710171730,
            "numberOfFlights": 1,
            "ticketNo": 76,
            "arrival": 710172230,
            "seatClass": "Orange",
            "price": 1326.6,
            "_id": null,
            "from": "Paris",
            "to": "New-York",
            "duration": 500
        }
    ]
}
```