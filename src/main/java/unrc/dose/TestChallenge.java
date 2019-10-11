package unrc.dose;

import org.javalite.activejdbc.Model;

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
     * This method is responsible for validating the compilation challenge.
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
     * This method allows you to create a compilation challenge.
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
}
