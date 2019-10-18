package unrc.dose;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class to test the CompilationChallenge class methods.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public class CompilationChallengeTest {

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			System.out.println("CompilationChallengeTest setup");
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

		Challenge.addCompilationChallenge(1, "Test", "Test", "description", "source", 100, 0);
		Challenge c = Challenge.findFirst("title = ?", "Test");
		Challenge.addCompilationChallenge(1, "Test1", "Test1", "description", "source", 100, 0);
		Challenge c1 = Challenge.findFirst("title = ?", "Test1");
		Challenge.addCompilationChallenge(1, "Test2", "Test2", "description", "source", 100, 0);
		Challenge c2 = Challenge.findFirst("title = ?", "Test2");
		Challenge.addCompilationChallenge(1, "Test3", "Test3", "description", "source", 100, 0);
		
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
			Base.rollbackTransaction();
			System.out.println("Compilation ChallengeTest tearDown");
			Base.close();
		}  
	}

	/**
	 * Test methods for set and get.
	 */
	@Test
	public void setAndGetTest() {
		CompilationChallenge compChallenge = new CompilationChallenge();
		compChallenge.setChallengeId(31);
		compChallenge.saveIt();
		assertEquals(31,compChallenge.getChallengeId());
	}

	/**
	 * Test method for validateCompilationChallenge.
	 */
	@Test
	public void validateCompilationChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.setUserId(1); 
		challenge.setTitle("Hello Word");
		challenge.setClassName("HelloWordx2");
		challenge.setSource("public String hello(){ return "+"HelloWord"+" }");
		challenge.setPoint(100);
		challenge.setOwnerSolutionId(9);
		challenge.saveIt();
		boolean validate = CompilationChallenge.validateCompilationChallenge(challenge);
		assertEquals(true ,validate);
	}

	/**
	 * Test method for addCompilationChallenge.
	 */
	@Test
	public void addCompilationChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.setUserId(1); 
		challenge.setTitle("Hello Word");
		challenge.setClassName("HelloWord");
		challenge.setSource("public String hello(){ return "+"HelloWord"+"; }");
		challenge.setPoint(100);
		challenge.setOwnerSolutionId(9);
		challenge.saveIt();
		int challengeId = challenge.getInteger("id");
		CompilationChallenge compChallenge = CompilationChallenge.addCompilationChallenge(challengeId);
		assertEquals(challengeId,compChallenge.getChallengeId());
	}

	/**
	 * Test method for viewAllCompilationChallange.
	 */
	@Test
	public void viewAllCompilationChallangeTest() {
		List<Challenge> all = CompilationChallenge.viewAllCompilationChallange();
		String resul = all.get(0).getString("title");
		String resul1 = all.get(1).getString("title");
		String resul2 = all.get(2).getString("title");
		String resul3 = all.get(3).getString("title");
		assertEquals(4, all.size());
		assertEquals("Test", resul);
		assertEquals("Test1", resul1);
		assertEquals("Test2", resul2);
		assertEquals("Test3", resul3);
	}

	/**
	 * Test method for viewResolvedCompilationChallange.
	 */
	@Test
	public void viewResolvedCompilationChallangeTest() {
		List<Challenge> resolved = CompilationChallenge.viewResolvedCompilationChallange();
		String resul = resolved.get(0).getString("title");
		String resul1 = resolved.get(1).getString("title");
		assertEquals(2, resolved.size());
		assertEquals("Test1", resul);
		assertEquals("Test2", resul1);
	}

	/**
	 * Test method for viewUnsolvedCompilationChallange.
	 */
	@Test
	public void viewUnsolvedCompilationChallangeTest() {
		List<Challenge> unsolved = CompilationChallenge.viewUnsolvedCompilationChallange();
		String resul = unsolved.get(0).getString("title");
		String resul1 = unsolved.get(1).getString("title");
		assertEquals(2, unsolved.size());
		assertEquals("Test", resul);
		assertEquals("Test3", resul1);
	}

} 
