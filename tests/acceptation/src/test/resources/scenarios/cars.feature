Feature: Car Rental research

  Background:
    Given a cars service deployed on localhost:8080

  Scenario: Research without filter
    Given a research for a car rental
    When the research is sent
    Then cars are suggested

  Scenario: Research by place
    Given a research for a car rental
    And located in France
    When the research is sent
    Then cars are suggested
    And the cars are located in France

