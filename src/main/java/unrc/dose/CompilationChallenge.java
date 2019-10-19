package unrc.dose;

import java.util.LinkedList;
import java.util.List;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

/**
 * Table compilation_challenges - Attributes.
 * id integer not null auto_increment primary key.
 * challenge_id integer not null.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public class CompilationChallenge extends Model {

    /**
     * the class constructor.
     */
    public CompilationChallenge() { }

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
     * This method is responsible for validating the compilation challenge.
     * @param c challenge to validate.
     * @return True in case the validation passes (source does not compile),
     * otherwise false.
     */
    public static boolean validateCompilationChallenge(final Challenge c) {
        String title = c.getClassName();
        String source = c.getSource();
        Challenge.generateFileJava(title, source);
        return !(Challenge.runCompilation(title));
    }

    /**
     * This method allows you to create a test challenge.
     * @param challengeId challenge id.
     * @return compilation challenge already created.
     */
    public static CompilationChallenge addCompilationChallenge(
        final int challengeId) {
        CompilationChallenge t = new CompilationChallenge();
        t.setChallengeId(challengeId);
        t.saveIt();
        return t;
    }

    /**
     * method that returns a list of all compilation challenges.
     * @return list of all compilation challange.
     */
    public static List<Challenge> viewAllCompilationChallange() {
        LazyList<CompilationChallenge> all = CompilationChallenge.findAll();
        LinkedList<Challenge> allChallenges = new LinkedList<Challenge>();
        if (!all.isEmpty()) {
            for (CompilationChallenge currentChallenge : all) {
                Challenge c = Challenge.findFirst(
                    "id = ?",
                    currentChallenge.get("challenge_id"));
                allChallenges.add(c);
            }
        }
        return allChallenges;
    }


    /**
     * method that returns a list of resolved compilation challenges.
     * @return list of compilacion challanges resolved.
     */
    public static List<Challenge> viewResolvedCompilationChallange() {
        LazyList<Proposition> allResolved =
        Proposition.where("isSubmit = ?", 1);
        List<Challenge> resolved = new LinkedList<Challenge>();
        if (!allResolved.isEmpty()) {
            for (Proposition challengeResolved : allResolved) {
                Challenge c = Challenge.findFirst(
                    "id = ?",
                    challengeResolved.get("challenge_id"));
                if (!(resolved.contains(c))) {
                    resolved.add(c);
                }
            }
        }
        return resolved;
    }

    /**
     * method that returns a list of unresolved compilation challenges.
     * @return list of compilation challanges unresolved.
     */
    public static List<Challenge> viewUnsolvedCompilationChallange() {
        List<Challenge> resolved = viewResolvedCompilationChallange();
        List<Challenge> all = viewAllCompilationChallange();
        List<Challenge> unsolved = new LinkedList<Challenge>();
        if (!(all.isEmpty())) {
            for (Challenge c: all) {
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
     */
    public static void modifyUnsolvedCompilationChallenge(
        final int challengeId,
        final String title,
        final String className,
        final String description,
        final String source,
        final int point) {
        Challenge.checkUnsolvedChallenge(challengeId);
        Challenge c = Challenge.findFirst("id", challengeId);
        c.setTitle(title);
        c.setClassName(className);
        c.setDescription(description);
        c.setSource(source);
        c.saveIt();
    }

}
