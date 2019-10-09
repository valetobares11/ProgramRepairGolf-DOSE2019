package unrc.dose;

import unrc.dose.Challenge;
import unrc.dose.CompilationChallenge;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CompilationChallengeTest {

	@Before
	public void before(){
		if (!Base.hasConnection()) {
			Base.open();
			System.out.println("CompilationChallengeTest setup");
			Base.openTransaction();
		}
	}

	@After
	public void after(){
		if (Base.hasConnection()) {
			Base.rollbackTransaction();
			System.out.println("Compilation ChallengeTest tearDown");
			Base.close();
		}  
	}

	/**
	 * Test the method set and get
	 * from CompilationChallenge class 
	 */
	@Test
	public void setAndGetTest() {
		CompilationChallenge compChallenge = new CompilationChallenge();
		compChallenge.setChallengeId(31);
		compChallenge.saveIt();
		assertEquals(31,compChallenge.getChallengeId());
	}

	/**
	 * Test the method validateCompilationChallenge
	 * from CompilationChallenge class 
	 */
	@Test
	public void validateCompilationChallengeTest() {
		Challenge challenge = new Challenge();
		challenge.setTitle("Not found");
		challenge.setSource("Not found");
		challenge.saveIt();
		boolean validate = CompilationChallenge.validateCompilationChallenge(challenge);
		assertEquals(true ,validate);
	}

	/**
	 * Test the method addCompilationChallenge
	 * from CompilationChallenge class 
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
