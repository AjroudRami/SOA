Feature: Flight research
  Background:
    Given a flights service deployed on localhost:8080

  Scenario: Research without filter
    Given a research for a flight booking
    And with a departure airport located in Nice
    And with an arrival airport located in Paris
    And with a departure date 12345
    When the research is sent
    Then flights are suggested

  Scenario: Research by destination
    Given a research for a hotel booking
    And located in Liudu
    When the research is sent
    Then hotels are suggested
    And the hotels are located in Liudu

  Scenario: Research with ascending price ordering
    Given a research for a hotel booking
    And with prices ascendingly ordered
    When the research is sent
    Then hotels are suggested
    And the prices are ascendingly ordered

  Scenario: Research with descending price ordering
    Given a research for a hotel booking
    And with prices descendingly ordered
    When the research is sent
    Then hotels are suggested
    And the prices are descendingly ordered
