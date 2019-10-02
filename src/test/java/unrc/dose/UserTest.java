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
	 * delete an user
	 * @result delete logically a user logged
	 */
	@Test
	public void deleteUserSuccessfully(){
		String username = "JohnConnor";
		String password = "VeryHardPass";

		assertEquals("Usuario eliminado",User.deleteUser(username, password));
	}

	/**
	 * delete Unsuccessfully user
	 * @result user not found
	 */
	@Test
	public void deleteUserUnsuccessfully(){
		String username = "Pity";
		String password = "Martinez";

		assertEquals("Usuario no encontrado",User.deleteUser(username, password));
	}
}



