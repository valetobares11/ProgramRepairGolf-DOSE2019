package unrc.dose;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.HashMap;
import java.util.Map;

import unrc.dose.User;
import unrc.dose.StepUtils;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import org.javalite.activejdbc.LazyList;

public class UserStep extends StepUtils{
    User user = new User();
    Password passw;
    String pass;
    String email;
    String name;

    @BeforeClass
    public static void beforeAll() {
        if (!Base.hasConnection()) {
            Base.open();
            Base.openTransaction();

        }
    }

    @AfterClass
    public static void afterAll() {
        Base.rollbackTransaction();
        Base.close();
    }


    @Given( "^that a user wants to create an account$")
    public void that_a_user_wants_to_create_an_account()  {
        
    }

    @Given( "^the username selected is \"([^\"]*)\"$")
    public void the_username_selected_is(String arg1) {
        name = arg1;

    }

    @Given( "^the selected password is \"([^\"]*)\"$")
    public void the_selected_password_is(String arg1)  {         
        pass = arg1;
    }


    @Given( "^the users’ email is \"([^\"]*)\"$")
    public void the_users_email_is(String arg1)  {
         email = arg1;
        }


    @Given("^no user exists in the system with the given email is \"([^\"]*)\" or username is \"([^\"]*)\"$")
    public void no_user_exists_in_the_system_with_the_given_email_is_or_username_is(String arg1, String arg2) {
        if(User.userExistsByUsername(arg1)||User.userExistsByEmail(arg2)) {
            System.out.println("username o email ya existente");
        } else {
            System.out.println("No existe el username ni el email");
        }
    }


    @When( "^the user tries to create an account with the given credentials$")
    public void the_user_tries_to_create_an_account_with_the_given_credentials()  {
        System.out.println("Creacion del usuario en progreso");

    }

    @Then( "^the system should create the account$")
    public void the_system_should_create_the_account() {
        
        user = User.set(name, email, pass, false);
    }

    @Then( "^inform the user that the account has been created$")
    public void inform_the_user_that_the_account_has_been_created(){
        
        assertEquals(true, User.userExistsByUsername(name));
    }

}
