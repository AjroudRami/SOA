Feature: Hotel research

  Background:
    Given a hotel service deployed on localhost:8003

  Scenario: Research without filter
    Given a research for a hotel booking
    When the research is sent
    Then hotels are suggested

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

  Scenario: Research at given date (after 14. march 1995)
    Given a research for a hotel booking
    And on the 12-02-1998
    When the research is sent
    Then hotels are suggested