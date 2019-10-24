package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChallengeStatTest {

    @BeforeClass
	public static void before(){
        if (!Base.hasConnection()) {
            Base.open();
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
    * Creates a new ChallengeStat record on the data base.
    * @result ChallengeStat c=("challenge_id"= 1, "average_score"= 0.0, "solved_count"= 0) record sucessfully created.
    */
    @Test
    public void newChallengeStatTest() {

        ChallengeStat.newChallengeStat(1);
        Base.commitTransaction();
        ChallengeStat c = ChallengeStat.findFirst("challenge_id = ?", 1);

        assertEquals(1, c.get("challenge_id"));
        assertEquals((float) 0.0, c.get("average_score"));
        assertEquals(0, c.get("solved_count"));
        
    }

    /**
    * Updates the attribute "average_score" of a ChallengeStat record, and increments the "solved_count".
    * @result "average_score" = 6.0, "solved_count" = 2
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

        ChallengeStat cs2 = ChallengeStat.findFirst("challenge_id = ?", challengeId);

        assertEquals((float) 15.0, cs2.get("average_score"));
        assertEquals(1, cs2.get("solved_count"));
    }

    /**
     * 
     */
    @Test
    public void getChallengeStatTest() {
        ChallengeStat.newChallengeStat(1);
        Base.commitTransaction();

        ChallengeStat c = ChallengeStat.findFirst("challenge_id = ?", 1);
        ChallengeStat result = ChallengeStat.getChallengeStat(1);

        boolean comparison = result.equals(c);
        assertTrue(comparison);
    }
}
