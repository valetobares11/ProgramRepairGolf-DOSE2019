package unrc.dose;

import static spark.Spark.get;

public class App 
{
    public static void main( String[] args ) {
      get("/hello/:name", (req, res) -> {
        return "hello" + req.params(":name");
      });
    }
}
