package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to test the Challenge class methods.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public class ChallengeTest {

	private static final Logger log = LoggerFactory.getLogger(ChallengeTest.class);

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			log.info("ChallengeTest setup");
			Base.openTransaction();
		}
		
		User u = User.set("test", "1234", "test@example.com", true);

		Challenge.addChallenge(u.getInteger("id"), "Test", "Test", "description",
		"source", 100, 0);
		Challenge.addChallenge(u.getInteger("id"), "Test1", "Test1", "description",
		"source", 100, 0);
		Challenge.addChallenge(u.getInteger("id"), "Test2", "Test2", "description",
		"source", 100, 0);
		Challenge.addChallenge(u.getInteger("id"),"Test3", "Test3", "description",
		"source", 100, 0);
		Challenge c = Challenge.findFirst("title = ?", "Test");

		Proposition p = Proposition.newProposition(u.getInteger("id"), c.getInteger("id"));
		p.set("isSolution", 1);
		p.saveIt();
	}


	@AfterClass
	public static void after(){
		if (Base.hasConnection()) {
			Base.rollbackTransaction();
			log.info("ChallengeTest tearDown");
			Base.close();
		}  
	}

	/**
	 * Test methods for set and get.
	 */
	@Test
	public void setAndGetTest() {
		Challenge challenge = new Challenge();
		challenge.setUserId(2);
		challenge.setTitle("Hello Word");
		challenge.setClassName("HelloWord");
		challenge.setDescription("Test Hello Word");
		challenge.setSource("System.out.println('Hello Word')");
		challenge.setPoint(100);
		challenge.setOwnerSolutionId(6);
		challenge.saveIt();
		assertEquals(2,challenge.getUserId());
		assertEquals("Hello Word",challenge.getTitle());
		assertEquals("HelloWord", challenge.getClassName());
		assertEquals("Test Hello Word",challenge.getDescription());
		assertEquals("System.out.println('Hello Word')",challenge.getSource());
		assertEquals(100,challenge.getPoint());
		assertEquals(6,challenge.getOwnerSolutionId());
	}

	/**
	 * Test method for addChallenge.
	 */
	@Test
	public void addChallengeTest() {
		int userId = 1; 
		String title= "Hello Word";
		String nameClass = "HelloWord1";
		String description = "Test Hellos Word";
		String source = "System.out.println('Hello Word')";
		int point = 100;
		int ownerSolutionId = 9;
		Challenge challenge = new Challenge();
		challenge = Challenge.addChallenge(userId,nameClass,title,description,
		source,point,ownerSolutionId);
		assertEquals(1,challenge.getUserId());
	}

	/**
	 * Test method for deleteChallenge.
	 * In case of the challenge already exist.
	 */
	@Test
	public void deleteChallengeTest() {
		int userId = 5; 
		String title= "Hello Word";
		String nameClass = "HelloWord4";
		String description = "Test Hellos Word";
		String source = "System.out.println('Hello Word')";
		int point = 300;
		int ownerSolutionId = 10;
		Challenge challenge = new Challenge();
		challenge = Challenge.addChallenge(userId,title,nameClass,description,
		source,point,ownerSolutionId);
		Challenge.deleteChallenge(challenge.getInteger("id"));
		assertNull(Challenge.findFirst("title = ?",title));   
	}

	/**
	 * Test method for deleteChallenge.
	 * In case of the challenge not exist.
	 */
	@Test
	public void deleteChallengeTest1() {
		try {
			int id = 5;
			Challenge.deleteChallenge(id);
			fail();
		} catch (IllegalArgumentException ex) {
			assertEquals(Challenge.CHALLENGE_NOT_EXIST, ex.getMessage());
		}
		
	}

	/**
	 * Test method for viewUserAssociatedChallange.
	 */
	@Test
	public void viewUserAssociatedChallangeTest() {
		User username  = User.findFirst("username = ?", "test");
		int id = username.getInteger("id");
		List<Challenge> all = Challenge.viewUserAssociatedChallange(id);
		assertEquals(4, all.size());
		assertEquals("Test", all.get(0).getString("title"));
		assertEquals("Test1", all.get(1).getString("title"));
		assertEquals("Test2", all.get(2).getString("title"));
		assertEquals("Test3", all.get(3).getString("title"));
	}

	/**
	 * Test method for generateFileJava.
	 */
	@Test
	public void generateFileJavaTest() {
		String name = "TestGenerateFile";
		String source = "public class " + name + " {\n";
			   source = "    public static void main(String[] args) {\n";
			   source+= "        System.out.println("+"\"TestGenerateFile\""+");\n";
			   source+= "    }\n";
			   source+= "}\n";
		boolean obtained = Challenge.generateFileJava(name, source);
		assertTrue(obtained);
	}

	/**
	 * Test method for runCompilation.
	 */
	@Test
	public void runCompilationTest() {
		String nameFile = "TestCompilation";
		String source = "public class " + nameFile + " {\n";
			   source += "   public static void main(String[] args) {\n";
			   source += "    	System.out.println("+"\"Test Compilation\""+");\n";
			   source += "    }\n";
			   source += "}\n";
		Challenge.generateFileJava(nameFile, source);
		boolean obtained = Challenge.runCompilation(nameFile);
		assertEquals(true, obtained);
	}

	/**
	 * Test method for runJava.
	 */
	@Test
	public void runJavaTest() {
		String nameFile = "TestRunJava";
		String source = "package src.main;\n";
			  source += "public class " + nameFile + " {\n";
			  source += "    public static void main(String[] args) {\n";
			  source += "        System.out.println("+"\"Test RunJava\""+");\n";
			  source += "    }\n";
			  source += "}\n";
		Challenge.generateFileJava(nameFile, source);
		Challenge.runCompilation(nameFile);
		boolean obtained = Challenge.runJava(nameFile);
		assertEquals(true, obtained);
	}

	/**
	 * Test method for checkUnsolvedChallenge.
	 */
	@Test
	public void checkUnsolvedChallengeTest() {
		Challenge c = Challenge.findFirst("title = ?", "Test");
		try{
			Challenge.checkUnsolvedChallenge(c.getInteger("id"));
			fail();
		} catch (RuntimeException ex) {
			assertEquals(Challenge.CHALLENGE_RESOLVED, ex.getMessage());
		}
	}

	/**
	 * Test method for validatePresenceChallenge.
	 */
	@Test
	public void validatePresenceChallengeTest() {
		Challenge c = null;
		try{
			Challenge.validatePresenceChallenge(c);
			fail();
		} catch (IllegalArgumentException ex) {
			assertEquals(Challenge.CHALLENGE_NOT_EXIST, ex.getMessage());
		}
	}

}
