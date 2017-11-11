Feature: Travel Report

  Background:
    Given a travel report service deployed on localhost:8005

  Scenario: Register a travel report and add expenses
    Given a new travel report for john_doe12
    When the travel report is sent
    Then an empty travel report is created
    When the travel reports are listed
    Then the list contains the last travel report
    When the employee add an expense of 130 on 2017-11-11 for 2 nights at Negresco
    Then the travel report contains the correct total amount
    When the employee add an expense of 270 on 2017-11-13 for flight back to nice
    Then the travel report contains the correct total amount

  Scenario: Register a travel report, add expenses and end the travel
    Given a new travel report for john_doe12
    When the travel report is sent
    Then an empty travel report is created
    When the travel reports are listed
    Then the list contains the last travel report
    When the employee add an expense of 130 on 2017-11-11 for 2 nights at Negresco
    Then the travel report contains the correct total amount
    When the employee ends the travel report
    Then the status should change to FINISH

