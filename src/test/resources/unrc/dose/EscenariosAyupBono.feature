Feature: User solve challenge

Scenario: User solves an unsolved compile challenge with the maximum distance
		Given the user "Fulano" is already logged
		And he wants to solve the compile challenge with id 20
		And this challenge it's already been resolved
		And the text of this challenge is
		"""
        	public class Hola {
		"""        	       	
		And the proposed solution is
		"""        
        public class Hola {}
		"""        
		When the user submits his solution
		Then the system should inform that the solution is valid
		And that the distance of the solution is 25
		And the user is in 25th place in the ranking of challenge 20
	
Scenario: User unsolves a simple unsolved test based challenge
     	Given that user "Fulano" is already logged on
     	And he wants to solve the test based challenge with id 15
     	And this challenge has not been solved yet
     	And the text of this challenge is
	 	"""
         Public static int isSum (int a, int b){
                    a*b;}
        """           
     	And the test of the challenge is 
		"""
         @Test
             Public void testIsSum(){
             assertEquals(7,isSum(4,3));
    		}
		"""  		
     	And the proposed solution is 
		"""
                  Public static int isSum (int a, int b){
                   a – b;
}
 
		"""
     	When the user submits his solution
     	Then the system should inform that the solution is invalid
     	And the system should inform that the solution has not passed the test
     	And the system show the message “back to in”
     

Scenario: A non-logged user wants to solve a challenge
    	Given that user "Fulano" is not logged on
    	And user wants to solve a challenge
    	When the user press solve challenge
    	Then the system show message  "must log in to solve challenge"
    

Scenario: A user wants to create an account with a username that already exists
    	Given that a user wants to create an account
    	And the username selected is "pponzio"
    	And the selected password is "dose19"
    	And the users email is "pponzio@hotmail.com"
		And a user exists in the system with the given username
		When the user tries to create an account with the given credentials
		Then the system reports that the account cannot be created

