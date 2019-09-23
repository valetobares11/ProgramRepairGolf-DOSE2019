Feature: the user resolves the challenge again, but better, and receives the maximum score of the challenge

Scenario: A user resolves a previously resolved challenge again
    Given that user "pponzio" is already logged on
    And wants to solve the test based challenge with id 5
    And this challenge has not been solved yet
    And the user already has a score assigned in this challenge
    And the text of this challenge is
        ```
        public static int searchFirstItem(int[ ] array) {
            return array[4];
        }
        ```
    And the par for the challenge is 50
    And the test of the challenge is
        ```
        @Test
            public void testSearchFirstItem(int[ ] array){
                assertEquals(1, searchFirstItem( {1,2,4} ));
            }
        ```
    And the proposed solution is
        ```
            public int searchFirstItem(int[ ] array){
                return array[0];                    
            }
        ```
    When the user submits the solution
    Then the system should inform that the solution passed the tests
    And that the distance of the solution is 1
    And user "pponzio" receives a score of 49 for solving the challenge
    And the score is better than the previous one
    And the new score the best of them
