Feature: Flight research
  Background:
    Given a flights service deployed on localhost:8080

  Scenario: Simple research
    Given a research for a flight booking
    And a departure airport located in Nice
    And an arrival airport located in Paris
    And a departure date 12345
    When the research is sent
    Then flights are suggested

  Scenario: Research ordered by prices
    Given a research for a flight booking
    And a departure airport located in Nice
    And an arrival airport located in Paris
    And a departure date 12345
    And ordering by price
    When the research is sent
    Then hotels are suggested
    And the flights are order by price

  Scenario: Research ordered by duration
    Given a research for a flight booking
    And a departure airport located in Nice
    And an arrival airport located in Paris
    And a departure date 12345
    And ordering by duration
    When the research is sent
    Then hotels are suggested
    And the flights are order by duration

  Scenario: Research filtered by direct flights only
    Given a research for a flight booking
    And a departure airport located in Nice
    And an arrival airport located in Paris
    And a departure date 12345
    And a simple filter direct
    When the research is sent
    Then hotels are suggested
    And the flights are filtered : direct

  Scenario: Research filtered by max duration
    Given a research for a flight booking
    And a departure airport located in Nice
    And an arrival airport located in Paris
    And a departure date 12345
    And a filter max_duration with value 1000
    When the research is sent
    Then hotels are suggested
    And the flights are filtered : max_duration 1000
