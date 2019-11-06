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
     The value of MAX_VALUE is {@value}.
    */
    static final Integer MAX_VALUE = 20;
    /**
     The value of MIN_VALUE is {@value}.
    */
    static final Integer MIN_VALUE = 6;

    /**
    * @param name : username that user wants: String
    * @return value that represents if username already exits: Boolean
    */
    public static Boolean userExistsByUsername(final String name) {
        User user = User.findFirst(USERNAME + " = ?", name);

        return (user != null);
    }

    /**
    * @param email : username that user wants: String
    * @return value that represents if username already exits: Boolean
    */
    public static Boolean userExistsByEmail(final String email) {
        User user = User.findFirst(EMAIL + " = ?", email);
        return (user != null);
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

        UserStat.createUserStat(user.getId());

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




    /**.
     * Method that given un email and new password returns a boolean ,
     * if the password has been modified successfully
     * @param email : the email of the user who wants to change the password
     * @param  pass : the new password
     * @return A boolean if the password has been modified successfully
     */
    public static Boolean updatePassword(final String email,
        final String oldPass, final String newPass) {
        User user = User.findFirst("email_address = ?", email);
        if (user != null) {
            String name = user.getName();
            
            if(User.validateCredentials(name,oldPass)){
                byte[] passSaved = user.getPass();
                Password passUser = Password.findFirst("username = ?", name);
                byte[] salt = (byte[]) passUser.get("salt");
                if ( Password.isExpectedPassword(newPass.toCharArray(),
                     salt, passSaved) || newPass.length() <= MIN_VALUE
                    || newPass.length() > MAX_VALUE) {

                    return false;
                } else {
                    byte[] passw = Password.hash(newPass.toCharArray(), salt);
                    user.set("password", passw);
                    user.saveIt();
                return true;
                }
            }
        }
        return false;
    }

    /**.
     * Method that generate a new password for a user ,
     * for a user that forgot his pass
     * @param email : the email of the user who wants to change the username
     * @return A String the new pass
     */
    public static String resetPassword(final String email) {
      User user = User.findFirst(EMAIL + " = ? ", email);
      if (user != null) {
        String name = user.getName();
        Password passUser = Password.findFirst(USERNAME + " = ?", name);
        byte[] salt = (byte[]) passUser.get("salt");
        String newpass = Password.generateRandomPassword();
        byte[] passw = Password.hash(newpass.toCharArray(), salt);
        user.set(PASSWORD, passw);
        user.saveIt();
        return newpass;
        } else {
            return null;
        } 
    }

    /**.
     * Method that given un new email and username returns a boolean ,
     * if the email has been modified successfully
     * @param email : the new email of the user who wants to change his email address
     * @param  username : the new username
     * @return A boolean if the email has been modified successfully
     */

    public static Boolean updateEmail(final String newEmail,
        final String username) {
        User user = User.findFirst(USERNAME + " = ?", username);
        if (user != null) {
            user.set(EMAIL, newEmail);
            user.saveIt();
        return true;
        }
        return false;
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
