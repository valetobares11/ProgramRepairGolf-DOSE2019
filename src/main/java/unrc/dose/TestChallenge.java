package unrc.dose;

import java.util.LinkedList;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;

/**
 * Table test_challenges - Attributes.
 * id integer not null auto_increment primary key.
 * challenge_id integer not null.
 * test varchar(1000) not null.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
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
     * method that returns a list of all test challenges.
     * @return list of all test challange.
     */
    public static List<Tuple<Challenge, TestChallenge>>
        viewAllTestChallange() {
        LazyList<TestChallenge> all = TestChallenge.findAll();
        LinkedList<Tuple<Challenge, TestChallenge>> allChallenges
        = new LinkedList<Tuple<Challenge, TestChallenge>>();
        if (!all.isEmpty()) {
            for (TestChallenge currentChallenge : all) {
                Challenge c = Challenge.findFirst(
                    "id = ?",
                    currentChallenge.get("challenge_id"));
                Tuple<Challenge, TestChallenge> t =
                new Tuple<Challenge, TestChallenge>(c,
                currentChallenge);
                allChallenges.add(t);
            }
        }
        return allChallenges;
    }


    /**
     * method that returns a list of resolved test challenges.
     * @return list of test challanges resolved.
     */
    public static List<Tuple<Challenge, TestChallenge>>
        viewSolvedTestChallange() {
        LazyList<OwnerSolution> allOwnerSol = OwnerSolution.findAll();
        LinkedList<Tuple<Challenge, TestChallenge>> resolved
        = new LinkedList<Tuple<Challenge, TestChallenge>>();
        if (!allOwnerSol.isEmpty()) {
            for (OwnerSolution challengeResolved : allOwnerSol) {
                if (TestChallenge.exists(
                    challengeResolved.get("challenge_id"))) {
                    Challenge c = Challenge.findFirst(
                        "id = ?",
                        challengeResolved.get("challenge_id"));
                    TestChallenge tc = TestChallenge.findFirst(
                        "challenge_id = ?",
                        challengeResolved.get("challenge_id"));
                    Tuple<Challenge, TestChallenge> t =
                    new Tuple<Challenge, TestChallenge>(c,
                    tc);
                    resolved.add(t);
                }
            }
        }
        return resolved;
    }

    /**
     * method that returns a list of unresolved test challenges.
     * @return list of test challanges unresolved.
     */
    public static List<Tuple<Challenge, TestChallenge>>
        viewUnsolvedTestChallange() {
        LazyList<OwnerSolution> allOwnerSol = OwnerSolution.findAll();
        LinkedList<Tuple<Challenge, TestChallenge>> unsolved
        = new LinkedList<Tuple<Challenge, TestChallenge>>();
        if (!allOwnerSol.isEmpty()) {
            for (OwnerSolution challengeResolved : allOwnerSol) {
                if (!(TestChallenge.exists(
                    challengeResolved.get("challenge_id")))) {
                    Challenge c = Challenge.findFirst(
                        "id = ?",
                        challengeResolved.get("challenge_id"));
                    TestChallenge tc = TestChallenge.findFirst(
                        "challenge_id = ?",
                        challengeResolved.get("challenge_id"));
                    Tuple<Challenge, TestChallenge> t =
                    new Tuple<Challenge, TestChallenge>(c,
                    tc);
                    unsolved.add(t);
                }
            }
        }
        return unsolved;
    }
}
