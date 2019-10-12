package unrc.dose;

import java.util.LinkedList;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;

/**
 *  table attributes.
 *  id integer not null auto_increment primary key.
 *  challenge_id integer not null.
 *  test varchar(1000) not null.
 */
public class TestChallenge extends Model {

    /**
     * the class constructor.
     */
    public TestChallenge() { }

    /**
     * method that returns the id of a challenge.
     * @return challenge id.
     */
    public int getChallengeId() {
        return getInteger("challenge_id");
    }

    /**
     * method to modify the challenge id.
     * @param challengeId challenge id that created it.
     */
    public void setChallengeId(final int challengeId) {
        set("challenge_id", challengeId);
    }

    /**
     * method that returns the test of a challenge.
     * @return test of a challenge.
     */
    public String getTest() {
        return getString("test");
    }

    /**
     * method to modify the challenge test.
     * @param test test that will have the challenge.
     */
    public void setTest(final String test) {
        set("test", test);
    }

    /**
     * This method is responsible for validating the test challenge.
     * @param c challenge to validate.
     * @param t test challenge to validate
     * @return True in case the validation passes (the source compiles and
     * the tests run), otherwise, false.
     */
    public static boolean validateTestChallenge(
        final Challenge c,
        final TestChallenge t) {
        return true;
    }

    /**
     * This method allows you to create a test challenge.
     * @param challengeId challenge id.
     * @param test test code.
     * @return test challenge already created.
     */
    public static TestChallenge addTestChallenge(
        final int challengeId,
        final String test) {
        TestChallenge t = new TestChallenge();
        t.setChallengeId(challengeId);
        t.setTest(test);
        t.saveIt();
        return t;
    }

    /**
     * method that returns the test challenge according to
     * the id of the challenge.
     * @param idChallenge challenge id.
     * @return TestChallenge.
     */
    public static TestChallenge getChallenge(final int idChallenge) {
        return TestChallenge.findFirst("challenge_id", idChallenge);
    }

    /**
     * method that returns a list of all test challenges.
     * @return list of all test challange.
     */
    public static List<Challenge> viewAllTestChallange() {
        LazyList<TestChallenge> testChall = TestChallenge.findAll();
        LinkedList<Challenge> allChallenge = new LinkedList<Challenge>();
        if (!testChall.isEmpty()) {
            for (TestChallenge tChallenge : testChall) {
                Challenge res = Challenge.findFirst(
                    "id = ?",
                    tChallenge.get("challenge_id"));
                allChallenge.add(res);
            }
        }
        return allChallenge;
    }


    /**
     * method that returns a list of resolved test challenges.
     * @return list of test challanges resolved.
     */
    public static List<Challenge> viewSolvedTestChallange() {
        LazyList<OwnerSolution> ownerSol = OwnerSolution.findAll();
        LinkedList<Challenge> resolved = new LinkedList<Challenge>();
        if (!ownerSol.isEmpty()) {
            for (OwnerSolution challengeResolved : ownerSol) {
                if (TestChallenge.exists(
                    challengeResolved.get("challenge_id"))) {
                    Challenge res = Challenge.findFirst(
                        "id = ?",
                        challengeResolved.get("challenge_id"));
                    resolved.add(res);
                }
            }
        }
        return resolved;
    }

    /**
     * method that returns a list of unresolved test challenges.
     * @return list of test challanges unresolved.
     */
    public static List<Challenge> viewUnsolvedTestChallange() {
        List<Challenge> resolved = viewSolvedTestChallange();
        List<Challenge> unresolved = new LinkedList<Challenge>();
        if (!resolved.isEmpty()) {
            for (Challenge res : resolved) {
                if (!(TestChallenge.exists(res.get("challenge_id")))) {
                    unresolved.add(res);
                }
            }
        }
        return unresolved;
    }
}
