package unrc.dose;

import unrc.dose.TestChallenge;
import unrc.dose.CompilationChallenge;
import org.javalite.activejdbc.Model;

/*	
Atributos de la tabla:
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
     */
    public static boolean addCompilationChallenge(int user_id, String title, String description, String source, int point, int owner_solution_id){
        Challenge c = addChallenge(user_id, title, description, source, point, owner_solution_id);
        CompilationChallenge t = CompilationChallenge.addCompilationChallenge(c.getInteger("id"));
        if (CompilationChallenge.validateCompilationChallenge(c))
            return true;
        else 
            return false;
    }

    /**
     */
    public static void deleteChallenge(Challenge c){
        c.deleteCascade();
    }

}
