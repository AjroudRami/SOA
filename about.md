# Polytech SOA
- - - -
Authors : 
  - AJROUD Rami
  - AUBE Antoine
  - BIN AHMAD Danial
  - JUNGBLUTH Günther

# Case Study: The Virtual Travel Agency
- - - -
We consider here an ecosystem of services dedicated to support an employee when travelling for business purpose. The services must support the following features:
- Propose alternative flights for a given destination and a given departureTimestamp, ordered by price and based on travellers preferences (e.g., only direct flight, given max travel time);
- Propose alternatives hotels for a given destination and a given departureTimestamp, ordered by prices;
- Propose alternative car rentals at a given place and for a given duration;
- Submit a business travel (a description of the different tickets and/or hotel nights to buy) to a manager, and wait for approval;
- Send a summary of an approved business travel by email.

### Personas & Story
* Jack is a software developer who often travel around europe to meet his clients. He always run on tight schedule. As Jack 
    * I want to receive a list of flights with given departure departureTimestamp, a destination and an arrival airport so that I can book flights that suit my schedule
    * I want to receive a list of flights ordered by price, so that I can book flights within my budget
    * I want to have the option of filtering direct flights only so that I don't spend time and effort in multiple plane changes
    * I want to have the option of filtering flights according to the travel time, so that I can optimize my travelling time 
    * I want to receive a list of flights ordered by duration, so that I can book flights within my time window
* Suzy is a supervisor of a clothing company. Once a month, she needs to visit the company's fabric supplier to check on supplies. Suzy doesn't owns a car and public transportation is not available. As Suzy
    * I want to receive a list of car rentals with a given place and duration, so that I can rent a car for a short duration and choose a car that is nearest to me 
* Michael is a full time researcher. In order to gather information for his research, he needs to attend forums and meet other researcher in a different country. When travel, Michael prefer to stay in cheap hotels as he spend a lot of time outside. As Michael
    * I want to receive a list of hotels with a given departureTimestamp and destination, so that I can choose a room that suits my schedule
    * I want to sort the list of hotels according to price, so that I can book the cheapest hotel available
* Juliette, a sales representative, often travels to sell her company's products. To get a fully subsidised trip, she needs to send an email of an approve business travel to the HR department before being able to travel. As Juliette
    * I want to submit a business travel proposal to my manager to get an approval
    * I want to email an approved business travel, so that my trip will be subsidised by the company


# Development timeline
- - - -
### Phase #1: Deploying services
* Service development
  - Creating a Flight Search System as a Document service;
  - Creating a Hotels Search System as a Resource service;
  - Creating a Car Rentals Search System as an RPC service;
  - Creating a Business Travel System as a Document service;
* Service testing
    - Acceptance test using scenario
    - Stress testing
* Service deployment
    - Deploy service using containers
    - Making a single system from the composed containers
 
### Phase #2: Integration services
* Integrate the different services (cars, flights, hotels) from different team to retrieve the cheapest fare.
* Implement the Business Travel data flow
* Implement the Travel Report data flow (including the refund process)
  
# Service description
- - - - 
* [Flight service](https://github.com/scipio3000/polytech-soa/tree/master/services/flights)
* [Hotel service](https://github.com/scipio3000/polytech-soa/tree/master/services/hotels)
* [Car rental service](https://github.com/scipio3000/polytech-soa/tree/master/services/cars)
* [Business travel service](https://github.com/scipio3000/polytech-soa/tree/master/services/business-travels)
