package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import spark.Spark;

import unrc.dose.Proposition;
import org.junit.Test;

public class PropositionTest {
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


	//--------test get----//
	@Test
	public void invalidUserId() {
		Proposition proposition = new Proposition();
		proposition.setUserId(-1);
		assertEquals(proposition.isValid(), false);
	}

	@Test
	public void invalidChallengeId() {
		Proposition proposition = new Proposition();
		proposition.setChallengeId(-1);
		assertEquals(proposition.isValid(), false);
	}

	@Test
	public void sourceIsEmpty() {
		Proposition proposition = new Proposition();
		proposition.setSource("");
		assertEquals(proposition.isValid(), false);
	}

	@Test
	public void invalidIsSubmit() {
		Proposition proposition = new Proposition();
		proposition.setIsSubmit(-1);
		assertEquals(proposition.isValid(), false);
	}

	@Test
	public void invalidDistance() {
		Proposition proposition = new Proposition();
		proposition.setDistance(-1);
		assertEquals(proposition.isValid(), false);
	}

	@Test
	public void invalidCantTestPassed() {
		Proposition proposition = new Proposition();
		proposition.setCantTestPassed(-1);
		assertEquals(proposition.isValid(), false);
	}

	//-------end test get-----//


  	@Test
  	public void sameDistance() {
  		String str1 = "Hola";
  		String str2 = "";
  		
  		assertEquals(4, Proposition.computeLevenshteinDistance(str1, str2));
  	}

  	@Test
  	public void distanceZero() {
  		String str1 = "Hola";
  		String str2 = "Hola";

  		assertEquals(0, Proposition.computeLevenshteinDistance(str1, str2));
	  }
	  


}
