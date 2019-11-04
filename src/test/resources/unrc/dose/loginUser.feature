Feature: Account creation

Scenario: New user creates an account with credentials that are not associated with any previous user of the system.
    Given that a user wants to create an account
    And the username selected is "juan"
    And the selected password is "juanc"
    And the users’ email is "juan@gmail.com"
    And no user exists in the system with the given email is "juan@gmail.com" or username is "juan"
    When the user tries to create an account with the given credentials
    Then the system should create the account
    And inform the user that the account has been created

