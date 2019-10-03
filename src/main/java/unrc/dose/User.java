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
*  unique(username),
*  unique(email_address)
*
**/

package unrc.dose;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;


public class User extends Model {

	static final String ID = "id";
	static final String USERNAME = "username";
	static final String PASSWORD = "password";
	static final String EMAIL = "email_address";
	static final String ADMIN = "admin";

	/**
	* @param name : username that user wants: String
	* @return value that represents if username already exits: Boolean 
	*/
	public static Boolean searchUserByUsername (String name) {
		User user = User.findFirst(USERNAME + " = ?", name);

		return (user == null);
	}

	/**
	* @param email : username that user wants: String
	* @return value that represents if username already exits: Boolean 
	*/
	public static Boolean searchUserByEmail (String email) {
		User user = User.findFirst(EMAIL + " = ?", email);

		return (user == null);
	}

	/**
    * @param name : username that user charge: String
    * @param pass : password that user charge: String
    * @param email : email_address that user charge: String
    * @param admin : if user has privileges of admin users: Boolean
    * @return user created: User 
    */
	public static User set (String name, String pass, String email, Boolean admin) {
		User user = new User();
		
		user.load(user, name, pass, email, admin);
		
		return user;

	}

    /**
    * @return id of a user: Integer
    */
	public Integer getId() {
		return this.getInteger(ID);

	}

    /**
    * @return username of the user: String
    */
	public String getName() {
		return this.getString(USERNAME);

	}

    /**
    * @return password of the user: String
    */
	public String getPass() {
		return this.getString(PASSWORD);

	}

    /**
    * @return email_address of the user: String
    */
	public String getEmail() {
		return this.getString(EMAIL);

	}

    /**
    * @return represents if user has privileges of admin: Boolean
    */
	public Boolean getAdmin() {
		return this.getBoolean(ADMIN);

	}

    /**
    * @param u : user: User
    * @param name : username that user charge: String
    * @param pass : password that user charge: String
    * @param email : email_address that user charge: String
    * @param admin : if user has privileges of admin users: Boolean
    */
	private void load(User u, String name, String pass, String email, Boolean admin) {
		u.set(USERNAME, name);
		u.set(PASSWORD, pass);
		u.set(EMAIL, email);
		u.set(ADMIN, admin);
	}

}
