Scenario: Admin user wants to create a challenge that already has been submitted
  Given the user “JohnyMeLavo” is already logged on
  And he has admin permission
  And the challenge is already charged in the system
  When he submits the challenge
  Then the system shouldn’t charge the challenge
  And inform the user that his challenge is equal another challenge that already has been charged
