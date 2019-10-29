package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import spark.Spark;

import unrc.dose.User;
import org.junit.Test;


public class UserTest {
	
	@BeforeClass
  	public static void beforeAll() {
  		if (!Base.hasConnection()) {
	      	Base.open();
	      	Base.openTransaction();
	       	User pepe = User.set("Enzo" , "Ferrari" , "F40@gmail.com" , false);
	    }
  	}

  	@AfterClass
  	public static void tearDown() {
    	Base.rollbackTransaction();
    	Base.close();
    }
    /**
	 * test that verifies that the password provided is the same as the current password.
	 * @result false since the password of the previous account cannot be updated with the same password
	*/
	@Test
	public void repeatedPassword() {
		String email = "F40@gmail.com";
		String oldPass = "Ferrari";
		String newPass = "Ferrari";

		assertEquals(false, User.updatePassword(email, oldPass, newPass));
	}
	/**
	 * test that verifies that the password has been modified successfully
	 * @result false bescause the email is not registered in the DB
	 */
	@Test
	public void updatePasswordUnSuccessfully() {
		String email = "juanPerez@gmail.com";
		String oldPass = "Ferrari";
		String newPass = "NewPass";

		assertEquals(false, User.updatePassword(email, oldPass, newPass));
	}



	/**.
	 * test that verifies that the password could not be modified
	 * @result true (password reset)
	*/
	@Test
	public void updatePasswordSuccessfully() {
		String email = "F40@gmail.com";
		String newPass = "NewPass";
		String oldPass = "Ferrari";

		assertEquals(true, User.updatePassword(email, oldPass, newPass));
	}
    /**
    * found a user by his username succesfully
    * @result user found 
    */
	@Test
	public void userFoundByName() {
		String name = "Enzo";

		assertEquals(true , User.userExistsByUsername(name));
	}

    /**
    * user not found by his username
    * @result user not found  
    */
	@Test
	public void userNotFoundByName() {
		String name = "Pity";

		assertEquals(false , User.userExistsByUsername(name));
	}

    /**
    * found a user by his email_address succesfully 
    * @result email_addres found  
    */
	@Test
	public void userFoundByEmail() {
		String email = "F40@gmail.com";

		assertEquals(true , User.userExistsByEmail(email));
	}

    /**
    * user not found by his email_address
    * @result email_addres not found  
    */
	@Test
	public void userNotFoundByEmail() {
		String email = "YvaelTercero@gmail.com";

		assertEquals(false , User.userExistsByEmail(email));
	}

    /**
    * load all the fields of a user with his data
    * @result user charged with fields that have passed by the user 
    */
	@Test
	public void userSet() {
		String name = "Enzo" ;
		User pepe = User.findFirst("username = ? " , name);
		assertEquals(name , pepe.get("username"));
	}

	/**
	 * delete an user
	 * @result delete logically a user logged
	 */
	@Test
	public void deleteUserSuccessful(){
		String username = "Enzo";
		String password = "Ferrari";
        
		assertEquals(true, User.disableUser(username, password));
	}

	/**
	 * delete Unsuccessfully user
	 * @result user not found
	 */
	@Test
	public void deleteUserUnsuccessful(){
		String username = "Pity";
		String password = "Martinez";

		assertEquals(false, User.disableUser(username, password));
	}
	/**.
	 * test that verifies that not exists an username with east emails
	 * @result false because no user with east email provided
	*/
	@Test
	public void userNotExists() {
		String email = "F@gmail.com";
		String name = "Enzo";

		assertEquals(false, (User.userExistsByUsername(name) && User.userExistsByEmail(email)));
	}

	/**
	 * test that verifies that  exists an username with east emails
	 * @result true because exists an user with east email provided
	*/
	@Test
	public void userExists() {
		String email = "F40@gmail.com";
		String name = "Enzo";

		assertEquals(true, (User.userExistsByUsername(name) && User.userExistsByEmail(email)));
	}

	

	/**
	 * test that verifies that the password provided doesn't comply with password policies, 
	 * where the password character range must be greater than 6 and less than 20
	 * @result false since the password doesn't comply with password policies
	*/
	@Test
	public void invalidPasswordForInvalidPolicies() {
		String email = "F40@gmail.com";
		String oldPass = "Ferrari";
		String newPass = "1234";

		assertEquals(false, User.updatePassword(email, oldPass, newPass));
	}


	/**
	 * Unsuccessfully validate Credentials the user
	 * @result user not found
	 */
	@Test
	public void userValidateUnsuccessful(){
		String username = "Pity";
		String password = "Martinez";

		assertEquals(false, User.validateCredentials(username, password));
	}

	/**
	 * Successfully validate Credentials the user with data correct
	 * @result user found whit data get into
	 */
	@Test
	public void userValidateSuccessful(){
		String username = "Enzo";
		String password = "Ferrari";

		assertEquals(true, User.validateCredentials(username, password));
	}

	/**
	 * Successfully validate Credentials the user with password incorrect
	 * @result user not found
	 */
	@Test
	public void userValidateUnsuccessfulWithoutPassCorrect(){
		String username = "Enzo";
		String password = "Fer";

		assertEquals(false, User.validateCredentials(username, password));
	}

	/**
	 * Successfully validate Credentials the user with password or username incorrect
	 * @result user found with correct data 
	 */
	@Test
	public void userValidateUnsuccessfulWithoutDataCorrect(){
		String username = "Enzi";
		String password = "Ferrari";

		assertEquals(false, User.validateCredentials(username, password));
	}
}
