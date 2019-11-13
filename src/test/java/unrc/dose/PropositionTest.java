package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PropositionTest {

    @BeforeClass
    public static void before() {
        if (!Base.hasConnection()) {
            Base.open();
            System.out.println("PropositionTest setup");
            Base.openTransaction();
        }

        User admin = User.set("admin", "admin", "admin@gmail.com", false);
        User.set("player1", "abc123", "player1@gmail.com", false);
        User.set("player2", "abc123", "player2@gmail.com", false);
        Challenge.addChallenge(admin.getInteger("id"), "challenge1", "Challenge1", "challenge1", "", 0, 100);
        Challenge.addChallenge(admin.getInteger("id"), "challenge2", "Challenge2", "challenge1", "", 0, 100);

    }

    @AfterClass
    public static void tearDown() {
        if (Base.hasConnection()) {
            System.out.println("PropositionTest tearDown");
            Base.rollbackTransaction();
            Base.close();
        }
    }

    @Test
    public void getUserIdTest() {

        Proposition proposition = new Proposition();
        proposition.set("user_id", 2);
        int userId = proposition.getUserId();
        assertEquals(userId, 2);

    }

    @Test
    public void getChallengeIdTest() {

        Proposition proposition = new Proposition();
        proposition.set("challenge_id", 2);
        int challengeId = proposition.getChallengeId();
        assertEquals(challengeId, 2);

    }

    @Test
    public void getSourceTest() {

        Proposition proposition = new Proposition();
        proposition.set("source", "hola mundo;");
        String source = proposition.getSource();
        assertEquals(source, "hola mundo;");

    }

    @Test
    public void getisSolutionTest() {

        Proposition proposition = new Proposition();
        proposition.set("isSolution", true);
        boolean isSolution = proposition.getIsSolution();
        assertEquals(isSolution, true);

    }

    @Test
    public void getDistanceTest() {

        Proposition proposition = new Proposition();
        proposition.set("distance", 1);
        int distance = proposition.getDistance();
        assertEquals(distance, 1);

    }

    @Test
    public void getCantTestPassedTest() {

        Proposition proposition = new Proposition();
        proposition.set("cantTestPassed", 1);
        int cantTestPassed = proposition.getCantTestPassed();
        assertEquals(cantTestPassed, 1);

    }

    @Test
    public void setUserIdTest() {

        Proposition proposition = new Proposition();
        proposition.setUserId(1);
        assertEquals(proposition.get("user_id"), 1);

    }

    @Test
    public void setChallengeIdTest() {

        Proposition proposition = new Proposition();
        proposition.setChallengeId(1);
        assertEquals(proposition.get("challenge_id"), 1);

    }


    @Test
    public void setSourceTest() {

        Proposition proposition = new Proposition();
        proposition.setSource("hola mundo;");
        assertEquals(proposition.get("source"), "hola mundo;");

    }

    @Test(expected=IllegalArgumentException.class)
    public void setSourceNullTest() {
        Proposition proposition = new Proposition();
        String code = null;
        proposition.setSource(code);
    }


    @Test
    public void setisSolutionTest() {

        Proposition proposition = new Proposition();
        proposition.setIsSolution(true);
        assertEquals(proposition.get("isSolution"), true);

    }

    @Test
    public void setDistanceTest() {

        Proposition proposition = new Proposition();
        proposition.setDistance(1);
        assertEquals(proposition.get("distance"), 1);

    }

    @Test
    public void setCantTestPassedTest() {

        Proposition proposition = new Proposition();
        proposition.setCantTestPassed(1);
        assertEquals(proposition.get("cantTestPassed"), 1);

    }

    @Test
    public void newPropositionTest() {

        int challengeId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");
        int playerId = User.findFirst("username = ?", "player1").getInteger("id");

        Proposition newProp = Proposition.newProposition(playerId, challengeId);
        LazyList<Proposition> listProp = Proposition.where("user_id = ? and challenge_id = ?", playerId, challengeId);
        Proposition propDB = listProp.get(0);

        assertEquals(newProp.getInteger("id"), propDB.getInteger("id"));

        Proposition.deleteAll();
    }
    
    @Test
    public void newPropositionTest2() {
        int challengeId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");
        int playerId = User.findFirst("username = ?", "player1").getInteger("id");
        String source = "hola mundo";
        boolean solution = false;
        int distance = 5;
        int testPassed = 0;

        Proposition newProposition = Proposition.newProposition(playerId, challengeId, source, solution, distance, testPassed);
        LazyList<Proposition> listProp = Proposition.where("user_id = ? and challenge_id = ?", playerId, challengeId);
        Proposition propDb =listProp.get(0);

        assertEquals(newProposition.getInteger("id"), propDb.getInteger("id"));
        Proposition.deleteAll();
    }

    @Test
    public void searchByIdPropositionTest() {
        
        int playerId = User.findFirst("username = ?", "player1").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");

        Proposition p1 = new Proposition();
        p1.set("challenge_id", challId);
        p1.set("user_id", playerId);
        p1.set("source", "System.out.println('Hello World');");
        p1.set("isSolution", true);
        p1.set("distance", Integer.MAX_VALUE);
        p1.set("cantTestPassed", 0);
        p1.saveIt();

        int idProp = p1.getInteger("id");
        
        assertTrue(Proposition.searchByIdProposition(idProp).getInteger("id") == idProp);
        Proposition.deleteAll();
    }

    @Test 
    public void solutionsForUserInChallengeTest() {

        int playerId = User.findFirst("username = ?", "player1").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");

        Proposition p1 = new Proposition();
        p1.set("challenge_id", challId);
        p1.set("user_id", playerId);
        p1.set("source", "System.out.println('Hello World');");
        p1.set("isSolution", true);
        p1.set("distance", Integer.MAX_VALUE);
        p1.set("cantTestPassed", 0);
        p1.saveIt();

        Proposition p2 = new Proposition();
        p2.set("challenge_id", challId);
        p2.set("user_id", playerId);
        p2.set("source", "System.out.println('Hello World');");
        p2.set("isSolution", true);
        p2.set("distance", Integer.MAX_VALUE);
        p2.set("cantTestPassed", 0);
        p2.saveIt();

        LazyList<Proposition> propositions = Proposition.getChallengeSolutionsByUser(playerId, challId);
        assertEquals(2, propositions.size());
        Proposition.deleteAll();
    }

    @Test
    public void getSolutionFromAUserTest() {
        int playerId1 = User.findFirst("username = ?", "player1").getInteger("id");
        int playerId2 = User.findFirst("username = ?", "player2").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");

        Proposition p1 = new Proposition();
        p1.set("challenge_id", challId);
        p1.set("user_id", playerId1);
        p1.set("source", "System.out.println('Hello World');");
        p1.set("isSolution", true);
        p1.set("distance", Integer.MAX_VALUE);
        p1.set("cantTestPassed", 0);
        p1.saveIt();

        Proposition p2 = new Proposition();
        p2.set("challenge_id", challId);
        p2.set("user_id", playerId2);
        p2.set("source", "System.out.println('Hello World');");
        p2.set("isSolution", true);
        p2.set("distance", Integer.MAX_VALUE);
        p2.set("cantTestPassed", 0);
        p2.saveIt();

        LazyList<Proposition> propositions = Proposition.getSolutionsFromAUser(playerId2);
        assertEquals(1, propositions.size());
        Proposition.deleteAll();        
    }

    @Test
    public void saveSolutionTest() {

        int playerId = User.findFirst("username = ?", "player1").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");

        Proposition p1 = new Proposition();
        p1.set("challenge_id", challId);
        p1.set("user_id", playerId);
        p1.set("source", "System.out.println('Hello World');");
        p1.set("isSolution", false);
        p1.set("distance", Integer.MAX_VALUE);
        p1.set("cantTestPassed", 0);
        p1.saveIt();

        p1.saveSolution("hola mundo;", 10);

        boolean isSolution = p1.getIsSolution();

        assertEquals(true, isSolution);

        Proposition.deleteAll();
    }

    @Test
    public void nullGetPropositionNoSubmitTest() {

        assertTrue(Proposition.getUnsubmittedChallengePropByUser(0, 0).isEmpty());

    }

    @Test
    public void notNullGetPropositionNoSubmitTest() {

        int playerId = User.findFirst("username = ?", "player1").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");

        Proposition p = new Proposition();
        p.set("challenge_id", challId);
        p.set("user_id", playerId);
        p.set("source", "System.out.println('Hello World');");
        p.set("isSolution", false);
        p.set("distance", Integer.MAX_VALUE);
        p.set("cantTestPassed", 0);
        p.saveIt();

        LazyList<Proposition> proposition = Proposition.getUnsubmittedChallengePropByUser(playerId, challId);  		
        assertEquals(1, proposition.size());
        Proposition.deleteAll();		
    }

    @Test
    public void nullAllSolutionChallengeTest() {
        assertTrue(Proposition.getAllSolutionsForChallenge(0).isEmpty());
    }

    @Test
    public void allSolutionChallengeTest() {

        int player1 = User.findFirst("username = ?", "player1").getInteger("id");
        int player2 = User.findFirst("username = ?", "player2").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");
        Proposition solution1 = new Proposition();
        Proposition solution2 = new Proposition();

        solution1.set("user_id", player1);
        solution1.set("challenge_id", challId);
        solution1.set("source", "Hola mundo!!;");
        solution1.set("isSolution", true);
        solution2.set("user_id", player2);
        solution2.set("challenge_id", challId);
        solution2.set("source", "Hola mundo!!;");
        solution2.set("isSolution", true);
        solution1.saveIt();
        solution2.saveIt();

        LazyList<Proposition> allSolution = Proposition.getAllSolutionsForChallenge(challId);

        assertEquals(2,allSolution.size());
        Proposition.deleteAll();
    }

    @Test
    public void sameDistance() {
        String str1 = "hola";
        String str2 = "";

        assertEquals(4, Proposition.computeLevenshteinDistance(str1, str2));
    }

    @Test
    public void distanceZero() {
        String str1 = "hola";
        String str2 = "hola";

        assertEquals(0, Proposition.computeLevenshteinDistance(str1, str2));
    }

    @Test
    public void getDistancePTotal() {

        int playerId = User.findFirst("username = ?", "player1").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");

        Proposition p = new Proposition();
        p.set("challenge_id", challId);
        p.set("user_id", playerId);
        p.set("source", "hello");
        p.set("isSolution", false);
        p.set("distance", 5);
        p.set("cantTestPassed", 0);
        p.saveIt();

        assertEquals(5, Proposition.getDistanceProposition(p));
        Proposition.deleteAll();
    }

    @Test
    public void getDistancePZero() {

        int playerId = User.findFirst("username = ?", "player1").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");

        Proposition p = new Proposition();
        p.set("challenge_id", challId);
        p.set("user_id", playerId);
        p.set("source", "");
        p.set("isSolution", false);
        p.set("distance", 0);
        p.set("cantTestPassed", 0);
        p.saveIt();

        assertEquals(0, Proposition.getDistanceProposition(p));
        Proposition.deleteAll();
    }

    @Test
	  public void notSubmitPropositionTest() {

	      int playerId = User.findFirst("username = ?", "player1").getInteger("id");
	      int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");
          String nameClass = Challenge.findFirst("title = ?", "challenge1").getString("class_name");
	      
	      Proposition p = new Proposition();
	      p.set("challenge_id", challId);
	      p.set("user_id", playerId);
	      p.set("isSolution", false);
	      p.set("distance", 0);
	      p.set("cantTestPassed", 0);
		  p.saveIt();
		  String newCode = "public class Challenge1 {public static void main(String[] args) {int a = 2; int b = 2; int c = a + b System.out.println(c);}}";
	      
	      assertFalse(p.submitProposition(newCode, nameClass));
	      assertTrue(p.getBoolean("isSolution") == false);
	      
	      Proposition.deleteAll();      
	  }
	  
	  @Test
	  public void submitPropositionTest() {
          
          int playerId = User.findFirst("username = ?", "player1").getInteger("id");
          int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");
          String nameClass = Challenge.findFirst("title = ?", "challenge1").getString("class_name");
          
          Proposition p = new Proposition();
          p.set("challenge_id", challId);
          p.set("user_id", playerId);
          p.set("isSolution", false);
          p.set("distance", 0);
          p.set("cantTestPassed", 0);
		  p.saveIt();
		  String newCode = "public class Challenge1 {public static void main(String[] args) {int a = 2; int b = 2; int c = a + b; System.out.println(c);}}";
          
          assertTrue(p.submitProposition(newCode, nameClass));
          assertTrue(p.getBoolean("isSolution") == true);
          
          Proposition.deleteAll();	      
	  }
	  
   
    
    @Test
    public void minDistanceTest() {

        int playerId = User.findFirst("username = ?", "player1").getInteger("id");
        int challId = Challenge.findFirst("title = ?", "challenge1").getInteger("id");

        Proposition p = new Proposition();
        p.set("challenge_id", challId);
        p.set("user_id", playerId);
        p.set("source", "hello");
        p.set("isSolution", true);
        p.set("distance", 0);
        p.set("cantTestPassed", 0);
        p.saveIt();

        Proposition p2 = new Proposition();
        p2.set("challenge_id", challId);
        p2.set("user_id", playerId);
        p2.set("source", "");
        p2.set("isSolution", true);
        p2.set("distance", 4);
        p2.set("cantTestPassed", 0);
        p2.saveIt();

        assertEquals(p.getInteger("id"), (Proposition.bestPropDistance(playerId, challId)).getInteger("id"));
        Proposition.deleteAll();
    }
}
