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
	
	/**
	 * Create the statistics for a given user.
	 * @param u The user who statistics we will create. 
	 */
	public static void createUserStat(User u){
		UserStat stat = new UserStat();
		stat.set("user_id",u.get("id"));
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
}
