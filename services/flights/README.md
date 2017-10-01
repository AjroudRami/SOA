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
     * `sortBy`: not mandatory, a String that can be either "price" or "duration"
     * `filterBy`: not mandatory, an Array of String with one or more keywords ("direct" only for now).
 
 ### Example request:
```json
    {
      "event":"list",
      "orderBy": "price",
      "filterBy":["direct"]
    }
``` 

## Response

The response is a json object composed of a field `"flights"` which is an array of flights.

### Example Response

```json
{
    "flights": [
        {
            "numberOfFlights": 1,
            "seatClass": "cosy",
            "price": 948.2164511421322,
            "ticketNo": 2736,
            "departure": 700,
            "arrival": 1542,
            "_id": null,
            "from": "Paris",
            "to": "New-York"
        },
        {
            "numberOfFlights": 1,
            "seatClass": "cosy",
            "price": 991.886690803693,
            "ticketNo": 5552,
            "departure": 310,
            "arrival": 1349,
            "_id": null,
            "from": "Paris",
            "to": "New-York"
        },
        {
            "numberOfFlights": 1,
            "seatClass": "cosy",
            "price": 1005.2455965992281,
            "ticketNo": 3985,
            "departure": 212,
            "arrival": 1075,
            "_id": null,
            "from": "Paris",
            "to": "New-York"
        }
    ]
}
```