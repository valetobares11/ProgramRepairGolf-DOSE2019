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

	@Test
	public void userFound(){
		String name = "JohnConnor";
		String pass = "VeryHardPass";
		String email = "JohnConnor@gmail.com";

		assertEquals(true ,User.searchUser(name, pass, email));
	}


	@Test
	public void userNotFound(){
		String name = "Pity";
		String pass = "Martinez";
		String email = "YvaelTercero@gmail.com";

		assertEquals(false ,User.searchUser(name, pass, email));
	}
}