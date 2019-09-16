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


Scenario: A content creator user creates a new test challenge
    Given the the user "fulano2" is already logged on
    And the user is a content creator user
    And the type of the challenge is “test challenge”
    And the text of the challenge is 
    """
    public class Min {
	    Static Int min(int a, int b) {
	    	return a;
	    }
    }
    """
    And the text of the challenge compiles
    And the text of the tests is:
    """
		@Test
		public void test1(){
			Int result = Min.min(1, 5);
			assertEquals(5,result);
		}
		@Test
		public void test2(){
			Int result = Min.min(5, -5);
			assertEquals(-5,result);
		}
    """
    And the at least one of the tests fails
    And the user sets the challenge score on 80
    When the user submits the challenge
    Then the system creates a new id for the challenge
    And saves the challenge

