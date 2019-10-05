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
import org.javalite.activejdbc.LazyList;

public class UserStat extends Model {
	/*Validators for model*/
	static {
		validatePresenceOf("user_id");
		validatePresenceOf("created_challenges");
		validatePresenceOf("solved_challenges");
		validatePresenceOf("current_points");
	}
	/**
	 * Gets the id of the user who has assigned this statistics 
	 * in the database.
	 * @return The id of the user.
	 */
	public int getUserId() {
		return this.getInteger("user_id");
	}
	
	/**
	 * Gets the number of created challenges by the user
	 * who has assigned this statistics in the database.
	 * @return The number of created challenges.
	 */
	public int getCreatedChallenges() {
		return this.getInteger("created_challenges");
	}
	
	/**
	 * Gets the number of solved challenges by the user
	 * who has assigned this statistics in the database.
	 * @return The number of solved challenges.
	 */
	public int getSolvedChallenges() {
		return this.getInteger("solved_challenges");
	}
	
	/**
	 * Gets the points that the user earned solving challenges.
	 * @return The points of the user.
	 */
	public int getCurrentPoints() {
		return this.getInteger("current_points");
	}

	/**
	 * Show all users who are registered in the system
	 *@return LazyList with all the users.
	 */
	public static LazyList<User> showAllUsers (){
		LazyList<User> users = User.findAll();
		return users;
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
