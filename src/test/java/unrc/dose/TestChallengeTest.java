package unrc.dose;

import unrc.dose.TestChallenge;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestChallengeTest {

  @Before
  public void before(){
      Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/repair_game_test", "root", "root");
      System.out.println("TestChallengeTest setup");
      Base.openTransaction();
  }

  @After
  public void after(){
      System.out.println("TestChallengeTest tearDown");
      Base.rollbackTransaction();
      Base.close();
  }

  @Test
  public void validatePresenceOfParams() {
    TestChallenge tc = new TestChallenge();
    
  }
}
