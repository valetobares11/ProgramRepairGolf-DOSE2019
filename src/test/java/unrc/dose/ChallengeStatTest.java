package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class to test the ChallengeStat class methods.
 * @author Fernandez, Camilo
 * @author Manzetti, Mariano
 */
public class ChallengeStatTest {

    @BeforeClass
	public static void before(){
        if (!Base.hasConnection()) {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/repair_game_test", "root", "root");
            Base.openTransaction();
        }
    }

    @AfterClass
	public static void after(){
        if (Base.hasConnection()) {
            Base.rollbackTransaction();
            Base.close();
        }
	}

    /**
     * Test for newChallengeStat() method.
    */
    @Test
    public void newChallengeStatTest() {
        ChallengeStat.delete(1);
        ChallengeStat.newChallengeStat(1);
        Base.commitTransaction();
        ChallengeStat c = ChallengeStat.findFirst("challenge_id = ?", 1);

        assertEquals(1, c.get("challenge_id"));
        assertEquals((float) 0.0, c.get("average_score"));
        assertEquals(0, c.get("solved_count"));
    }

    /**
    * Test for updateAverageScore() method.
    */
    @Test
    public void updateAverageScoreTest() {

        User testUser = User.set("testName", "testPass", "testMail", false);
        int userId = testUser.getInteger("id");
        testUser.saveIt();

        Challenge testChallenge = Challenge.addChallenge(userId, "testChallenge",
        "testClass", "testDesc", "testSrc", 20, 1);
        int challengeId = testChallenge.getInteger("id");
        testChallenge.saveIt();

        Proposition p = Proposition.newProposition(userId, challengeId);
        p.setDistance(5);
        p.saveIt();

        ChallengeStat cs = (ChallengeStat) ChallengeStat.newChallengeStat(challengeId);
        Float zero = Float.parseFloat("0");
        assertEquals(zero, cs.getFloat("average_score"));
        assertEquals((int) 0, cs.get("solved_count"));

        ChallengeStat.updateAverageScore(p.getInteger("id"));

        ChallengeStat cs1 = ChallengeStat.findFirst("challenge_id = ?", challengeId);

        assertEquals((float) 15.0, cs1.get("average_score"));
        assertEquals(1, cs1.get("solved_count"));
        Challenge.deleteChallenge(testChallenge);

    }

    /**
     * Test for delete() method.
     */
    @Test
    public void deleteChallengeStatTest() {

        ChallengeStat c = ChallengeStat.newChallengeStat(1);

        assertNotNull(c);

        ChallengeStat.delete(1);

        c = ChallengeStat.getChallengeStat(1);

        assertNull(c);
    }

    /**
     * Test for getChallengeStat() method.
     */
    @Test
    public void getChallengeStatTest() {

        ChallengeStat c = ChallengeStat.findFirst("challenge_id = ?", 1);
        ChallengeStat result = ChallengeStat.getChallengeStat(1);

        boolean comparison = result.equals(c);

        assertNotNull(c);
        assertTrue(comparison);
        ChallengeStat.delete(1);

    }
}
