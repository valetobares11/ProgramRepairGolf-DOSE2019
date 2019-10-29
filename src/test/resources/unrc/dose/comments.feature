Feature: comments
      Scenario: User wants to create a new comment to a challenge
        Given the user "Pablo" is already logged on
        And the challenge exists
        When the user writes the title "titleEx" and the description "descriptionEx"
        Then the system will saves the comment

      Scenario: User wants to create a new response to a commment
        Given the user "Pedro" is already logged on
        And the comment already exists
        When the user writes the title "titleEx" of the response and the description "descriptionEx" to the comment with id 1
        Then the system will save the response

      Scenario: User wants to see the comments by another user
        Given the user "Pablo" is already logged on
        And the user "Pedro" exists
        When the user wants to see the comments "comment1"
        Then the system will return a list of comments of the user

      Scenario: User wants to see the comments of a challenge
        Given the user "Pablo" is already logged on
        And the challenge exists
        When the user wants to see the comments
        Then the system will return a list of comments of the challenge

      Scenario: User wants to see the comments of a challenge
        Given the user "Pablo" is already logged on
        And the comment exists
        When the user wants to see the responses of that comment
        Then the system will return a list of responses to the comment
