package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class to test the CompilationChallenge class methods.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public class CompilationChallengeTest //extends DBSpec 
{

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			System.out.println("CompilationChallengeTest setup");
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

		Challenge.addCompilationChallenge(u.getInteger("id"), "Test", "Test", "description",
		"source", 100, 0);
		Challenge c = Challenge.findFirst("title = ?", "Test");
		Challenge.addCompilationChallenge(u.getInteger("id"), "Test1", "Test1", "description",
		"source", 100, 0);
		Challenge c1 = Challenge.findFirst("title = ?", "Test1");
		Challenge.addCompilationChallenge(u.getInteger("id"), "Test2", "Test2", "description",
		"source", 100, 0);
		Challenge c2 = Challenge.findFirst("title = ?", "Test2");
		Challenge.addCompilationChallenge(u.getInteger("id"), "Test3", "Test3", "description",
		"source", 100, 0);
		
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
		assertEquals(4, all.size());
		assertEquals("Test", all.get(0).getString("title"));
		assertEquals("Test1", all.get(1).getString("title"));
		assertEquals("Test2", all.get(2).getString("title"));
		assertEquals("Test3", all.get(3).getString("title"));
	}

	/**
	 * Test method for viewResolvedCompilationChallange.
	 */
	@Test
	public void viewResolvedCompilationChallangeTest() {
		List<Challenge> resolved = CompilationChallenge.viewResolvedCompilationChallange();
		assertEquals(2, resolved.size());
		assertEquals("Test1", resolved.get(0).getString("title"));
		assertEquals("Test2", resolved.get(1).getString("title"));
	}

	/**
	 * Test method for viewUnsolvedCompilationChallange.
	 */
	@Test
	public void viewUnsolvedCompilationChallangeTest() {
		List<Challenge> unsolved = CompilationChallenge.viewUnsolvedCompilationChallange();
		assertEquals(2, unsolved.size());
		assertEquals("Test", unsolved.get(0).getString("title"));
		assertEquals("Test3", unsolved.get(1).getString("title"));
	}

	/**
	 * Test method for modifyUnsolvedCompilationChallenge
	 * In case the challenge is solved.
	 */
	@Test
	public void modifyUnsolvedCompilationChallengeTest() {
		Challenge c = Challenge.findFirst("title = ?", "Test1");
		String title = "Change1";
		String className = "NotFound";
		String description = "";
		String source = "//";
		int point = 0;
		try{
			CompilationChallenge.modifyUnsolvedCompilationChallenge(
				c.getInteger("id"),
				title,
				className,
				description,
				source,
				point);
			fail();
		} catch (RuntimeException ex) {
			assertEquals(Challenge.CHALLENGE_RESOLVED, ex.getMessage());
		}
	}

	/**
	 * Test method for modifyUnsolvedCompilationChallenge
	 * In case the challenge is unsolved.
	 */
	@Test
	public void modifyUnsolvedCompilationChallengeTest1() {
		Challenge c = Challenge.findFirst("title = ?", "Test3");
		String title = "Change1";
		String className = "NotFound";
		String description = "";
		String source = "//";
		int point = 0;
		boolean obtained = CompilationChallenge.modifyUnsolvedCompilationChallenge(
			c.getInteger("id"),
			title,
			className,
			description,
			source,
			point);
		assertTrue(obtained);
	}

} 
