package unrc.dose;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import unrc.dose.User;
import unrc.dose.StepUtils;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class Steps extends StepUtils {
  Belly belly;
  UrlResponse response;
  User u;
  int points;
  
  /**
   * Opens the database
   * before all the tests
   */
  @Before
  public static void classSetUp() {
      if(!Base.hasConnection()) {
          Base.open();
      }
      Base.openTransaction();
  }

  
  /**
   * Rollback a transaction
   * after each test
   */
  @After
  public void tearDown() {
      Base.rollbackTransaction();
      if(Base.hasConnection()) {
          Base.close();
      }
  }

  
  @Given("^(?:I have|the user has) (\\d+) cukes in my belly$")
  public void i_have_cukes_in_my_belly(int cukes) throws Exception {
    belly = new Belly(1, "pedro");
    belly.eat(cukes);
  }

  @Then("^I should be full$")
  public void i_should_be_full() throws Exception {
    assertTrue(belly.isFull());
  }

  @Given("^there are system users$")
  public void there_are_system_users() throws Exception {
    User.deleteAll();

    User u = new User();
    u.set("password", "VeryHardPass");
    u.set("username", "JohnConnor");
    u.set("email_address", "JohnConnor@gmail.com");
    u.save();
  }

  @Then("^I should get them$")
  public void i_should_see_them() throws Exception {
    Map<String, String> parameters = new HashMap<>();

    // Notice we pass empty parameter when we do a GET request
    response = StepUtils.doRequest("GET", "/users", parameters);

    // Retrieve DB users
    LazyList<User> users = User.findAll();

    assertNotNull(response);
    assertNotNull(response.body);
    assertEquals(200, response.status);

    // Check that the same user save in DB is the same that we got from http req
    assertEquals(response.body, users.toJson(true, "username", "password"));
  }


  @Given("^user wants to look his score$")
  public void user_wants_to_look_his_score() throws Exception {
    u = User.set("Hackerman", "T3H4ck303lC0r4z0n", "hackingnsa@gmail.com", false);
    u.saveIt();
  }

  @When("^user press show score$")
  public void user_press_show_score() throws Exception {
    Map<String, Integer> parameters = new HashMap<>();
    parameters.put("id", u.getId());
    UserStat us = UserStat.findFirst("user_id", u.getId());
    points = us.getCurrentPoints();
  }

  @Then("^the system show the score on a message$")
  public void the_system_show_the_score_on_a_messag() throws Exception {
      UserStat us = UserStat.findFirst("user_id", u.getId());
      assertEquals((int)us.getInteger(UserStat.CURRENTPOINTS),points);
  }
      
  @Given("^he wants to select the type of level education for play$")
  public void he_wants_to_select_the_type_of_level_education_for_play() throws Exception {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

  @When("^the user select the type of level$")
  public void the_user_select_the_type_of_level() throws Exception {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

  @Then("^the system should be show a challenge depending on level selected for the user$")
  public void the_system_should_be_show_a_challenge_depending_on_level_selected_for_the_user() throws Exception {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
  }

@Given("^wants select a type of challenge for play$")
public void wants_select_a_type_of_challenge_for_play() throws Exception {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@When("^the user select a type of challenge$")
public void the_user_select_a_type_of_challenge() throws Exception {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Then("^the system should be show one challenge based in the selection$")
public void the_system_should_be_show_one_challenge_based_in_the_selection() throws Exception {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}


}
