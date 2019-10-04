package unrc.dose;

import unrc.dose.Challenge;
import org.javalite.activejdbc.Model;

/*	
 * table attributes:
	id integer not null auto_increment primary key,
    challenge_id integer not null
*/
public class CompilationChallenge extends Model {

    public CompilationChallenge() {}

    public int getChallengeId(){    
        return getInteger("challenge_id");
    }

    public void setChallengeId(int challenge_id){
        set("challenge_id",challenge_id);
    }

    /**
     * This method is responsible for validating the compilation challenge.
     * @param c challenge to validate.
     * @return True in case the validation passes (source does not compile), otherwise false.
     */
    public static boolean validateCompilationChallenge(Challenge c){
        return true;
    }

    /**
     * This method allows you to create a test challenge.
     * @param challenge_id challenge id.
     * @return compilation challenge already created.
     */
    public static CompilationChallenge addCompilationChallenge(int challenge_id){
        CompilationChallenge t = new CompilationChallenge();
        t.setChallengeId(challenge_id);
        t.saveIt();
        return t;
    }

}
