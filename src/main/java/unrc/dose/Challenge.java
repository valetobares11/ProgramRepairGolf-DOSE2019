package unrc.dose;

import org.javalite.activejdbc.Model;

/*	
Atributos de la tabla:
    id integer auto_increment primary key,
    user_id: integer,
    title varchar (50),
    description varchar(50),
    source varchar(10000),
    point integer,
    owner_id integer,
    owner_solution_id integer
*/
public class Challenge extends Model {


    public Challenge() {}

    public int getUserId(){    
        return getInteger("user_id");
    }

    public void setUserId(int user_id){
        set("user_id",user_id);
    }

    public String getTitle(){
        return getString("title");
    }

    public void setTitle(String title){
        set("title",title);
    }

    public String getDescription(){
        return getString("description");
    }

    public void setDescription(String description){
        set("description",description);
    }

    public String getSource(){
        return getString("source");
    }

    public void setSource(String source){
        set("source",source);
    }
    public int getPoint(){
        return getInteger("point");
    }

    public void setPoint(String point){
        set("point",point);
    }

    
}
