package unrc.dose;

import unrc.dose.Challenge;
import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChallengeTest {

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			System.out.println("ChallengeTest setup");
			Base.openTransaction();
		}
	}

	@AfterClass
	public static void after(){
		if (Base.hasConnection()) {
			Base.rollbackTransaction();
			System.out.println("ChallengeTest tearDown");
			Base.close();
		}  
	}

	/**
	 * Test the methods set and get.
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
	 * Test the method addChallenge.
	 */
	@Test
	public void addChallengeTest() {
		Challenge challenge = new Challenge();
		int userId = 1; 
		String title= "Hello Word";
		String nameClass = "HelloWord1";
		String description = "Test Hellos Word";
		String source = "System.out.println('Hello Word')";
		int point = 100;
		int ownerSolutionId = 9;
		challenge = Challenge.addChallenge(userId,nameClass,title,description,source,point,ownerSolutionId);
		challenge.saveIt();
		assertEquals(1,challenge.getUserId());
	}

	/**
	 * Test the method addTestChallenge.
	 */
	@Test
	public void addTestChallengeTest() {
		int userId = 32; 
		String title= "Hello Word";
		String nameClass = "HelloWord2";
		String description = "Test Hellos Word";
		String source = "System.out.println('Hello Word')";
		int point = 52;
		int ownerSolutionId = 3;
		String test = "this is a challenge test";
		boolean validation = Challenge.addTestChallenge(userId,title,nameClass,description,source,point,ownerSolutionId,test);
		assertEquals(true,validation);
	}

	/**
	 * Test the method addCompilationChallenge.
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
		boolean validate = Challenge.addCompilationChallenge(userId,title,nameClass,description,source,point,ownerSolutionId);
		assertEquals(true,validate);
	}

	/**
	 * Test the method deleteChallenge.
	 */
	@Test
	public void deleteChallengeTest() {
		Challenge challenge = new Challenge();
		int userId = 5; 
		String title= "Hello Word";
		String nameClass = "HelloWord4";
		String description = "Test Hellos Word";
		String source = "System.out.println('Hello Word')";
		int point = 300;
		int ownerSolutionId = 10;
		challenge = Challenge.addChallenge(userId,title,nameClass,description,source,point,ownerSolutionId);
		challenge.saveIt();
		Challenge.deleteChallenge(challenge);
		assertEquals(null,Challenge.findFirst("title = ?",title));   
	} 

	/**
	 * Test the method generateFileJava.
	 */
	@Test
	public void generateFileJavaTest() {
		String name = "Test";
		String source = "    public static void main(String[] args) {\n";
			   source+= "        System.out.println("+"\"Hello Word\""+");\n";
			   source+= "    }\n";
		boolean obtained = Challenge.generateFileJava(name, source);
		assertTrue(obtained);
	} 

	/**
	 * Test the method runCompilation.
	 */
	@Test
	public void runCompilationTest() {
		String nameFile = "Test";
		boolean obtained = Challenge.runCompilation(nameFile);
		assertEquals(true, obtained);
	}

	/**
	 * Test the method runJava.
	 */
	@Test
	public void runJavaTest() {
		String nameFile = "Test";
		boolean obtained = Challenge.runJava(nameFile);
		assertEquals(true, obtained);
	}

}
