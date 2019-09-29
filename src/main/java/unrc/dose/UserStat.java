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

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;

public class UserStat extends Model {

  public static LazyList showAllUsers (){
    LazyList<User> users = User.findAll();

    return users;
  }


}
