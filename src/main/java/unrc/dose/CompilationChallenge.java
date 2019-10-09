package unrc.dose;

import org.javalite.activejdbc.Model;

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
        String title = c.getTitle();
        String source = c.getSource();
        String nameFile = Challenge.generateFileJava(title, source);
        return (Challenge.runProcess("javac /../tmp/" + nameFile)!=0);
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

}
