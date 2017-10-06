Feature: Business Travel

  Background:
    Given a business travel service deployed on localhost:8000

  Scenario: Register a business travel and validate it
    Given a new business travel
    And a flight #123 from NCE to AMS on 12/01/2017-12:23 arriving at 12/01/2017-14:30
    And a hotel #23 with the given nights
      | room BIG on 12/01/2017 |
      | room BIG on 13/01/2017 |
    When the business travel is sent
    Then an id is received
    When the business travels are listed
    Then the list contains the last business travel
    When the business travel is validated
    And the business travels are listed
    Then the list not contain the last business travel

  Scenario: Register a business travel and validate it
    Given a new business travel
    And a flight #123 from KUL to AMS on 10/01/2017-10:00 arriving at 11/01/2017-23:30
    And a hotel #23 with the given nights
      | room BIG on 12/01/2017 |
      | room BIG on 13/01/2017 |
      | room SMALL on 13/01/2017 |
    And a hotel #24 with the given nights
      | room SMALL on 13/01/2017 |
    When the business travel is sent
    Then an id is received
    When the business travel is sent to toto@yolo.fr
    Then the service should return properly

  Scenario: Send an invalid business travel should not work
    When the business travel #12 is sent to toto@yolo.fr
    Then the service should not return properly