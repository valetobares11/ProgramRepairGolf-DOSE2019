package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to test the CompilationChallenge class methods.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public class CompilationChallengeTest {

	private static final Logger log = LoggerFactory.getLogger(CompilationChallengeTest.class);

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			log.info("CompilationChallengeTest setup");
			Base.openTransaction();
		}
		
		User u = User.set("test", "1234", "test@example.com", true);
		User u1 = User.set("test1", "1234", "test1@example.com", false);

		CompilationChallenge.addCompilationChallenge(u.getInteger("id"), "Test", "Test", "description",
		"source", 100, 0);
		Challenge c = Challenge.findFirst("title = ?", "Test");
		CompilationChallenge.addCompilationChallenge(u.getInteger("id"), "Test1", "Test1", "description",
		"source", 100, 0);
		Challenge c1 = Challenge.findFirst("title = ?", "Test1");
		CompilationChallenge.addCompilationChallenge(u.getInteger("id"), "Test2", "Test2", "description",
		"source", 100, 0);
		Challenge c2 = Challenge.findFirst("title = ?", "Test2");
		CompilationChallenge.addCompilationChallenge(u.getInteger("id"), "Test3", "Test3", "description",
		"source", 100, 0);

		Proposition.newProposition(u.getInteger("id"), c.getInteger("id"));
		Proposition p1 = Proposition.newProposition(u.getInteger("id"), c1.getInteger("id"));
		p1.set("isSolution", 1);
		p1.saveIt();
		Proposition p2 = Proposition.newProposition(u.getInteger("id"), c2.getInteger("id"));
		p2.set("isSolution", 1);
		p2.saveIt();
		Proposition p3 = Proposition.newProposition(u1.getInteger("id"), c2.getInteger("id"));
		p3.set("isSolution", 1);
		p3.saveIt();
	
	}

	@AfterClass
	public static void after(){
		if (Base.hasConnection()) {
			Base.rollbackTransaction();
			log.info("Compilation ChallengeTest tearDown");
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
		int userId = 1; 
		String title = "Hello Word";
		String description = "---";
		String className = "HelloWordx2";
		String source = "public String hello(){ return "+"HelloWord"+" }";
		int point= 100;
		int ownerSolutionId= 9;
		Challenge challenge = Challenge.addChallenge(userId, title, className, description,
		source, point, ownerSolutionId);
		boolean validate = CompilationChallenge.validateCompilationChallenge(challenge);
		assertEquals(true ,validate);
	}

	/**
	 * Test method for addCompilationChallenge.
	 */
	@Test
	public void addCompilationChallengeTest() {
		int userId = 32; 
		String title= "Hello Word";
		String nameClass = "HelloWord3";
		String description = "Test Hellos Word";
		String source = "System.out.println('Hello Word')";
		int point = 52;
		int ownerSolutionId = 3;
		boolean validate = CompilationChallenge.addCompilationChallenge(userId,title,
		nameClass,description,source,point,ownerSolutionId);
		assertEquals(true,validate);
	}

	/**
	 * Test method for viewAllCompilationChallange.
	 */
	@Test
	public void viewAllCompilationChallangeTest() {
		List<Map<String,Object>> all = CompilationChallenge.viewAllCompilationChallange();
		assertEquals(4, all.size());
		assertEquals("Test", all.get(0).get("title"));
		assertEquals("Test1", all.get(1).get("title"));
		assertEquals("Test2", all.get(2).get("title"));
		assertEquals("Test3", all.get(3).get("title"));
	}

	/**
	 * Test method for viewResolvedCompilationChallange.
	 */
	@Test
	public void viewResolvedCompilationChallangeTest() {
		List<Map<String,Object>> resolved = CompilationChallenge.viewResolvedCompilationChallange();
		assertEquals(2, resolved.size());
		assertEquals("Test1", resolved.get(0).get("title"));
		assertEquals("Test2", resolved.get(1).get("title"));
	}

	/**
	 * Test method for viewUnsolvedCompilationChallange.
	 */
	@Test
	public void viewUnsolvedCompilationChallangeTest() {
		List<Map<String,Object>> unsolved = CompilationChallenge.viewUnsolvedCompilationChallange();
		assertEquals(2, unsolved.size());
		assertEquals("Test", unsolved.get(0).get("title"));
		assertEquals("Test3", unsolved.get(1).get("title"));
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
		String source = "public class " + className + " {\n";
			   source+= " ///";
			   source+= "}\n";
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

	/**
	 * Test method for deleteChallenge.
	 * In case of the CompilationChallenge already exist.
	 */
	@Test
	public void deleteCompilationChallengeTest() {
		int userId = 5; 
		String title= "Hello Word";
		String className = "HelloWord4";
		String description = "Test Hellos Word";
		String source = "System.out.println('Hello Word')";
		int point = 300;
		int ownerSolutionId = 10;
		CompilationChallenge.addCompilationChallenge(userId, title, className, description,
		source, point, ownerSolutionId);
		int id = Challenge.findFirst("class_name = ?", className).getInteger("id");
		Challenge.deleteChallenge(id);
		assertNull(CompilationChallenge.findFirst("challenge_id = ?", id));   
	}

} 
