package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
            Base.open();
            Base.openTransaction();
        }

        User testUser = User.set("testName", "testPass", "testMail", false);
        int userId = testUser.getInteger("id");

        Challenge testChallenge = Challenge.addChallenge(userId, "testChallenge",
        "testClass", "testDesc", "testSrc", 20, 1);
        int challengeId = testChallenge.getInteger("id");

        Proposition.newProposition(userId, challengeId, "testSrc", true, 5, 0);

        ChallengeStat.newChallengeStat(challengeId);

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

        ChallengeStat.newChallengeStat(15);
        ChallengeStat c = ChallengeStat.findFirst("challenge_id = ?", 15);

        assertNotNull(c);
        assertEquals(15, c.get("challenge_id"));
        assertEquals((float) 0.0, c.get("average_score"));
        assertEquals(0, c.get("solved_count"));
    }

    /**
    * Test for updateAverageScore() method.
    */
    @Test
    public void updateAverageScoreTest() {

        List<Challenge> challengeList = Challenge.where("title = 'testChallenge'");
        Challenge testChallenge = challengeList.get(0);
        int challengeId = testChallenge.getInteger("id");
        ChallengeStat cs = ChallengeStat.getChallengeStat(challengeId);
        Proposition p = Proposition.findFirst("challenge_id = ?", challengeId);

        assertEquals((float) 0, cs.get("average_score"));
        assertEquals((int) 0, cs.get("solved_count"));

        ChallengeStat.updateAverageScore(p.getInteger("id"));

        ChallengeStat cs1 = ChallengeStat.findFirst("challenge_id = ?", challengeId);

        assertEquals((float) 15.0, cs1.get("average_score"));
        assertEquals(1, cs1.get("solved_count"));

    }

    /**
     * Test for delete() method.
     */
    @Test
    public void deleteChallengeStatTest() {

        ChallengeStat c = ChallengeStat.newChallengeStat(16);

        assertNotNull(c);

        ChallengeStat.delete(16);

        c = ChallengeStat.getChallengeStat(16);

        assertNull(c);
    }

    /**
     * Test for getChallengeStat() method.
     */
    @Test
    public void getChallengeStatTest() {

        List<Challenge> challengeList = Challenge.where("title = 'testChallenge'");
        Challenge testChallenge = challengeList.get(0);
        int challengeId = testChallenge.getInteger("id");

        ChallengeStat c = ChallengeStat.findFirst("challenge_id = ?", challengeId);
        ChallengeStat result = ChallengeStat.getChallengeStat(challengeId);

        boolean comparison = result.equals(c);

        assertNotNull(result);
        assertTrue(comparison);

    }
}
