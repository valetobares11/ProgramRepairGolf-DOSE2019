package unrc.dose;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
     * message that will throw the exception if the challenge
     * to delete does not exist.
     */
    public static final String CHALLENGE_NOT_EXIST =
    "The test challenge is not exist";

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
        Challenge c = Challenge.findFirst("id = ?", t.getChallengeId());
        String className = c.getClassName();
        String source = c.getSource();
        String test = t.getTest();
        String classNameTest = className + "Test";
        Challenge.generateFileJava(className, source);
        Challenge.generateFileJavaTest(classNameTest, test);
        return Challenge.runCompilation(className)
               && TestChallenge.runCompilationTestJava(classNameTest)
               && TestChallenge.runTestJava(classNameTest);
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
    public static List<Map<String, String>> viewAllTestChallange() {
        LazyList<TestChallenge> all = TestChallenge.findAll();
        LinkedList<Map<String, String>> allChallenges
        = new LinkedList<Map<String, String>>();
        if (!all.isEmpty()) {
            for (TestChallenge currentChallenge : all) {
                Challenge c = Challenge.findFirst(
                    "id = ?",
                    currentChallenge.get("challenge_id"));
                Map<String, String> t = toTestChallege(c, currentChallenge);
                allChallenges.add(t);
            }
        }
        return allChallenges;
    }


    /**
     * method that returns a list of resolved test challenges.
     * @return list of test challanges resolved.
     */
    public static List<Map<String, String>> viewResolvedTestChallange() {
        LazyList<Proposition> allResolved =
        Proposition.where("isSolution = ?", 1);
        LinkedList<Map<String, String>> resolved =
        new LinkedList<Map<String, String>>();
        if (!allResolved.isEmpty()) {
            for (Proposition challengeResolved : allResolved) {
                Challenge c = Challenge.findFirst(
                    "id = ?",
                    challengeResolved.get("challenge_id"));
                TestChallenge tc = TestChallenge.findFirst(
                    "challenge_id = ?",
                    challengeResolved.get("challenge_id"));
                Map<String, String> t = toTestChallege(c, tc);
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
    public static List<Map<String, String>> viewUnsolvedTestChallange() {
        List<Map<String, String>> resolved = viewResolvedTestChallange();
        List<Map<String, String>> all = viewAllTestChallange();
        List<Map<String, String>> unsolved =
        new LinkedList<Map<String, String>>();
        if (!all.isEmpty()) {
            for (Map<String, String> c: all) {
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
            "javac -cp .:/tmp/:target/dependency/junit-4.12.jar:. "
            + "/tmp/src/test/" + nameFile + ".java");
    }

    /**
     * This method to run a JUnit test.
     * @param nameFile name of the file to execute.
     * @return true if run otherwise false.
     */
    public static boolean runTestJava(final String nameFile) {
        return Challenge.runProcess(
            "java -cp .:/tmp:target/dependency/junit-4.12.jar:target/"
            + "dependency/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore "
            + "src.test." + nameFile);
    }

    /**
     * this method verifies if the challenge exists.
     * @param t test challenge to verifies.
     */
    public static void validatePresenceTestChallenge(final TestChallenge t) {
        if (t == null) {
            throw new IllegalArgumentException(CHALLENGE_NOT_EXIST);
        }
    }

    /**
     * This method returns a map <Challenge, TestChallenge>.
     * @param c a Challenge.
     * @param t a TestChallenge.
     * @return Map<Challenge, TestChallenge>.
     */
    public static Map<String, String> toTestChallege(
        final Challenge c, final TestChallenge t) {
        Map<String, String> ret = new HashMap<String, String>();
        ret.put(c.toJson(true, "id", "user_id", "title",
        "class_name", "description", "source", "point", "owner_solution_id"),
        t.toJson(true, "id", "challenge_id", "test"));
        return ret;

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
