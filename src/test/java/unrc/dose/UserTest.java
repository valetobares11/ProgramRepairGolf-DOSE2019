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
    	App.main(null);

		Spark.awaitInitialization();
      	Base.open();
  	}


  	@AfterClass
  	public static void tearDown() {
    	Spark.stop();
    	Base.close();
  	}

    /**
    * found a user by his username succesfully
    * @result user found 
    */
	@Test
	public void userFoundByName() {
		String name = "JohnConnor";

		assertEquals(false , User.searchUserByUsername(name));
	}

    /**
    * user not found by his username
    * @result user not found  
    */
	@Test
	public void userNotFoundByName() {
		String name = "Pity";

		assertEquals(true , User.searchUserByUsername(name));
	}

    /**
    * found a user by his email_address succesfully 
    * @result email_addres found  
    */
	@Test
	public void userFoundByEmail() {
		String email = "JohnConnor@gmail.com";

		assertEquals(false , User.searchUserByEmail(email));
	}

    /**
    * user not found by his email_address
    * @result email_addres not found  
    */
	@Test
	public void userNotFoundByEmail() {
		String email = "YvaelTercero@gmail.com";

		assertEquals(true , User.searchUserByEmail(email));
	}

    /**
    * load all the fields of a user with his data
    * @result user charged with fields that have passed by the user 
    */
	@Test
	public void userSet() {
		String name = "Enzo";
		String password = "Ferrari";
		String email = "F40@gmail.com";
		Boolean admin = false;
		User pepe = new User();

		pepe = User.set(name, password, email, admin);	

		assertEquals(name , pepe.get("username"));
	}
	/**
	 * test that verifies that the password has been modified successfully
	'' * @result false bescause the email is not registered in the DB
	 */
	@Test
	public void updatePasswordUnSuccessfully() {
		String email = "juanPerez@gmail.com";
		String pass = "NewPass";

		assertEquals(false, User.updatePassword(email, pass));
	}


	/**.
	 * test that verifies that the password could not be modified
	 * @result true (password reset)
	*/
	@Test
	public void updatePasswordSuccessfully() {
		String email = "JohnConnor@gmail.com";
		String pass = "NewPass";

		assertEquals(true, User.updatePassword(email, pass));
	}
	
	/**.
	 * test that verifies that not exists an username with east emails
	 * @result true because no user with east email provided
	*/
	@Test
	public void userNotExists() {
		String email = "JohnConnor@gmail.com";
		String name = "juan";

		assertEquals(true, User.searchUsernameAndEmail(email, name));
	}

	/**
	 * test that verifies that  exists an username with east emails
	 * @result false because exists an user with east email provided
	*/
	@Test
	public void userExists() {
		String email = "JohnConnor@gmail.com";
		String name = "JohnConnor";

		assertEquals(false, User.searchUsernameAndEmail(email, name));
	}

	/**
	 * test that verifies that the password provided is the same as the current password.
	 * @result false since the password of the previous account cannot be updated with the same password
	*/
	@Test
	public void repeatedPassword() {
		String email = "JohnConnor@gmail.com";
		String newPass = "VeryHardPass";

		assertEquals(false, User.updatePassword(email, newPass));
	}

	/**
	 * test that verifies that the password provided doesn't comply with password policies, 
	 * where the password character range must be greater than 6 and less than 20
	 * @result false since the password doesn't comply with password policies
	*/
	@Test
	public void invalidPassword() {
		String email = "JohnConnor@gmail.com";
		String newPass = "1234";

		assertEquals(false, User.updatePassword(email, newPass));
	}
}