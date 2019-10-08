package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import java.awt.List;

import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.LazyList;

import spark.Spark;

import unrc.dose.Proposition;
import org.junit.Test;
import org.javalite.activejdbc.LazyList;

public class PropositionTest {

	@BeforeClass
  	public static void beforeAll() {
		if (!Base.hasConnection()) {
			Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1/repair_game_test?nullNamePatternMatchesAll=True", "admin", "password");
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
	public void newPropositionTest() {
  		Challenge challenger = new Challenge();
  		User usr = new User();
  		challenger.set("title", "Challenger 1");
  		challenger.set("source", "hola mundo;");
  		usr.set("username", "Gaston");
  		usr.set("password", "abc123");
  		usr.set("email_address", "pepe@gmail.com");
  		challenger.saveIt(); 
  		usr.saveIt();
  		int challengerId = challenger.getInteger("id");
  		int usrId = usr.getInteger("id");
  		
  		Proposition newProp = Proposition.newProposition(usrId, challengerId);
  		LazyList<Proposition> listProp = Proposition.where("user_id = ? and challenge_id = ?", usrId, challengerId);   		
  		Proposition propDB = listProp.get(0);
  		
  		assertEquals(newProp.getInteger("id"), propDB.getInteger("id"));
  		
  		Proposition.deleteAll();
  		Challenge.deleteAll();
  		User.deleteAll();
	}

	
	//------test para isue --> get compromised solution #101
	@Test 
	public void solutionsForUserInChallengeTest() {
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
  		  		
 	  		
  		Proposition p1 = new Proposition();
		p1.set("challenge_id", challId);
		p1.set("user_id", usrId);
		p1.set("source", "System.out.println('Hello World');");
		p1.set("isSubmit", 1);
		p1.set("distance", Integer.MAX_VALUE);
		p1.set("cantTestPassed", 0);
		p1.saveIt();
		  
		Proposition p2 = new Proposition();
		p2.set("challenge_id", challId);
		p2.set("user_id", usrId);
		p2.set("source", "System.out.println('Hello World');");
		p2.set("isSubmit", 1);
		p2.set("distance", Integer.MAX_VALUE);
		p2.set("cantTestPassed", 0);
		p2.saveIt();

		Proposition proposition = new Proposition();

		LazyList<Proposition> propositions = proposition.solutionsForUserInChallenge(usrId, challId);

		assertEquals(2, propositions.size());

		Proposition.deleteAll();
		Challenge.deleteAll();
		User.deleteAll();
	}

	// ----test para issue --> save a proposed solutipon as definitive #104
		@Test
		public void saveSolutionTest() {
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

			Proposition p1 = new Proposition();
			p1.set("challenge_id", challId);
			p1.set("user_id", usrId);
			p1.set("source", "System.out.println('Hello World');");
			p1.set("isSubmit", 0);
			p1.set("distance", Integer.MAX_VALUE);
			p1.set("cantTestPassed", 0);
			p1.saveIt();

			p1.saveSolution("hola mundo;", 10);

			int isSubmit = p1.getIsSubmit();

			assertEquals(1, isSubmit);

			Proposition.deleteAll();
			Challenge.deleteAll();
			User.deleteAll();
		}

	
	
	@Test
  	public void nullGetPropositionNoSubmitTest() {
  		
  		assertTrue(Proposition.getPropositionNoSubmit(0, 0).isEmpty());
  	
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
  		
		
		LazyList<Proposition> proposition = Proposition.getPropositionNoSubmit(usrId, challId);
  		
  		
  		assertEquals(1, proposition.size());
  		Proposition.deleteAll();
  		Challenge.deleteAll();
  		User.deleteAll();

  			
  	}
  	
  	@Test
  	public void nullAllSolutionChallengeTest() {
  		assertTrue(Proposition.allSolutionChallenge(0).isEmpty());
  	}
  	
  	@Test
  	public void allSolutionChallengeTest() {
  		Challenge challenge = new Challenge();
  		User usr1 = new User();
  		User usr2 = new User();
  		Proposition solution1 = new Proposition();
  		Proposition solution2 = new Proposition();
  		challenge.set("title", "challenge 2");
  		usr1.set("username", "Gaston");
  		usr1.set("password", "abc123");
  		usr1.set("email_address", "pepe@gmail.com");
  		usr2.set("username", "Agustin");
  		usr2.set("password", "abc123");
  		usr2.set("email_address", "agustin@gmail.com");
  		challenge.saveIt();
  		usr1.saveIt();
  		usr2.saveIt();
  		solution1.set("user_id", usr1.getInteger("id"));
  		solution1.set("challenge_id", challenge.getInteger("id"));
  		solution1.set("source", "Hola mundo!!;");
  		solution1.set("isSubmit", 1);
  		solution2.set("user_id", usr2.getInteger("id"));
  		solution2.set("challenge_id", challenge.getInteger("id"));
  		solution2.set("source", "Hola mundo!!;");
  		solution2.set("isSubmit", 1);
  		solution1.saveIt();
  		solution2.saveIt();
  		
  		LazyList<Proposition> allSolution = Proposition.allSolutionChallenge(challenge.getInteger("id"));
  		
  		assertEquals(2,allSolution.size());
  		Proposition.deleteAll();
  		Challenge.deleteAll();
  		User.deleteAll();
  	}

  	@Test
  	public void sameDistance() {
  		String str1 = "hola";
  		String str2 = "";
  		
  		assertEquals(4, Proposition.computeLevenshteinDistance(str1, str2));
  	}
  	
  	@Test
  	public void distanceZero() {
  		String str1 = "hola";
  		String str2 = "hola";
  		
  		assertEquals(0, Proposition.computeLevenshteinDistance(str1, str2));
  	}
  	
  	@Test
  	public void getDistancePTotal() {
  		Challenge challenger = new Challenge();
  		User usr = new User();
  		challenger.set("title", "Challenger 1");
  		challenger.set("source", "hello");
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
		p.set("source", "");
		p.set("isSubmit", 0);
		p.set("distance", 5);
		p.set("cantTestPassed", 0);
		p.saveIt();
		
  		assertEquals(5, Proposition.getDistanceProposition(p));
  		Proposition.deleteAll();
  		Challenge.deleteAll();
  		User.deleteAll();
  	}

	@Test
  	public void getDistancePZero() {
		Challenge challenger = new Challenge();
  		User usr = new User();
  		challenger.set("title", "Challenger 1");
  		challenger.set("source", "hello");
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
		p.set("source", "hello");
		p.set("isSubmit", 0);
		p.set("distance", 0);
		p.set("cantTestPassed", 0);
		p.saveIt();

  		assertEquals(0, Proposition.getDistanceProposition(p));
  		Proposition.deleteAll();
  		Challenge.deleteAll();
  		User.deleteAll();
	  }
}
