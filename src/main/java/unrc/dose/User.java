/*
* == Schema Info
*
* Table name: users
*
*  id              :integer(11)    not null, primary key
*  username        :varchar(20)    not null,
*  password        :varchar(20)    not null,
*  email_address   :varchar(50)    not null,
*  admin           BOOLEAN not null default 0
*  active_account  BOOLEAN not null default 1
*
**/

package unrc.dose;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;

public class User extends Model {

	static final String user_delete;
	static final String user_not_found;

	 /**
     * this method remove logically a user
     * @param username_user this username is for delete logicaly, his account associate
     * @param password_user this param is used for confirm the operation
     * @return a string with this exit of remove or no remove the user
     */
	public static String deleteUser(String username_user, String password_user){
		User user = new User();
		LazyList<User> users = User.where("username = ? and password = ?", username_user, password_user);
		
        if(users.size() > 0){
			user = users.get(0);
			user.set("active_account", false);
			return user_delete;
		}
		return user_not_found;
	}
}
