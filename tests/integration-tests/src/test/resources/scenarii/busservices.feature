Feature: Bus Cheapest Services
  Background:
    Given a bus deployed on localhost:8181

  Scenario: Cheapest flight research
    Given a research for the cheapest flight booking
    And a departure airport located in Paris
    And an arrival airport located in New-York
    And a flight departure date 0710171030
    When the request is sent
    Then a flight is suggested

  Scenario: Cheapest car research
    Given a research for the cheapest car
    And a rental starting from : 2017-11-11
    And a rental ending on : 2017-11-14
    And with the car location in Cuba
    When the request is sent
    Then a car is suggested

  Scenario: Cheapest hotel research
    Given a research for the cheapest hotel
    And with the hotel location in Lyon
    And a date : 710170930
    When the request is sent
    Then an hotel is suggested