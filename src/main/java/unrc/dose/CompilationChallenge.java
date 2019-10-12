package unrc.dose;

import java.util.LinkedList;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;

/**
 * table attributes.
 * id integer not null auto_increment primary key.
 * challenge_id integer not null.
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
     * method that returns the compilation challenge according to
     * the id of the challenge.
     * @param idChallenge challenge id.
     * @return CompilationChallenge.
     */
    public static CompilationChallenge getChallenge(final int idChallenge) {
        return CompilationChallenge.findFirst("challenge_id", idChallenge);
    }

    /**
     * method that returns a list of all compilation challenges.
     * @return list of all compilation challange.
     */
    public static List<Challenge> viewAllCompilationChallange() {
        LazyList<CompilationChallenge> compChall =
        CompilationChallenge.findAll();
        LinkedList<Challenge> allChallenge = new LinkedList<Challenge>();
        if (!compChall.isEmpty()) {
            for (CompilationChallenge compilationChallenge : compChall) {
                Challenge res = Challenge.findFirst(
                    "id = ?",
                    compilationChallenge.get("challenge_id"));
                allChallenge.add(res);
            }
        }
        return allChallenge;
    }


    /**
     * method that returns a list of resolved compilation challenges.
     * @return list of compilacion challanges resolved.
     */
    public static List<Challenge> viewSolvedCompilationChallange() {
        LazyList<OwnerSolution> ownerSol = OwnerSolution.findAll();
        LinkedList<Challenge> resolved = new LinkedList<Challenge>();
        if (!ownerSol.isEmpty()) {
            for (OwnerSolution challengeResolved : ownerSol) {
                if (CompilationChallenge.exists(
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
     * method that returns a list of unresolved compilation challenges.
     * @return list of compilation challanges unresolved.
     */
    public static List<Challenge> viewUnsolvedCompilationChallange() {
        List<Challenge> resolved = viewSolvedCompilationChallange();
        List<Challenge> unresolved = new LinkedList<Challenge>();
        if (!resolved.isEmpty()) {
            for (Challenge res : resolved) {
                if (!(CompilationChallenge.exists(res.get("challenge_id")))) {
                    unresolved.add(res);
                }
            }
        }
        return unresolved;
    }

}
