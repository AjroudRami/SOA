Feature: Car Rental research

  Background:
    Given a cars service deployed on localhost:8001

  Scenario: Research without filter
    Given a research for a car rental
    When the search is sent
    Then cars are suggested

  Scenario: Research by place
    Given a research for a car rental
    And at France
    When the search is sent
    Then cars are suggested
    And the cars are located in France

  Scenario: Research by place and duration
    Given a research for a car rental
    And at Malaysia
    And  for a duration of 3 days
    When the search is sent
    Then cars are suggested
    And the cars are located in Malaysia
    And the cars suggested are exactly 3
