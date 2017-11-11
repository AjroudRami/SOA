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

    When the request is sent
    Then a car is suggested



  Scenario: Cheapest hotel research
    Given a research for the cheapest hotel

    When the request is sent
    Then an hotel is suggested