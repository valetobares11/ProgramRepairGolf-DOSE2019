package unrc.dose;

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

}
