package unrc.dose;

import unrc.dose.Challenge;
import org.javalite.activejdbc.Model;

/*	
Atributos de la tabla:
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

    public static boolean validateCompilationChallenge(Challenge c){
        return true;
    }

    public static CompilationChallenge addCompilationChallenge(int challenge_id){
        CompilationChallenge t = new CompilationChallenge();
        t.setChallengeId(challenge_id);
        t.saveIt();
        return t;
    }

}
