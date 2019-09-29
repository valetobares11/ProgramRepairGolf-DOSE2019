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
*
**/

package unrc.dose;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;

public class User extends Model {

	public static Boolean searchUser(String name, String pass, String email){
		LazyList<User> users = User.where("username = ? and password = ? and email_address = ?", name, pass, email);

		if(users.size() > 0){
			return true;
		}
		return false;
	}


}
