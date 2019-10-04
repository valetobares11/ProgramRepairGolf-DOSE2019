package unrc.dose;

import unrc.dose.TestChallenge;
import unrc.dose.Challenge;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestChallengeTest {

   @Before
  public void before(){
      if (!Base.hasConnection()) {
        Base.open();
        System.out.println("TestChallengeTest setup");
        Base.openTransaction();
      }
  }

  @After
  public void after(){
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

}
