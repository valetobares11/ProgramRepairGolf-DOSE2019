package unrc.dose;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class to test the TestChallenge class methods.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public class TestChallengeTest //extends DBSpec 
{

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			System.out.println("TestChallengeTest setup");
			Base.openTransaction();
		}

		//User u = User.set("test", "1234", "test@example.com", true);
		User u = new User();
		u.set("username", "test");
		u.set("password", "1234");
		u.set("email_address", "test@example.com");
		u.set("admin", true);
		u.saveIt();
		//User u1 = User.set("test1", "1234", "test1@example.com", false);
		User u1 = new User();
		u1.set("username", "test1");
		u1.set("password", "1234");
		u1.set("email_address", "test1@example.com");
		u1.saveIt();

		Challenge.addTestChallenge(u.getInteger("id"), "Test", "Test", "description",
		"ssource", 100, 1, "test");
		Challenge c = Challenge.findFirst("title = ?", "Test");
		Challenge.addTestChallenge(u.getInteger("id"), "Test1", "Test1", "description",
		"source", 100, 0, "test");
		Challenge c1 = Challenge.findFirst("title = ?", "Test1");
		Challenge.addTestChallenge(u.getInteger("id"), "Test2", "Test2", "description",
		"source", 100, 0, "test");
		Challenge c2 = Challenge.findFirst("title = ?", "Test2");
		Challenge.addTestChallenge(u.getInteger("id"), "Test3", "Test3", "description",
		"source", 100, 0, "test");

		Proposition p = new Proposition();
		p.set("user_id", u.getId());
		p.set("challenge_id", c.getId());
		p.set("source", "//");
		p.set("isSubmit", 0);
		p.saveIt();

		Proposition p1 = new Proposition();
		p1.set("user_id", u.getId());
		p1.set("challenge_id", c1.getId());
		p1.set("source","//");
		p1.set("isSubmit", 1);
		p1.saveIt();

		Proposition p2 = new Proposition();
		p2.set("user_id", u.getId());
		p2.set("challenge_id", c2.getId());
		p2.set("source","//");
		p2.set("isSubmit", 1);
		p2.saveIt();

		Proposition p3 = new Proposition();
		p3.set("user_id", u1.getId());
		p3.set("challenge_id", c2.getId());
		p3.set("source","//");
		p3.set("isSubmit", 1);
		p3.saveIt();
	
	}

	@AfterClass
	public static void after(){
		if (Base.hasConnection()) {
			System.out.println("TestChallengeTest tearDown");
			Base.rollbackTransaction();
			Base.close();
		}  
	}

	/**
	 * Test methods for set and get.
	 */
	@Test
	public void setAndGetTest() {
		TestChallenge testChallenge = new TestChallenge();
		testChallenge.setChallengeId(19);
		testChallenge.setTest("this is a challenge test");
		testChallenge.saveIt();
		assertEquals(19,testChallenge.getChallengeId());
		assertEquals("this is a challenge test",testChallenge.getTest());
	}

	/**
	 * Test method for validateTestChallenge.
	 */
	@Test
	public void validateTestChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.setUserId(1); 
		challenge.setTitle("Hello Word");
		challenge.setClassName("HelloWord");
		challenge.setSource("public String hello(){ return "+"HelloWord"+"; }");
		challenge.setPoint(100);
		challenge.setOwnerSolutionId(9);
		challenge.saveIt();
		TestChallenge testChallenge = new TestChallenge();
		testChallenge.set("id",challenge.getInteger("id"));
		testChallenge.setTest("--");
		testChallenge.saveIt();
		boolean validate = TestChallenge.validateTestChallenge(challenge,testChallenge);
		assertEquals(true,validate);
	}

	/**
	 * Test method for addTestChallenge.
	 */
	@Test
	public void addTestChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.setUserId(1); 
		challenge.setTitle("Hello Word");
		challenge.setClassName("HelloWord");
		challenge.setSource("public String hello(){ return "+"HelloWord"+"; }");
		challenge.setPoint(100);
		challenge.setOwnerSolutionId(9);
		challenge.saveIt();
		int challengeId = challenge.getInteger("id");
		String test = "this is a challenge test";
		TestChallenge testChallenge = TestChallenge.addTestChallenge(challengeId,test);
		assertEquals(challengeId,testChallenge.getChallengeId());
		assertEquals(test,testChallenge.getTest());
	}

	/**
	 * Test method for viewAllTestChallange.
	 */
	@Test
	public void viewAllTestChallangeTest() {
		List<Tuple<Challenge, TestChallenge>> all = TestChallenge.viewAllTestChallange();
		assertEquals(4, all.size());
		assertEquals("Test", all.get(0).getFirst().getString("title"));
		assertEquals("Test1", all.get(1).getFirst().getString("title"));
		assertEquals("Test2", all.get(2).getFirst().getString("title"));
		assertEquals("Test3", all.get(3).getFirst().getString("title"));
	}

	/**
	 * Test method for viewResolvedTestChallange.
	 */
	@Test
	public void viewResolvedTestChallangeTest() {
		List<Tuple<Challenge, TestChallenge>> resolved = TestChallenge.viewResolvedTestChallange();
		assertEquals(2, resolved.size());
		assertEquals("Test1", resolved.get(0).getFirst().getString("title"));
		assertEquals("Test2", resolved.get(1).getFirst().getString("title"));
	}

	/**
	 * Test method for viewResolvedTestChallange,
	 */
	@Test
	public void viewUnsolvedTestChallangeTest() {
		List<Tuple<Challenge, TestChallenge>> unsolved = TestChallenge.viewUnsolvedTestChallange();
		assertEquals(2, unsolved.size());
		assertEquals("Test", unsolved.get(0).getFirst().getString("title"));
		assertEquals("Test3", unsolved.get(1).getFirst().getString("title"));
	}

}
