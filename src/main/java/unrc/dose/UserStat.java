/* db-attributes of userStats:
	id
	user_id
	created_challenges
	solved_challenges
	current_points
	created_at
	updated_at
*/
package unrc.dose;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.DBException;

public class UserStat extends Model {
	/*Validators for model*/
	static {
		validatePresenceOf("user_id");
		validatePresenceOf("created_challenges");
		validatePresenceOf("solved_challenges");
		validatePresenceOf("current_points");
	}
	
	/**
	 * Create the statistics for a given user.
	 * @param id The id of the user who statistics we will create. 
	 */
	public static void createUserStat(int id){
		UserStat stat = new UserStat();
		stat.set("user_id",id);
		stat.set("created_challenges", 0);
		stat.set("solved_challenges",0);
		stat.set("current_points", 0);
		Base.openTransaction();
		try {
			stat.saveIt();
			Base.commitTransaction();
		}
		catch(DBException e) {
			Base.rollbackTransaction();    	
		}
	}
	
	/**
	 * This method return the statics of the user
	 * @param user
	 * @return UserStat of the user
	 */
	public static UserStat getUserStat(User user) {
		return UserStat.findFirst("user_id = ?", user.getId());
	}

}
