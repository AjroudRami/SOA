Feature: Flight research
  Background:
    Given a flights service deployed on localhost:8002

  Scenario: Simple research
    Given a research for a flight booking
    And a departure airport located in New-York
    And an arrival airport located in Paris
    And a departure date 0710171030
    When the flight research is sent
    Then flights are suggested

  Scenario: Research ordered by prices
    Given a research for a flight booking
    And a departure airport located in New-York
    And an arrival airport located in Paris
    And a departure date 0710171030
    And ordering by price
    When the flight research is sent
    Then flights are suggested
    And the flights are ordered by price

  Scenario: Research ordered by duration
    Given a research for a flight booking
    And a departure airport located in New-York
    And an arrival airport located in Paris
    And a departure date 0710171030
    And ordering by duration
    When the flight research is sent
    Then flights are suggested
    And the flights are ordered by duration

  Scenario: Research filtered by direct flights only
    Given a research for a flight booking
    And a departure airport located in New-York
    And an arrival airport located in Paris
    And a departure date 0710171030
    And a simple filter direct
    When the flight research is sent
    Then flights are suggested
    And the flights are filtered : direct no

  Scenario: Research filtered by max duration
    Given a research for a flight booking
    And a departure airport located in New-York
    And an arrival airport located in Paris
    And a departure date 0710171030
    And a filter max_duration with value 1000
    When the flight research is sent
    Then flights are suggested
    And the flights are filtered : max_duration 1000
