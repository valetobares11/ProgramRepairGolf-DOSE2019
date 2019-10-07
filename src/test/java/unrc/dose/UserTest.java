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
	 * delete an user
	 * @result delete logically a user logged
	 */
	@Test
	public void deleteUserSuccessfully(){
		String username = "JohnConnor";
		String password = "VeryHardPass";

		assertEquals(true,User.deleteUser(username, password));
	}

	/**
	 * delete Unsuccessfully user
	 * @result user not found
	 */
	@Test
	public void deleteUserUnsuccessfully(){
		String username = "Pity";
		String password = "Martinez";

		assertEquals(false,User.deleteUser(username, password));
	}

}
