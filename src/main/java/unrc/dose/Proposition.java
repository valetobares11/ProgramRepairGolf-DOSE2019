package unrc.dose;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * == Schema Info.
 *
 * Table name: proposition
 *
 * id              :integer(11)    not null, primary key
 * user_id         :integer(11)    not null,
 * challenge_id    :integer(11)    not null,
 * source          :text
 * isSolution      :boolean
 * distance        :integer(11)
 * cantTestPassed  :integer(11)
 *
 **/

public class Proposition extends Model {

    /**
     *
     * @return id of user
     */
    public int getUserId() {
        return this.getInteger("user_id");
    }

    /**
     *
     * @return id of challenge
     */
    public int getChallengeId() {
        return this.getInteger("challenge_id");
    }

    /**
     *
     * @return code of the proposed solution
     */
    public String getSource() {
        return this.getString("source");
    }

    /**
     *
     * @return status of the proposed solution
     */
    public Boolean getIsSolution() {
        return this.getBoolean("isSolution");
    }

    /**
     *
     * @return distance obtained from the proposed solution
     */
    public int getDistance() {
        return this.getInteger("distance");
    }

    /**
     *
     * @return amount of tests passed by the proposed solution
     */
    public int getCantTestPassed() {
        return this.getInteger("cantTestPassed");
    }

    /**
     * Sets a user id.
     * @param userId reference new user id for the proposition
     */
    public void setUserId(final int userId) {
        this.set("user_id", userId);
    }

    /**
     * Sets a challenge id.
     * @param challengeId reference new id challenge for the proposition
     */
    public void setChallengeId(final int challengeId) {
        this.set("challenge_id", challengeId);
    }

    /**
     * Sets a source's.
     * @param source reference new source for the proposition
     */
    public void setSource(final String source) {
        if (source == null) {
            throw new IllegalArgumentException();
        }
        this.set("source", source);
    }

    /**
     * Set isSolution value.
     * @param isSolution reference new value
     * for the isSolution in the proposition
     */
    public void setIsSolution(final boolean isSolution) {
        this.set("isSolution", isSolution);
    }

    /**
     * Set distance value calculated previus.
     * @param distance reference new distance in the proposition.
     */
    public void setDistance(final int distance) {
        this.set("distance", distance);
    }

    /**
     * Set cantTestPassed value.
     * @param testPassed reference the new amount of tests passed
     */
    public void setCantTestPassed(final int testPassed) {
        this.set("cantTestPassed", testPassed);
    }

    /**
     * Method for creates new proposition in the database.
     * @param userId reference the user for the new proposition.
     * @param challengeId reference the challenge for the new proposition.
     * @return the proposition created in the database.
     */
    public static Proposition newProposition(
            final int userId,
            final int challengeId) {

        Challenge currentChallenge = Challenge.findById(challengeId);
        User currentUser = User.findById(userId);

        if (currentChallenge == null || currentUser == null) {
            throw new IllegalArgumentException();
        }

        Proposition newProposition = new Proposition();
        newProposition.set("user_id", currentUser.getInteger("id"));
        newProposition.set("challenge_id", currentChallenge.getInteger("id"));
        newProposition.set("source", currentChallenge.get("source"));
        newProposition.set("isSolution", false);
        newProposition.set("distance", 0);
        newProposition.set("cantTestPassed", 0);
        newProposition.saveIt();
        return newProposition;
    }

    /**
     * Method for creates new proposition in the database.
     * @param userId represent the id from user
     * @param challengeId represent the id from challenge
     * @param source represent the code fron the new proposition
     * @param solution represent if one proposition is solution or not
     * @param distance represent the distance from the new proposition
     * @param cantTest represent the quantity of test passed
     * @return the new proposition created
     */
    public static Proposition newProposition(
        final int userId,
        final int challengeId,
        final String source,
        final boolean solution,
        final int distance,
        final int cantTest) {

        Proposition newProp = new Proposition();
        newProp.setUserId(userId);
        newProp.setChallengeId(challengeId);
        newProp.setSource(source);
        newProp.setIsSolution(solution);
        newProp.setDistance(distance);
        newProp.setCantTestPassed(cantTest);
        newProp.saveIt();
        return newProp;
    }

    /**
     * This method search one proposition for id.
     * @param idProp represent the id from proposition
     * @return one Proposition with id = idProp
     */
    public static Proposition searchByIdProposition(final int idProp) {
        return (Proposition.findById(idProp));
    }

    /**
     * Get the solutions for a challenge by a specific user..
     * @param userId reference the id of a user
     * @param challengeId reference the id of a challenge
     * @return List of proposition for user in challenge
     */
    public static LazyList<Proposition> getChallengeSolutionsByUser(
            final int userId, final int challengeId) {
        return Proposition.where("user_id = ? and challenge_id = ? "
                + "and isSolution = ?", userId, challengeId, true);
    }

    /**
     * This method return a list of solutions for one user.
     * @param userId represent the Id from user
     * @return a list of propositions
     */
    public static LazyList<Proposition> getSolutionsFromAUser(
        final int userId) {
        return Proposition.where(
                "user_id = ? and isSolution = ?", userId, true);
    }

    /**
     * Save a user's solution in a challenge.
     * @param proposedCode reference the new code of the proposed solution
     * @param distanceObtained reference the new distance obtained
     */
    public void saveSolution(
            final String proposedCode,
            final int distanceObtained) {
        this.setSource(proposedCode);
        this.set("distance", distanceObtained);
        this.set("isSolution", true);
    }

    /**
     * save proposed solution in a challenge.
     * @param sourceCurrent reference the new code of the proposed solution
     */
    public void saveProposedSolution(final String sourceCurrent) {
        this.set("source", sourceCurrent);
    }

    /**
     * This method find the proposition that is not a solution yet.
     * @param userId represents the user
     * @param challengeId represent the challenge
     * @return null if there is no proposition or the proposition,
     *  or the proposition found.
     */
    public static LazyList<Proposition> getUnsubmittedChallengePropByUser(
            final int userId,
            final int challengeId) {
        return Proposition.where("user_id = ? and challenge_id = ? and "
                + "isSolution = ?", userId, challengeId, false);
    }

    /**
     * This method find the proposition that is solution
     * for one specific challenge.
     * @param challengerId represent the challenge.
     * @return list the solution for one challenge.
     */
    public static LazyList<Proposition> getAllSolutionsForChallenge(
            final int challengerId) {
        return Proposition.where("challenge_id = ? and isSolution = ?",
                challengerId, true);
    }

    /**
     * This method calculates the editing distance of a Proposition.
     * @param p represents a Proposition
     * @return the editing distance of both string
     */
    public static int getDistanceProposition(final Proposition p) {
        String proposedCode = p.getSource();
        Challenge challenge = Challenge.findById(p.get("challenge_id"));
        String challengerCode = challenge.getString("source");

        return computeLevenshteinDistance(proposedCode, challengerCode);
    }

    /**
     * This method calculates the distance of editing between two string.
     * @param str1 represents the original string
     * @param str2 represents the modified string
     * @return the editing distance of both string
     */
    public static int computeLevenshteinDistance(
            final String str1,
            final String str2) {
        return computeLevenshteinDistance(
                str1.toCharArray(),
                str2.toCharArray()
                );
    }

    /**
     * Get the minimum between three characters.
     * @param a represent a integer value
     * @param b represent a integer value
     * @param c represent a integer value
     * @return represent the minimum value.
     */
    private static int minimum(final int a, final int b, final int c) {
        if (a <= b && a <= c) {
            return a;
        }
        if (b <= a && b <= c) {
            return b;
        }
        return c;
    }

    /**
     * This method calculates the distance of editing
     * between two character arrangements.
     * @param str1 represents the original arrangement
     * @param str2 represents the modified arrangement
     * @return the editing distance of both arrangements
     */
    private static int computeLevenshteinDistance(
            final char[] str1,
            final char[] str2) {
        int[][] distance = new int[str1.length + 1][str2.length + 1];

        for (int i = 0; i <= str1.length; i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= str2.length; j++) {
            distance[0][j] = j;
        }
        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                distance[i][j] = minimum(distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1, distance[i - 1][j - 1]
                                + ((str1[i - 1] == str2[j - 1]) ? 0 : 1));
            }
        }
        return distance[str1.length][str2.length];
    }

    /**
     * Generate a .java file with the code of a challenge.
     * @param source text with code java
     * @param className text with name of archive
     */
    public static void generateFileJava(
            final String source, final String className) {

        File f;
        String nameFile = "/../tmp/" + className + ".java";
        f = new File(nameFile);

        try {
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(source);
            wr.close();
            bw.close();
        } catch (IOException e) {
            System.out.println("File exception");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Compile a file .java.
     * @param archivoSourceJava file .java
     * @return 0 if the compilation is successful, 1 opposite case
     * @throws Exception if an error
     */
    private static int compilar(
            final String archivoSourceJava) throws Exception {
        int k = runProcess("javac /../tmp/" + archivoSourceJava);
        return k;
    }

    /**
     * The code to be compiled into the buffer.
     * @param name parameter javac Nombre.java
     * @param ins parameter for buffer
     * @throws Exception if an error
     */
    private static void printLines(
            final String name, final InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
    }

    /**
     * run the compilation process.
     * @param command java Nombre.java
     * @return 0 if the compilation is successful, 1 opposite case
     * @throws Exception if an error
     */
    private static int runProcess(final String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        return pro.exitValue();
    }

    /**
     * Compile a proposal.
     * @param proposedCode represents the code for the possible solution.
     * @param proposedClassName class name to compile
     * @return True if compiled, false otherwise
     */
    public boolean compileProposition(
            final String proposedCode,
            final String proposedClassName) {
        this.setSource(proposedCode);
        this.saveIt();
        generateFileJava(proposedCode, proposedClassName);
        try {
            int k = compilar(proposedClassName + ".java");
            if (k == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method saves the proposition committed,
     * change distance and isSolution 1.
     * @param proposedCode represents the possible
     * solution provided by a user
     * @param proposedClassName class name to compile
     * @return false if the source not compile, true otherwise.
     */
    public boolean submitProposition(
            final String proposedCode,
            final String proposedClassName) {
        if (!(this.compileProposition(proposedCode, proposedClassName))) {
            return false;
        }
        Integer newDistance = getDistanceProposition(this);
        this.set("distance", newDistance);
        this.set("isSolution", true);
        this.saveIt();
        return true;
    }

    /**
     * This method return the best solution of a user
     * in a specific challenge.
     * @param challengeId represent the challenge
     * @param userId represents the user
     * @return the proposition that have the best distance of a user
     * in a specific challenge
     */
    public static Proposition bestPropDistance(
            final int userId, final int challengeId) {

        LazyList<Proposition> list = getChallengeSolutionsByUser(
                userId, challengeId);
        int min = Integer.MAX_VALUE;
        Proposition result = new Proposition();
        for (Proposition i : list) {
            if (i.getDistance() <= min) {
                min = i.getDistance();
                result = i;
            }
        }
        return result;
    }

    /**
    * This method passes the parameters of a proposition in a map.
    * @return the parameters of proposition
    */
    public Map<String, Object> getMapProposition() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("propo_id", this.getId());
        m.put("user_Id", this.getUserId());
        m.put("challenge_id", this.getChallengeId());
        m.put("source_id", this.getSource());
        m.put("isSolution", this.getIsSolution());
        m.put("distance", this.getDistance());
        m.put("cantTestPassed", this.getCantTestPassed());
        return m;
    }

    /**
     * This method transforms a list of prepositions into a list of maps.
     * @param list represent a list of propositions
     * @return a list of maps
     */
    public static List<Map<String, Object>> listJson(
            final LazyList<Proposition> list) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> currentMap;
        if (list.isEmpty()) {
            return result;
        }
        for (Proposition prop : list) {
            currentMap = prop.getMapProposition();
            result.add(currentMap);
        }
        return result;
    }


}
