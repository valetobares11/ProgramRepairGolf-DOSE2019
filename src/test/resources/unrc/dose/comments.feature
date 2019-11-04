Feature: comments
      Scenario: User wants to create a new comment to a challenge
        Given the user "Pablo" is already logged on
        And the challenge with the name "challenge" exists
        When the user writes the title "titleEx" and the description "descriptionEx"
        Then the system will saves the comment

      Scenario: User wants to create a new response to a commment
        Given the user "Pablo" is already logged on
        And the comment of the existed user "Pedro" with the title "comment" 
        And the description "this is a description"
        And is in the challenge with the name "challenge" exists
        When the user writes the description "descriptionEx" of the response
        Then the system will save the response

      Scenario: User wants to see the comments by another user
        Given the user "Pablo" is already logged on
        When the user wants to see the comments of the existed user "Pedro" 
        Then the system will return a list of comments of the user "Pedro"

      Scenario: User wants to see the comments of a challenge
        Given the user "Pablo" is already logged on
        And the challenge with the name "challenge" exists
        When the user wants to see the comments 
        Then the system will return a list of comments of the challenge

      Scenario: User wants to see the response of a comment
        Given the user "Pablo" is already logged on
        And the comment of the existed user "Pedro" with the title "comment" 
        And the description "this is a description"
        And is in the challenge with the name "challenge" exists
        When the user wants to see the responses of that comment
        Then the system will return a list of responses to the comment
