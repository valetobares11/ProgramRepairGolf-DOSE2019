package unrc.dose;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import unrc.dose.User;
import unrc.dose.StepUtils;

import org.javalite.activejdbc.LazyList;

public class CommentSteps extends StepUtils {
  Comment c = new Comment();
  Comment r = new Comment();
  User u = new User();
  @When("^the user writes the title \"([^\"]*)\" and the description \"([^\"]*)\"$")
public void the_user_writes_the_title_and_the_description(String arg1, String arg2) throws Exception {
    c.set("title", arg1);
    c.set("description", arg2);
}

@Then("^the system will saves the comment$")
public void the_system_will_saves_the_comment() throws Exception {
    c.saveIt();
}

@Given("^the comment already exists$")
public void the_comment_already_exists() throws Exception {
    assertNotNull(c);
}

@When("^the user writes the title \"([^\"]*)\" of the response and the description \"([^\"]*)\" to the comment with id (\\d+)$")
public void the_user_writes_the_title_of_the_response_and_the_description_to_the_comment_with_id(String arg1, String arg2, int arg3) throws Exception {
  r.set("title", arg1);
  r.set("description", arg2);
  r.set("comment_id", arg3);
}


@Then("^the system will save the response$")
public void the_system_will_save_the_response() throws Exception {
    r.saveIt();
}

@Given("^the user \"([^\"]*)\" exists$")
public void the_user_exists(String arg1) throws Exception {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@When("^the user wants to see the comments \"([^\"]*)\"$")
public void the_user_wants_to_see_the_comments(String arg1) throws Exception {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}

@Then("^the system will return a list of comments of the user$")
public void the_system_will_return_a_list_of_comments_of_the_user() throws Exception {
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

@Given("^the comment exists$")
public void the_comment_exists() throws Exception {
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
