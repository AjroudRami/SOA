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
- Propose alternative flights for a given destination and a given date, ordered by price and based on travellers preferences (e.g., only direct flight, given max travel time);
- Propose alternatives hotels for a given destination and a given date, ordered by prices;
- Propose alternative car rentals at a given place and for a given duration;
- Submit a business travel (a description of the different tickets and/or hotel nights to buy) to a manager, and wait for approval;
- Send a summary of an approved business travel by email.

### Personas & Story
* Jack is a software developper who oftens travel around europe to meet his clients. He always run on tight schedule. As Jack 
    * I want to receive a list of flights with given date and destination, so that I can book flights that suit my schedule
    * I want to receive a list of flights ordered by price, so that I can book flights within my budget
    * I want to have the option of filtering direct flights so that i can be at my destination quicker 
    * I want to have the option of filtering flights according to the travel time, so that I can optimize my travelling time 
* Suzy is a supervisor of a clothing company. Once a month, she needs to visit the company's fabric suplier to check on supplies. Suzy doesn't owns a car and public transportation is not available. As Suzy
    * I want to receive a list of car rental with a given place and duration, so that I can rent a car for a short duration and choose a car that is nearest to me 
* Michael is a full time researcher. In order to gather information for his research, he needs to attend forums and meet other researcher in a different country. When travel, Michael prefer to stay in cheap hotels as he spend a lot of time outside. As Michael
    * I want to receive a list of hotel with a given date and destination, so that I can book hotels that suits my schedule
    * I want to sort the list of hotel according to price, so that I can book the cheapest hotel available
* Juliette, a sales representative, often travels to sell her company's products. To get a fully subsidised trip, she needs to send an email of an approve business travel to the HR department before being able to travel. As Juliette
    * I want to submit a business travel proposal to my manager to get an approval
    * I want to email an approved business travel, so that my trip will be subsidised by the company


# Development timeline
- - - -
### Phase #1: Deploying services
* Service development
[ ] Creating a Flight Search System as a Document service;
[ ] Creating a Hotels Search System as a Resource service;
[ ] Creating a Car Rentals Search System as an RPC service;
[ ] Creating a Business Travel System as a Document service;
