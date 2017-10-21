# Travel Report service
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
The service end point is `/tcs-service-travel-report/report/`.
This endpoint currently contains 3 different actions (POST).

### Create
The `create` action is used to create a new travel report. 
Here is an example of a request:
```json
{
	"event": "create",
	"start":"2017-10-20",
	"finish":"2017-10-30" // optional
}
```
And its possible output:
```json
{
    "id": "46136d28-c079-4c92-a335-1a199eb10fe6",
    "start": 1514246400000,
    "status": "INPROGRESS",
    "finish": null,
    "expenses": null,
    "totalAmount": 0,
    "explaination": null
}
```

### List
The `list` action lists the different in-progress travel reports.
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
            "id": "bddadae4-4a9a-4dbe-8a22-3c222503304e",
            "start": 1513900800000,
            "status": "INPROGRESS",
            "finish": null,
            "expenses": null,
            "totalAmount": 0,
            "explaination": null
        },
        {
            "id": "500d094c-0917-4e27-a79c-5d371e8b59b3",
            "start": 1514332800000,
            "status": "INPROGRESS",
            "finish": null,
            "expenses": null,
            "totalAmount": 0,
            "explaination": null
        }
    ]
}
```

### Validate
The `validate` action marks a travel report as accepted.
Here is an example of a request:
```json
{
	"event": "validate",
	"id": "54ec80fe-afab-45d9-8f76-c66d46c38981""
}
```
And its possible outputs:
```json
{
    "id": "dcbf4280-9d2d-4535-bc3a-95e6054eabb3",
    "start": 1513987200000,
    "status": "ACCEPTED",
    "finish": null,
    "expenses": null,
    "totalAmount": 0,
    "explaination": null
}
```
This requests returns an empty 200 OK response upon success.
