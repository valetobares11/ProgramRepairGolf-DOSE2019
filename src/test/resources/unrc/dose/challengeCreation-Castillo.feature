Feature: The application responds appropriately to all creation of challenges.

Scenario: A content creator user creates a new valid compilation challenge
    Given the user "fulano" is already logged on
    And the user is a content creator user
    And the type of the challenge is "compilation challenge"
    And the text of the challenge is 
    """
    public class Hola {
    """
    And the text of the challenge does not compile
    And the user sets the challenge score on 5
    When the user submits the challenge
    Then the system creates a new id for the challenge


