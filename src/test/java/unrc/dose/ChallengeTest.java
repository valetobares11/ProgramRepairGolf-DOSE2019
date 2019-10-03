package unrc.dose;

import unrc.dose.Challenge;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ChallengeTest {

  @Before
  public void before(){
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/repair_game_test", "root", "root");
      System.out.println("ChallengeTest setup");
      Base.openTransaction();
  }

  @After
  public void after(){
      System.out.println("ChallengeTest tearDown");
      Base.rollbackTransaction();
      Base.close();
  }

  @Test
  public void setAndGetTest() {
    Challenge challenge = new Challenge();
    challenge.setUserId(2);
    challenge.setTitle("Hello Word");
    challenge.setDescription("Test Hello Word");
    challenge.setSource("System.out.println('Hello Word')");
    challenge.setPoint(100);
    challenge.setOwnerSolutionId(6);

    assertEquals(2,challenge.getUserId());
    assertEquals("Hello Word",challenge.getTitle());
    assertEquals("Test Hello Word",challenge.getDescription());
    assertEquals("System.out.println('Hello Word')",challenge.getSource());
    assertEquals(100,challenge.getPoint());
    assertEquals(6,challenge.getOwnerSolutionId());
  }

  @Test
  public void addChallengeTest() {
    Challenge challenge = new Challenge();
    challenge = Challenge.addChallenge(5,"Hello Word","Test Hello Word","System.out.println('Hello Word')",300,10);

    assertEquals(5,challenge.getUserId());

  }

  /*
  @Test
  public void deleteChallengeTest() {
    Challenge challenge = new Challenge();

    challenge = Challenge.addChallenge(5,"Hello Word","Test Hellos Word","System.out.println('Hello Word')",300,8,10);

    Challenge.deleteChallenge(challenge);

    assertEquals (null,Challenge.where("user_id = ?",5));   
  } 
  */   
}
