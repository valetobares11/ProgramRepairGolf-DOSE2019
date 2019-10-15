package unrc.dose;

import java.util.LinkedList;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;

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
    public static List<Tuple<Challenge, CompilationChallenge>>
        viewSolvedCompilationChallange() {
        LazyList<Proposition> allProposition =
        Proposition.where("isSubmit = ?", 1);
        LinkedList<Tuple<Challenge, CompilationChallenge>> resolved
        = new LinkedList<Tuple<Challenge, CompilationChallenge>>();
        if (!allProposition.isEmpty()) {
            for (Proposition challengeResolved : allProposition) {
                if (CompilationChallenge.exists(
                    challengeResolved.get("challenge_id"))) {
                    Challenge c = Challenge.findFirst(
                        "id = ?",
                        challengeResolved.get("challenge_id"));
                    CompilationChallenge tc = CompilationChallenge.findFirst(
                        "challenge_id = ?",
                        challengeResolved.get("challenge_id"));
                    Tuple<Challenge, CompilationChallenge> t =
                    new Tuple<Challenge, CompilationChallenge>(c,
                    tc);
                    resolved.add(t);
                }
            }
        }
        return resolved;
    }

    /**
     * method that returns a list of unresolved compilation challenges.
     * @return list of compilation challanges unresolved.
     */
    public static List<Tuple<Challenge, CompilationChallenge>>
        viewUnsolvedCompilationChallange() {
        List<Tuple<Challenge, CompilationChallenge>> resolved =
        viewSolvedCompilationChallange();
        List<Tuple<Challenge, CompilationChallenge>> unsolved =
        new LinkedList<Tuple<Challenge, CompilationChallenge>>();
        if (!resolved.isEmpty()) {
            for (Tuple<Challenge, CompilationChallenge> challResolv: resolved) {
                if (!(CompilationChallenge.exists(
                    challResolv.getFirst().get("challenge_id")))) {
                    unsolved.add(challResolv);
                }
            }
        }
        return unsolved;
}

}
