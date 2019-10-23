Feature: comments
      Scenario: User wants to create a new comment to a challenge
        Given the user "Pablo" with id 1 is already logged on
        And the challenge with id 2 exists
        When the user writes the title and the description
        Then the system will saves the comment

      Scenario: User wants to create a new response to a commment
        Given the user "Pedro" with the id 2 is already logged on
        And the comment with id 1 already exists
        When the user writes the title of the response and the description
        Then the system will save the response

      Scenario: User wants to see the comments by another user
        Given the user "Pablo" with id 1 is already logged on
        And the user "Pedro" with id 2 exists
        When the user wants to see the comments
        Then the system will return a list of comments of the user

      Scenario: User wants to see the comments of a challenge
        Given the user "Pablo" with id 1 is already logged on
        And the challenge with id 2 exists
        When the user wants to see the comments
        Then the system will return a list of comments of the challenge

      Scenario: User wants to see the comments of a challenge
        Given the user "Pablo" with id 1 is already logged on
        And the comment with id 1 exists
        When the user wants to see the responses of that comment
        Then the system will return a list of responses to the comment
