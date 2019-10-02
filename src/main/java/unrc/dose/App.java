package unrc.dose;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.before;
import static spark.Spark.after;
import static spark.Spark.options;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.LazyList;

import unrc.dose.User;
import unrc.dose.Challenge;
import unrc.dose.TestChallenge;

public class App
{
    public static void main( String[] args ) {
      Base.open();
      Challenge.addChallenge(12,"---","hello", "cascsdavADF<SAFc",10,1,1);
      TestChallenge.addTestChallenge(1, "hello");
      TestChallenge p = new TestChallenge();
      p.setChallengeId(1);
      p.setTest("NNNfksdlandflasdfjasldfjld<");
      p.saveIt();
      Base.close();
      before((request, response) -> {
        Base.open();
      });

      after((request, response) -> {
        Base.close();
      });

      get("/hello/:name", (req, res) -> {
        return "hello" + req.params(":name");
      });

      get("/users", (req, res) -> {
        res.type("application/json");

        LazyList<User> users = User.findAll();

        return users.toJson(true, "username", "password");
      });
    }
}
