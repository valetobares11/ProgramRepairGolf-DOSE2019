package unrc.dose;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import unrc.dose.User;
import unrc.dose.StepUtils;

import org.javalite.activejdbc.LazyList;

public class Steps extends StepUtils {
  Belly belly;

  @Given("^(?:I have|the user has) (\\d+) cukes in my belly$")
  public void i_have_cukes_in_my_belly(int cukes) throws Exception {
    belly = new Belly();
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
    u.save();
  }

  @Then("^I should get them$")
  public void i_should_see_them() throws Exception {
    Map<String, String> parameters = new HashMap<>();

    // Notice we pass empty parameter when we do a GET request
    UrlResponse response = StepUtils.doRequest("GET", "/users", parameters);

    // Retrieve DB users
    LazyList<User> users = User.findAll();

    assertNotNull(response);
    assertNotNull(response.body);
    assertEquals(200, response.status);

    // Check that the same user save in DB is the same that we got from http req
    assertEquals(response.body, users.toJson(true, "username", "password"));
  }
}
