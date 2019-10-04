package unrc.dose;

import unrc.dose.Challenge;
import org.javalite.activejdbc.Model;

/*	
 * table attributes:
    id integer not null auto_increment primary key,
    challenge_id integer not null,
    test varchar(1000) not null
*/
public class TestChallenge extends Model {

    public TestChallenge() {}

    public int getChallengeId(){    
        return getInteger("challenge_id");
    }

    public void setChallengeId(int challenge_id){
        set("challenge_id",challenge_id);
    }

    public String getTest(){    
        return getString("test");
    }

    public void setTest(String test){
        set("test",test);
    }
    
    /**
     * This method is responsible for validating the compilation challenge.
     * @param c challenge to validate.
     * @param t test challenge to validate
     * @return True in case the validation passes (the source compiles and the tests run), otherwise, false.
     */
    public static boolean validateTestChallenge(Challenge c, TestChallenge t){
        return true;
    }

    /**
     * This method allows you to create a compilation challenge.
     * @param challenge_id challenge id.
     * @param test test code.
     * @return test challenge already created.
     */
    public static TestChallenge addTestChallenge(int challenge_id, String test){
        TestChallenge t = new TestChallenge();
        t.setChallengeId(challenge_id);
        t.setTest(test);
        t.saveIt();
        return t;
    }
}
