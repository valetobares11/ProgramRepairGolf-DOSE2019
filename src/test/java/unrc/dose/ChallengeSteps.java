package unrc.dose;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.javalite.activejdbc.Base;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChallengeSteps extends StepUtils {

    private static final Logger log = LoggerFactory.getLogger(ChallengeSteps.class);

    private static User user = new User();
    private static User user2 = new User();
    private static Challenge challenge = new Challenge();
    private static TestChallenge testChallenge = new TestChallenge();  
    
    @BeforeClass
	public static void before(){
		if (!Base.hasConnection()) {
			Base.open();
			log.info("ChallengeSteps setup");
			Base.openTransaction();
        }
	}

	@AfterClass
	public static void after(){
		if (Base.hasConnection()) {
			Base.rollbackTransaction();
			log.info("ChallengeSteps tearDown");
			Base.close();
		}  
	}

    @Given("^the the user \"([^\"]*)\" is already logged on$")
    public void the_the_user_is_already_logged_on(String arg1) throws Exception {
        user = User.findFirst("username = ?", arg1);
    }
    
    @Given("^the user is a content creator user$")
    public void the_user_is_a_content_creator_user() throws Exception {
        user.set("admin",true);
    }
    
    @Given("^the type of the challenge is “test challenge”$")
    public void the_type_of_the_challenge_is_test_challenge() throws Exception {
        TestChallenge.addTestChallenge(
            user.getId(), 
            "Min", 
            "Min",
            "Min de dos numero",
            "",
            10, 
            1, 
            "test");
        testChallenge = TestChallenge.findFirst("className = ?", "Min");
        challenge = Challenge.findFirst("className = ?", "Min");
    }
    
    @Given("^the text of the challenge is$")
    public void the_text_of_the_challenge_is(String arg1) throws Exception {
        challenge.setSource(arg1);
    }
    
    @Given("^the text of the challenge compiles$")
    public void the_text_of_the_challenge_compiles() throws Exception {
        Challenge.runCompilation( challenge.getClassName());
    }
    
    @Given("^the text of the tests is:$")
    public void the_text_of_the_tests_is(String arg1) throws Exception {
        testChallenge.setTest(arg1);
    }
    
    @Given("^the at least one of the tests fails$")
    public void the_at_least_one_of_the_tests_fails() throws Exception {
        String classNameTest = challenge.getClassName()+"Test";
        TestChallenge.runCompilationTestJava(classNameTest);
        TestChallenge.runTestJava(classNameTest);
    }
    
    @Given("^the user sets the challenge score on (\\d+)$")
    public void the_user_sets_the_challenge_score_on(int arg1) throws Exception {
        challenge.setPoint(arg1);
    }
    
    @When("^the user submits the challenge$")
    public void the_user_submits_the_challenge() throws Exception {
        TestChallenge.validateTestChallenge(testChallenge);
    }
    
    @Then("^the system creates a new id for the challenge$")
    public void the_system_creates_a_new_id_for_the_challenge() throws Exception {
        // ???????????????????????????????????????????????????????
        throw new PendingException();
    }
    
    @Then("^saves the challenge$")
    public void saves_the_challenge() throws Exception {
        challenge.saveIt();
        testChallenge.saveIt();
        Challenge challAux = Challenge.findFirst("id = ?", challenge.getInteger("id"));
        TestChallenge testChallaux = TestChallenge.findFirst("id = ?", testChallenge.getInteger("id"));
        assertEquals(true, challAux != null && testChallaux != null);
    }
    
    //----------------------------------------------------------------------------------------------

    @Given("^the user \"([^\"]*)\" is already logged on$")
    public void the_user_is_already_logged_on(String arg1) throws Exception {
        user2 = User.findFirst("username = ?", arg1);
        assertEquals(true, User.userExistsByUsername(arg1));
    }
    
    @Given("^the type of the challenge is \"([^\"]*)\"$")
    public void the_type_of_the_challenge_is(String arg1) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    
    @Given("^the text of the challenge does not compile$")
    public void the_text_of_the_challenge_does_not_compile() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
     
}
