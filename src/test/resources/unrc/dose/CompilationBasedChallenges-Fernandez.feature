# Brusati, Cuesta, Fernandez, Manzetti

Feature: Compilation Based Challeges
	Scenario: User solves a compilation based challenge with the minimal distance
		Given that user <user> is already logged on
		And he wants to solve a compile challenge with id <id>
		And that challenge have a score of 20
		And the text of this challenge is
			"""
			public class suma {
				public void sumatoria (int array) { 
				int res;
				for (int i=0; i < array.length; i--) 
					res:= res+array[i];
					return res
				}
			}
			"""
		And the proposed solution is
			"""
			public class suma {
			public int sumatoria (int[] array) { 
				int res=0;
				for (int i=0; i < array.length; i++) 
					res= res+array[i];
					return res;
				}
			}
			"""
		When the user submits his solution
		Then the system informs that the solution is valid
		And the score obtained by <user> is 20 - 9
