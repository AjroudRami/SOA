Feature: Bus Cheapest Services
  Background:
    Given a bus deployed on localhost:8181

  Scenario: Cheapest flight research
    Given a research for the cheapest flight booking
    And a departure airport located in Paris
    And an arrival airport located in New-York
    And a flight departure date 0710171030
    When the request is sent
    Then flights are suggested

  Scenario: Cheapest car research
    Given a research for the cheapest car

    When the request is sent