package unrc.dose;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import spark.Spark;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCucumberTest{
  @BeforeClass
  public static void beforeAll() {
    App.main(null);

    App.spark.awaitInitialization();

    Base.open();
    Base.openTransaction();
  }

  @AfterClass
  public static void tearDown() {
    Spark.stop();
    Base.rollbackTransaction();
    Base.close();
  }
}
