Feature: Cheapest flight research
  Background:
    Given a cheapest flight service deployed on localhost:8005

  Scenario: Simple research
    Given a research for a flight booking
    And a departure airport located in New-York
    And an arrival airport located in Paris
    And a departure date 0710171030
    When the flight research is sent
    Then flights are suggested