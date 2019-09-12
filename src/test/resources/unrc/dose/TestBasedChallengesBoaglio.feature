Feature: A user solves a simple test challenge with less editing distance 

Scenario: User unsolves a simple unsolved test based challenge .
    Given that user "pponzio" is already logged on
    And wants to solve the test based challenge with id 5
    And this challenge has not been solved yet
    And the user does not have a score assigned in this challenge
    And the text of this challenge is
        """
        public static int searchFirstItem(int[ ] array) {
                return array[4];
        }
        """
    And the par for the challenge is 50;
    And the test of the challenge is
        """
        @Test
            public void testSearchFirstItem(int[ ] array){
                assertEquals(1, searchFirstItem( {1,2,4} ));
            }
        """
    And the proposed solution is
        """
        public int searchFirstItem(int[ ] array){
            int res = array[0];
            return res;                    
        }
        """
    When the user submits the solution
    Then the system should inform that the solution passed the tests
    And that the distance of the solution is 24
    And user "pponzio" receives a score of 26 for solving the challenge