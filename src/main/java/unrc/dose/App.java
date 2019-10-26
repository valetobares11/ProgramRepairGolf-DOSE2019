package unrc.dose;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;

import java.io.IOException;
import java.util.Arrays;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

import com.beerboy.ss.SparkSwagger;
import spark.Service;

public class App
{
    public static void main( String[] args ) {
      Service spark = Service.ignite().port(55555);

      try {
        SparkSwagger
          .of(spark)
          .endpoints(() -> Arrays.asList(new BellyEndpoint()))
          .generateDoc();
      }
      catch(IOException e) {
        e.printStackTrace();
      }

      spark.before((request, response) -> {
        if (!Base.hasConnection()) {
          Base.open();
        }
      });

      spark.after((request, response) -> {
        if (Base.hasConnection()) {
          Base.close();
        }
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
