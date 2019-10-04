package unrc.dose;

import unrc.dose.TestChallenge;
import unrc.dose.CompilationChallenge;
import org.javalite.activejdbc.Model;

/*	
 * table attributes:
    id integer auto_increment primary key,
    user_id: integer,
    title varchar (50),
    description varchar(50),
    source varchar(10000),
    point integer,
    owner_solution_id integer
*/
public class Challenge extends Model {


    public Challenge() {}

    public int getUserId(){    
        return getInteger("user_id");
    }

    public void setUserId(int user_id){
        set("user_id", user_id);
    }

    public String getTitle(){
        return getString("title");
    }

    public void setTitle(String title){
        set("title", title);
    }

    public String getDescription(){
        return getString("description");
    }

    public void setDescription(String description){
        set("description", description);
    }

    public String getSource(){
        return getString("source");
    }

    public void setSource(String source){
        set("source", source);
    }

    public int getPoint(){
        return getInteger("point");
    }

    public void setPoint(int point){
        set("point", point);
    }

    public int getOwnerSolutionId(){
        return getInteger("owner_solution_id");
    }

    public void setOwnerSolutionId(int owner_solution_id){
        set("owner_solution_id", owner_solution_id);
    }

    /**
     * This method allows to create a challenge
     * @param user_id user id that created it
     * @param title title that will have the challenge
     * @param description a brief description of what the challenge is about
     * @param source source code that will have the challenge
     * @param point points given by the admin that for the challenge
     * @param owner_solution_id id of the solution proposed by a user
     * @return challenge created
     */
    public static Challenge addChallenge(int user_id, String title, String description, String source, int point, int owner_solution_id){
        Challenge c = new Challenge();
        c.setUserId(user_id);
        c.setTitle(title);
        c.setDescription(description);
        c.setSource(source);
        c.setPoint(point);
        c.setOwnerSolutionId(owner_solution_id);
        c.saveIt();
        return c;
    }

    /**
     * This method allows you to create a test challenge and validate it.
     * @param user_id user id that created it.
     * @param title title that will have the challenge.
     * @param description a brief description of what the challenge is about.
     * @param source source code that will have the challenge.
     * @param point points given by the admin that for the challenge.
     * @param owner_solution_id id of the solution proposed by a user.
     * @param test test corresponding to the challenge.
     * @return true in case the validation is successful, otherwise false.
     */
    public static boolean addTestChallenge(int user_id, String title, String description, String source, int point, int owner_solution_id, String test){
        Challenge c = addChallenge(user_id, title, description, source, point, owner_solution_id);
        TestChallenge t = TestChallenge.addTestChallenge(c.getInteger("id"), test);
        if (TestChallenge.validateTestChallenge(c, t))
            return true;
        else 
            return false;
    }

    /**
     * This method allows you to create a compilation challenge and validate it.
     * @param user_id user id that created it.
     * @param title title that will have the challenge.
     * @param description a brief description of what the challenge is about.
     * @param source source code that will have the challenge.
     * @param point points given by the admin that for the challenge.
     * @param owner_solution_id id of the solution proposed by a user.
     * @return true in case the validation is successful, otherwise false.
     */
    public static boolean addCompilationChallenge(int user_id, String title, String description, String source, int point, int owner_solution_id){
        Challenge c = addChallenge(user_id, title, description, source, point, owner_solution_id);
        CompilationChallenge.addCompilationChallenge(c.getInteger("id"));
        if (CompilationChallenge.validateCompilationChallenge(c))
            return true;
        else 
            return false;
    }

    /**
     * This method allows you to delete a challenge created.
     * @param c challenge to eliminate.
     */
    public static void deleteChallenge(Challenge c){
        c.deleteCascade();
    }

}
