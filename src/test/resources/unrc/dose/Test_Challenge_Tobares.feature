
Feature: The user "pponzio" resolved a test challenge 
    Scenario:User solves an unsolved test based challenge
    Given that user "pponzio" is already logged on
    And he wants to solve the test based challenge with id 21
    And this challenge has not been solved yet
    And the text of this challenge is
   """"
   public static int max (int a, int b){
        if (a < b) {
     return a;
    }
    else{
        return b;
    } 
    }

    """
    And the par for the challenge is 5
    And the test of the challenge is 

    """

    @Test public void testMax(){ 
        assertEquals(6,max(5,6)); 
        }

    """
    And the proposed solution is 
    """
    public static int max (int a, int b){
        if (a > b) {
         return a;
        }
        else{
         return b;
        } 
    }

    """
    When the user submits the solution
    Then the system should inform that the solution passed the tests
    And that the distance of the solution is 1
    And user pponzio receives a score of 4 for solving the challenge
    