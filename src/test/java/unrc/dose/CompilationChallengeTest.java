package unrc.dose;

import unrc.dose.Challenge;
import unrc.dose.CompilationChallenge;
import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CompilationChallengeTest {

	@BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			System.out.println("CompilationChallengeTest setup");
			Base.openTransaction();
		}
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
	 * Test the method set and get.
	 */
	@Test
	public void setAndGetTest() {
		CompilationChallenge compChallenge = new CompilationChallenge();
		compChallenge.setChallengeId(31);
		compChallenge.saveIt();
		assertEquals(31,compChallenge.getChallengeId());
	}

	/**
	 * Test the method validateCompilationChallenge.
	 */
	@Test
	public void validateCompilationChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.setTitle("Not found");
		challenge.setClassName("NotFound");
		challenge.setSource("Not found");
		challenge.saveIt();
		boolean validate = CompilationChallenge.validateCompilationChallenge(challenge);
		assertEquals(true ,validate);
	}

	/**
	 * Test the method addCompilationChallenge.
	 */
	@Test
	public void addCompilationChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.saveIt();
		int challengeId = challenge.getInteger("id");
		CompilationChallenge compChallenge = CompilationChallenge.addCompilationChallenge(challengeId);
		assertEquals(challengeId,compChallenge.getChallengeId());
	}

} 
