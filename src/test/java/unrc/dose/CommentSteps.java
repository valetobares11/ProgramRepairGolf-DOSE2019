package unrc.dose;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.javalite.activejdbc.LazyList;

import unrc.dose.Comment;
import unrc.dose.StepUtils;
import unrc.dose.User;

public class CommentSteps extends StepUtils {
  private static User user = new User();
  private static User userAux = new User();
  private static Challenge ch = new Challenge();
  private static Comment c = new Comment();
  private static String title;
  private static String description;

  @Given("^the user \"([^\"]*)\" is already logged on$")
  public boolean the_user_is_already_logged_on(String arg1) throws Exception {
    user = User.findFirst("username = ?", arg1);
    return user != null;
  }

  @Given("^the challenge with the name \"([^\"]*)\" exists$")
  public boolean the_challenge_with_the_name_exists(String arg1) throws Exception {
    ch = Challenge.findFirst("title = ?", arg1);
    return ch != null;
  }

  @When("^the user writes the title \"([^\"]*)\" and the description \"([^\"]*)\"$")
  public void the_user_writes_the_title_and_the_description(String arg1, String arg2) throws Exception {
    title = arg1;
    description = arg2;
  }

  @Then("^the system will saves the comment$")
  public boolean the_system_will_saves_the_comment() throws Exception {
    c = Comment.createComment(title, description, ch.getInteger("id"), user.getInteger("id"));
    return c != null;
  }

  @Given("^the comment of the existed user \"([^\"]*)\" with the title \"([^\"]*)\"$")
  public boolean the_comment_of_the_existed_user_with_the_title(String arg1, String arg2) throws Exception {
    userAux = User.findFirst("username=?", arg1);
    title = arg2;
    return (userAux != null);
  }

  @Given("^the description \"([^\"]*)\"$")
  public void the_description(String arg1) {
    description = arg1;
  }

  @Given("^is in the challenge with the name \"([^\"]*)\" exists$")
  public boolean is_in_the_challenge_with_the_name(String arg1) {
    ch = Challenge.findFirst("title=?", arg1);
    c = Comment.findFirst("title= ? and description=? and user_id=? and challenge_id=?", title,description,userAux.getId(), ch.getId());
    return c != null;
  }


  @When("^the user writes the description \"([^\"]*)\" of the response$")
  public void the_user_writes_the_description_of_the_response(String arg1) throws Exception {
    description = arg1;
  }

  @Then("^the system will save the response$")
  public boolean the_system_will_save_the_response() throws Exception {
    Comment res = Comment.createResponse(description,user.getInteger("id"),c.getInteger("id"));
    return res != null;
  }

  @When("^the user wants to see the comments of the existed user \"([^\"]*)\"$")
  public boolean the_user_wants_to_see_the_comments_of_the_existed_user(String arg1) throws Exception {
    userAux = User.findFirst("username=?",arg1);
    return userAux != null;
  }


  @Then("^the system will return a list of comments of the user \"([^\"]*)\"$")
  public boolean the_system_will_return_a_list_of_comments_of_the_user(String arg1) throws Exception {
    LazyList<Comment> list = Comment.viewComment(userAux.getId(), new User());
    return list != null;
  }

  @When("^the user wants to see the comments$")
  public boolean the_user_wants_to_see_the_comments() throws Exception {
    return true;
  }

  @Then("^the system will return a list of comments of the challenge$")
  public boolean the_system_will_return_a_list_of_comments_of_the_challenge() throws Exception {
    LazyList<Comment> list = Comment.viewComment(ch.getInteger("id"), new Challenge());
    return list != null;
  }

  @When("^the user wants to see the responses of that comment$")
  public boolean the_user_wants_to_see_the_responses_of_that_comment() throws Exception {
    return true;
  }

  @Then("^the system will return a list of responses to the comment$")
  public boolean the_system_will_return_a_list_of_responses_to_the_comment() throws Exception {
    LazyList<Comment> list = Comment.viewComment(c.getInteger("id"), new Comment());
    return list != null;
  }
}
