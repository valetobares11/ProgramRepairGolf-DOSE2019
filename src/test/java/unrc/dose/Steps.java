package unrc.dose;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import static org.junit.Assert.*;

public class Steps {
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
}

