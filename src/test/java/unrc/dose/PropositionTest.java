package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
		if (!Base.hasConnection()) {
			Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1/repair_game_test?nullNamePatternMatchesAll=True", "root", "root");
			System.out.println("PropositionTest setup");
			Base.openTransaction();
  		}
  	}

  	@AfterClass
  	public static void tearDown() {
  		if (Base.hasConnection()) {
  			System.out.println("PropositionTest tearDown");
  			Base.rollbackTransaction();
  			Base.close();
  		}
  	}


	@Test
	public void getUserIdTest() {

		Proposition proposition = new Proposition();
		proposition.set("user_id", 2);
		int userId = proposition.getUserId();
		assertEquals(userId, 2);
		
	}

	@Test
	public void getChallengeIdTest() {

		Proposition proposition = new Proposition();
		proposition.set("challenge_id", 2);
		int challengeId = proposition.getChallengeId();
		assertEquals(challengeId, 2);

	}

	@Test
	public void getSourceTest() {

		Proposition proposition = new Proposition();
		proposition.set("source", "hola mundo;");
		String source = proposition.getSource();
		assertEquals(source, "hola mundo;");

	}

	@Test
	public void getIsSubmitTest() {

		Proposition proposition = new Proposition();
		proposition.set("isSubmit", 1);
		int isSubmit = proposition.getIsSubmit();
		assertEquals(isSubmit, 1);

	}

	@Test
	public void getDistanceTest() {

		Proposition proposition = new Proposition();
		proposition.set("distance", 1);
		int distance = proposition.getDistance();
		assertEquals(distance, 1);

	}

	@Test
	public void getCantTestPassedTest() {

		Proposition proposition = new Proposition();
		proposition.set("cantTestPassed", 1);
		int cantTestPassed = proposition.getCantTestPassed();
		assertEquals(cantTestPassed, 1);
	
	}



	@Test
	public void setUserIdTest() {

		Proposition proposition = new Proposition();
		proposition.setUserId(1);
		assertEquals(proposition.get("user_id"), 1);

	}
	
	@Test
	public void setChallengeIdTest() {

		Proposition proposition = new Proposition();
		proposition.setChallengeId(1);
		assertEquals(proposition.get("challenge_id"), 1);

	}

	
	@Test
	public void setSourceTest() {

		Proposition proposition = new Proposition();
		proposition.setSource("hola mundo;");
		assertEquals(proposition.get("source"), "hola mundo;");

	}

	
	@Test
	public void setIsSubmitTest() {

		Proposition proposition = new Proposition();
		proposition.setIsSubmit(1);
		assertEquals(proposition.get("isSubmit"), 1);

	}

	@Test
	public void setDistanceTest() {

		Proposition proposition = new Proposition();
		proposition.setDistance(1);
		assertEquals(proposition.get("distance"), 1);

	}

	@Test
	public void setCantTestPassedTest() {

		Proposition proposition = new Proposition();
		proposition.setCantTestPassed(1);
		assertEquals(proposition.get("cantTestPassed"), 1);

	}

	
	
	@Test
  	public void nullGetPropositionNoSubmitTest() {
  		
  		assertNull(Proposition.getPropositionNoSubmit(5, 2));
  	
  	}
  	
  	@Test
  	public void notNullGetPropositionNoSubmitTest() {
  		
  		Challenge challenger = new Challenge();
  		User usr = new User();
  		challenger.set("title", "Challenger 1");
  		usr.set("username", "Gaston");
  		usr.set("password", "abc123");
  		usr.set("email_address", "pepe@gmail.com");
  		challenger.saveIt(); 
  		usr.saveIt();
  		
  		Integer usrId = usr.getInteger("id");
  		Integer challId = challenger.getInteger("id");
  		  		
 	  		
  		Proposition p = new Proposition();
		p.set("challenge_id", challId);
		p.set("user_id", usrId);
		p.set("source", "System.out.println('Hello World');");
		p.set("isSubmit", 0);
		p.set("distance", Integer.MAX_VALUE);
		p.set("cantTestPassed", 0);
		p.saveIt();
  		
		
		Proposition proposition = Proposition.getPropositionNoSubmit(challId, usrId);
  		
  		
  		assertEquals(proposition.getInteger("id"), p.getInteger("id"));
  			
  	}


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
