package unrc.dose;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import spark.Spark;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCucumberTest {
  @BeforeClass
  public static void beforeAll() {
    App.main(null);

    App.spark.awaitInitialization();

    Base.open();
    Base.openTransaction();
    //
    User user = User.set("Pablo", "root", "pablo@gmail.com", false);
    User admin = User.set("admin", "root", "admin@gmail.com", true);
    Challenge ch = Challenge.addChallenge(admin.getInteger("id"), "challenge",
        "Test", "descripcion", "codigoooooo", 10, user.getInteger("id"));
    Challenge aux = Challenge.findFirst("title=?","challenge");
    user = User.set("Pedro","root","pedrito@gmail.com",false);
    Comment c = Comment.createComment("comment","this is a description", ch.getInteger("id"),user.getInteger("id"));
  }

  @AfterClass
  public static void tearDown() {
    Spark.stop();
    Base.rollbackTransaction();
    Base.close();
  }
}
