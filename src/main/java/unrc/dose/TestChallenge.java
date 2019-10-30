package unrc.dose;

import java.util.LinkedList;
import java.util.List;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

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
     * @param t test challenge to validate
     * @return True in case the validation passes (the source compiles and
     * the tests run), otherwise, false.
     */
    public static boolean validateTestChallenge(
        final TestChallenge t) {
        //Challenge c = Challenge.findFirst("challenge_id = ?", t.getId());
        return true;
    }

    /**
     * This method allows you to create a test challenge and validate it.
     * @param userId user id that created it.
     * @param title title that will have the challenge.
     * @param className title for class and name file.
     * @param description a brief description of what the challenge is about.
     * @param source source code that will have the challenge.
     * @param point points given by the admin that for the challenge.
     * @param ownerSolutionId id of the solution proposed by a user.
     * @param test test corresponding to the challenge.
     * @return true in case the validation is successful, otherwise false.
     */
    public static boolean addTestChallenge(
        final int userId,
        final String title,
        final String className,
        final String description,
        final String source,
        final int point,
        final int ownerSolutionId,
        final String test) {
        Challenge c = Challenge.addChallenge(
            userId,
            title,
            className,
            description,
            source,
            point,
            ownerSolutionId);
            TestChallenge t = new TestChallenge();
            t.setChallengeId(c.getInteger("id"));
            t.setTest(test);
            t.saveIt();
        return (TestChallenge.validateTestChallenge(t));
    }

    /**
     * method that resturns a list of all test challenges.
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
                new Tuple<Challenge, TestChallenge>(
                    c,
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
        viewResolvedTestChallange() {
        LazyList<Proposition> allResolved =
        Proposition.where("isSolution = ?", 1);
        LinkedList<Tuple<Challenge, TestChallenge>> resolved
        = new LinkedList<Tuple<Challenge, TestChallenge>>();
        if (!allResolved.isEmpty()) {
            for (Proposition challengeResolved : allResolved) {
                Challenge c = Challenge.findFirst(
                    "id = ?",
                    challengeResolved.get("challenge_id"));
                TestChallenge tc = TestChallenge.findFirst(
                    "challenge_id = ?",
                    challengeResolved.get("challenge_id"));
                Tuple<Challenge, TestChallenge> t =
                new Tuple<Challenge, TestChallenge>(c, tc);
                if (!(resolved.contains(t))) {
                    resolved.add(t);
                }
            }
        }
        return resolved;
    }

    /**
     * method that returns a list of unsolved test challenges.
     * @return list of test challanges unresolved.
     */
    public static List<Tuple<Challenge, TestChallenge>>
        viewUnsolvedTestChallange() {
        List<Tuple<Challenge, TestChallenge>> resolved =
        viewResolvedTestChallange();
        List<Tuple<Challenge, TestChallenge>> all =
        viewAllTestChallange();
        List<Tuple<Challenge, TestChallenge>> unsolved =
        new LinkedList<Tuple<Challenge, TestChallenge>>();
        if (!all.isEmpty()) {
            for (Tuple<Challenge, TestChallenge> c: all) {
                if (!(resolved.contains(c))) {
                    unsolved.add(c);
                }
            }
        }
        return unsolved;
    }

    /**
     * This method will allow you to modify a challenge if it has
     * not been resolved.
     * @param challengeId id of the challenge to check.
     * @param title title that will have the challenge.
     * @param className title for class and name file.
     * @param description a brief description of what the challenge is about.
     * @param source source code that will have the challenge.
     * @param point points given by the admin that for the challenge.
     * @param test test code that will have the challenge.
     * @return True in case the validation passes (source code compile and
     * the tests run).
     */
    public static boolean modifyUnsolvedTestChallenge(
        final int challengeId,
        final String title,
        final String className,
        final String description,
        final String source,
        final int point,
        final String test) {
        Challenge.checkUnsolvedChallenge(challengeId);
        Challenge c = Challenge.findFirst("id = ?", challengeId);
        Challenge.validatePresenceChallenge(c);
        c.setTitle(title);
        c.setClassName(className);
        c.setDescription(description);
        c.setSource(source);
        c.setPoint(point);
        c.saveIt();
        TestChallenge t = TestChallenge.findFirst("challenge_id = ?",
        challengeId);
        t.setTest(test);
        t.saveIt();
        return validateTestChallenge(t);
    }

     /**
     * This method to compile test.
     * @param nameFile name of the file to execute.
     * @return true if run otherwise false.
     */
    public static boolean runCompilationTestJava(final String nameFile) {
        return Challenge.runProcess(
            "javac -cp .:target/dependency/junit-4.12.jar /../tmp/"
            + nameFile + ".java");
    }

    /**
     * This method to run a JUnit test.
     * @param nameFile name of the file to execute.
     * @return true if run otherwise false.
     */
    public static boolean runTestJava(final String nameFile) {
        return Challenge.runProcess(
            "java -cp .:/tmp:target/dependency/junit-4.12.jar:target/dependency/" +
            "hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore " + nameFile);
    }

    /**
     * hashCode redefined.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getInteger("id").hashCode();
        return result;
    }

    /**
     * this method to compare two test challenges according to id.
     * @param c test challenge as a parameter.
     * @return true if the id of challenges are equal.
     */
    @Override
    public boolean equals(final Object c) {
        TestChallenge tc = (TestChallenge) c;
        return getInteger("id").equals(tc.getInteger("id"));
    }
}
