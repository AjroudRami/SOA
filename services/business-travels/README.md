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

## Developing the project
This project is divided into 3 main packages.
The Registry class is the entry point (`/tcs-service-business-travels/registry/`).

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
