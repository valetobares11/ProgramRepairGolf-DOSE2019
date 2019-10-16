package unrc.dose;

import unrc.dose.TestChallenge;
import unrc.dose.Challenge;
import unrc.dose.Tuple;
import unrc.dose.Proposition;
import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.List;

public class TestChallengeTest {

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			System.out.println("TestChallengeTest setup");
			Base.openTransaction();
		}

		User u = new User();
		u.set("username", "test");
		u.set("password", "1234");
		u.set("email_address", "test@example.com");
		u.saveIt();

		User u1 = new User();
		u1.set("username", "test1");
		u1.set("password", "1234");
		u1.set("email_address", "test1@example.com");
		u1.saveIt();

		Challenge.addTestChallenge(1, "Test", "Test", "//", "ssource", 100, 1, "test");
		Challenge c = Challenge.findFirst("title = ?", "Test");
		Challenge.addTestChallenge(1, "Test1", "Test1", "description", "source", 100, 0, "test");
		Challenge c1 = Challenge.findFirst("title = ?", "Test1");
		Challenge.addTestChallenge(1, "Test2", "Test2", "description", "source", 100, 0, "test");
		Challenge c2 = Challenge.findFirst("title = ?", "Test2");
		Challenge.addTestChallenge(1, "Test3", "Test3", "description", "source", 100, 0, "test");
		
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
	 * Test the method set and get
	 * from TestChallenge class 
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
	 * Test the method validateTestChallenge
	 * from TestChallenge class 
	 */
	@Test
	public void validateTestChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.saveIt();
		TestChallenge testChallenge = new TestChallenge();
		boolean validate = TestChallenge.validateTestChallenge(challenge,testChallenge);
		assertEquals(true,validate);
	}

	/**
	 * Test the method addTestChallenge
	 * from TestChallenge class 
	 */
	@Test
	public void addTestChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.saveIt();
		int challengeId = challenge.getInteger("id");
		String test = "this is a challenge test";
		TestChallenge testChallenge = TestChallenge.addTestChallenge(challengeId,test);
		assertEquals(challengeId,testChallenge.getChallengeId());
		assertEquals(test,testChallenge.getTest());
	}

	/**
	 * Test the method viewAllTestChallange
	 */
	@Test
	public void viewAllTestChallangeTest() {
		List<Tuple<Challenge, TestChallenge>> all = TestChallenge.viewAllTestChallange();
		String resul = all.get(0).getFirst().getString("title");
		String resul1 = all.get(1).getFirst().getString("title");
		String resul2 = all.get(2).getFirst().getString("title");
		String resul3 = all.get(3).getFirst().getString("title");
		assertEquals(4, all.size());
		assertEquals("Test", resul);
		assertEquals("Test1", resul1);
		assertEquals("Test2", resul2);
		assertEquals("Test3", resul3);
	}

	/**
	 * Test the method viewResolvedTestChallang
	 */
	@Test
	public void viewResolvedTestChallangeTest() {
		List<Tuple<Challenge, TestChallenge>> resolved = TestChallenge.viewResolvedTestChallange();
		String resul = resolved.get(0).getFirst().getString("title");
		String resul1 = resolved.get(1).getFirst().getString("title");
		assertEquals(2, resolved.size());
		assertEquals("Test1", resul);
		assertEquals("Test2", resul1);
	}

	/**
	 * Test the method viewResolvedTestChallang
	 */
	@Test
	public void viewUnsolvedTestChallangeTest() {
		List<Tuple<Challenge, TestChallenge>> unsolved = TestChallenge.viewUnsolvedTestChallange();
		String resul = unsolved.get(0).getFirst().getString("title");
		String resul1 = unsolved.get(1).getFirst().getString("title");
		assertEquals(2, unsolved.size());
		assertEquals("Test", resul);
		assertEquals("Test3", resul1);
	}

}
