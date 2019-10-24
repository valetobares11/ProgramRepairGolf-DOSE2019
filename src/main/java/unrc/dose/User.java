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
*  unique(username),
*  unique(email_address)
*
**/

package unrc.dose;

import org.javalite.activejdbc.Model;

/**
* User class represents a person into the system.
*/

public class User extends Model {
    /**
    * The value of ID is {@value}.
    */
    private static final String ID = "id";
    /**
    * The value of USERNAME is {@value}.
    */
    private static final String USERNAME = "username";
    /**
    * The value of PASSWORD is {@value}.
    */
    private static final String PASSWORD = "password";
    /**
    * The value of EMAIL is {@value}.
    */
    private static final String EMAIL = "email_address";
    /**
    * The value of ADMIN is {@value}.
    */
    private static final String ADMIN = "admin";

    /**
    * @param name : username that user wants: String
    * @return value that represents if username already exits: Boolean
    */
    public static Boolean searchUserByUsername(final String name) {
        User user = User.findFirst(USERNAME + " = ?", name);

        return (user == null);
    }

    /**
    * @param email : username that user wants: String
    * @return value that represents if username already exits: Boolean
    */
    public static Boolean searchUserByEmail(final String email) {
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
    public static User set(final String name, final String pass,
        final String email, final Boolean admin) {
        User user = new User();
        Password p = new Password();

        byte[] salt = Password.getNextSalt();

        p.set("salt", salt);
        p.set(USERNAME, name);

        byte[] passw = Password.hash(pass.toCharArray(), salt);
        user.load(user, name, passw, email, admin);
        user.saveIt();
        p.saveIt();

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
    public byte[] getPass() {
        return (byte[]) this.get(PASSWORD);

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
    private void load(final User u, final String name,
        final byte[] pass, final String email, final Boolean admin) {
        u.set(USERNAME, name);
        u.set(PASSWORD, pass);
        u.set(EMAIL, email);
        u.set(ADMIN, admin);
    }

     /**
     * this method remove logically a user, set the user as inactive.
     * @param name this username is for delete logically,
     * his account associate
     * @param pass this param is used for confirm the operation
     * @return a boolean with this exit of remove or no remove the user
     */
    public static boolean disableUser(final String name,
        final String pass) {
        User user = User.findFirst("username = ? ", name);
        if (user != null) {
            byte[] passSaved = user.getPass();
            Password passUser = Password.findFirst("username = ?", name);
            byte[] salt = (byte[]) passUser.get("salt");
            user.set("active_account", false);
            return ((Password.isExpectedPassword(pass.toCharArray(),
            salt, passSaved)));
        }
        return false;
    }

     /**
     * this method is for User's credential validation,
     * given a particular user's credential,
     * this method will validate if they are valid.
     * @param name this username is for validate data
     * @param pass this param is used for finally operation
     * @return {@code true} iff the user's credential are valid and the
     * user was successfully logged in
     */
    public static boolean validateCredentials(final String name,
        final String pass) {
        User user = User.findFirst("username = ? ", name);
        if (user != null) {
            byte[] passSaved = user.getPass();
            Password passUser = Password.findFirst("username = ?", name);
            byte[] salt = (byte[]) passUser.get("salt");

            return (Password.isExpectedPassword(pass.toCharArray(),
                salt, passSaved));
        } else {
            return false;
        }
    }

}
