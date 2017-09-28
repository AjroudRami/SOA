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