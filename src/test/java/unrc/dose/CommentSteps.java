package unrc.dose;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import static org.junit.Assert.*;
import unrc.dose.Comment;
import java.util.HashMap;
import java.util.Map;
import unrc.dose.Challenge;
import unrc.dose.User;
import unrc.dose.StepUtils;
import org.javalite.activejdbc.LazyList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommentSteps extends StepUtils {
  private static User user = new User();
  private static Challenge ch = new Challenge();
  private static Comment c = new Comment();
  private static String title;
  private static String description;
  @Given("^the user \"([^\"]*)\" is already logged on$")
  public boolean the_user_is_already_logged_on(String arg1) throws Exception {
      user = User.findFirst("username = ?", arg1);
      if (user==null){
        user = User.set("Pablo", "root", "pablo@gmail.com", false);
      }
      return (user!=null);
  }

  @Given("^the challenge with the name \"([^\"]*)\" exists$")
  public boolean the_challenge_with_the_name_exists(String arg1) throws Exception {
      ch = Challenge.findFirst("title = ?", arg1);
      if (ch==null){
        User admin = User.set("Juana", "root", "juana@gmail.com", true);
        ch = Challenge.addChallenge(admin.getInteger("id"), "Test",
        "challenge1", "descripcion de prueba", "codigo", 20, user.getInteger("id"));
      }
      return (ch!=null);
  }

  @When("^the user writes the title \"([^\"]*)\" and the description \"([^\"]*)\"$")
  public void the_user_writes_the_title_and_the_description(String arg1, String arg2) throws Exception {
      title= arg1;
      description= arg2;
  }

  @Then("^the system will saves the comment$")
  public void the_system_will_saves_the_comment() throws Ex1ception {
      c = Comment.createComment(title, description, ch.getInteger("id"), user.getInteger("id"));
  }

  @Given("^the comment exists$")
  public boolean the_comment_exists(Comment arg1) throws Exception {
      Comment aux = Comment.findById(arg1.getId());
      return (aux!=null);
  }

  @When("^the user writes the title \"([^\"]*)\" of the response and the description \"([^\"]*)\"$")
  public void the_user_writes_the_title_of_the_response_and_the_description(String arg1, String arg2) throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @Then("^the system will save the response$")
  public void the_system_will_save_the_response() throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @Given("^the user \"([^\"]*)\" exists$")
  public void the_user_exists(String arg1) throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @When("^the user \"([^\"]*)\" wants to see the comments of the user \"([^\"]*)\"$")
  public void the_user_wants_to_see_the_comments_of_the_user(String arg1, String arg2) throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @Then("^the system will return a list of comments of the user \"([^\"]*)\"$")
  public void the_system_will_return_a_list_of_comments_of_the_user(String arg1) throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @Given("^the challenge with id (\\d+) exists$")
  public void the_challenge_with_id_exists(int arg1) throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @When("^the user wants to see the comments$")
  public void the_user_wants_to_see_the_comments() throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @Then("^the system will return a list of comments of the challenge$")
  public void the_system_will_return_a_list_of_comments_of_the_challenge() throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @Given("^the comment with id (\\d+) exists$")
  public void the_comment_with_id_exists(int arg1) throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @When("^the user wants to see the responses of that comment$")
  public void the_user_wants_to_see_the_responses_of_that_comment() throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }

  @Then("^the system will return a list of responses to the comment$")
  public void the_system_will_return_a_list_of_responses_to_the_comment() throws Exception {
      // Write code here that turns the phrase above into concrete actions
      throw new PendingException();
  }
}
