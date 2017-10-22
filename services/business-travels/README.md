# Business travel service
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
pom.xml
Dockerfile
src
    main
        java
            .// Project code
        webapp
            WEB-INF
                beans.xml
                web.xml
``` 

## Building the project
This project can be built by running the following command: `mvn clean package`
The docker image can be built from the Dockerfile file.

## Interfaces
The service end point is `/tcs-service-business-travels/registry`.
This endpoint contains 4 different actions (POST).

### Submit
The `submit` action is used to create a new business travel. 
Here is an example of a request:
```json
{
	"event": "submit",
	"tickets": [
		{
			"ticketNumber": "123",
			"departureAirport": "NCE",
			"departureTimestamp": 123,
			"arrivalAirport": "AMS",
			"arrivalTimestamp": 1234
		}
	],
	"nights": [
		{
			"hotelId": "AMS-123",
			"nights": [
				{
					"departureTimestamp": 13456,
					"room": "BIG"
				}
			]
		}
	]
}
```
And its possible output:
```json
{
    "submissionId": "54ec80fe-afab-45d9-8f76-c66d46c38981"
}
```

### List
The `list` action lists the different non-approved business travels.
Here is an example of a request:
```json
{
	"event": "list"
}
```
And one of its possible outputs:
```json
{
    "travels": [
        {
            "id": "54ec80fe-afab-45d9-8f76-c66d46c38981",
            "nights": [
                {
                    "nights": [
                        {
                            "departureTimestamp": 13456,
                            "room": "BIG"
                        }
                    ],
                    "hotelId": "AMS-123"
                }
            ],
            "validated": false,
            "tickets": [
                {
                    "ticketNumber": "123",
                    "departureAirport": "NCE",
                    "arrivalAirport": "AMS",
                    "departureTimestamp": 123,
                    "arrivalTimestamp": 1234
                }
            ]
        }
    ]
}
```

### Approve
The `approve` action marks a business travel as approved/validated.
Here is an example of a request:
```json
{
	"event": "approve",
	"id": "54ec80fe-afab-45d9-8f76-c66d46c38981""
}
```
This requests returns an empty 200 OK response uppon success.

### Send
The `send` action send a given business travel to an email address (for the purpose of this demo, it only prints it on the console).
Here is an example of a request:
```json
{
	"event": "send",
	"recipient": "toto@toto",
	"content": {
		"id": "54ec80fe-afab-45d9-8f76-c66d46c3898"
	}
}
```
This requests returns an empty 200 OK response uppon success.

## Developing the project
This project is divided into 3 main packages.
The Registry class is the entry point

The actions package contains the different actions that can be executed on documents

The components package contains different components handling the main business code (e.g. listing the different business travels).

The models package contains our different business class

## Adding a new action
To add a new action, you first need to implements the DocumentAction interface.
You may want to use dependency injection in your action class. This is an example of a document action:

```java
@Any
public class SubmitAction implements DocumentAction<BusinessTravel, SubmissionResult> {

    @EJB
    private BusinessTravelComponent travels;

    /**
     * Executes a document submission
     * @param document
     * @return the result of the submission
     */
    @Override
    public SubmissionResult execute(BusinessTravel document) {
        BusinessTravel created = travels.put(document);
        return new SubmissionResult(created.getId());
    }

    @Override
    public Class<BusinessTravel> getInputType() {
        return BusinessTravel.class;
    }
}
```

You then need to define the action in the ActionComponentImpl class like so:
```java
@Singleton
public class ActionComponentImpl implements ActionComponent {

    private Map<String, Class<? extends DocumentAction>> actions;
    private ObjectMapper mapper;

    public ActionComponentImpl() {
        // ..
        actions.put("submit", SubmitAction.class);
    }
}
```
