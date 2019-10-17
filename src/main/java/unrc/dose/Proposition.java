package unrc.dose;


import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import java.io.*;
import java.util.*;

/**
 * == Schema Info.
 *
 * Table name: proposition
 *
 * id              :integer(11)    not null, primary key
 * user_id         :integer(11)    not null,
 * challenge_id    :integer(11)    not null,
 * source          :text
 * isSubmit        :integer(11)
 * distance        :integer(11)
 * cantTestPassed  :integer(11)
 *
 **/

public class Proposition extends Model {

    /**
     *
     * @return id of user
     */
    public Integer getUserId() {
        return this.getInteger("user_id");
    }

    /**
     *
     * @return id of challenge
     */
    public Integer getChallengeId() {
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
    public Integer getIsSubmit() {
        return this.getInteger("isSubmit");
    }

    /**
     *
     * @return distance obtained from the proposed solution
     */
    public Integer getDistance() {
        return this.getInteger("distance");
    }

    /**
     *
     * @return amount of tests passed by the proposed solution
     */
    public Integer getCantTestPassed() {
        return this.getInteger("cantTestPassed");
    }

    /**
     * Sets a user id.
     * @param userId reference new user id for the proposition
     */
    public void setUserId(final Integer userId) {
        this.set("user_id", userId);
    }

    /**
     * Sets a challenge id.
     * @param challengeId reference new id challenge for the proposition
     */
    public void setChallengeId(final Integer challengeId) {
        this.set("challenge_id", challengeId);
    }

    /**
     * Sets a source's.
     * @param source reference new source for the proposition
     */
    public void setSource(final String source) {
        this.set("source", source);
    }

    /**
     * Set isSubmit value.
     * @param isSubmit reference new value for the isSubmit in the proposition
     */
    public void setIsSubmit(final Integer isSubmit) {
        this.set("isSubmit", isSubmit);
    }

    /**
     * Set distance value.
     * @param distance reference new distance in the proposition.
     */
    public void setDistance(final Integer distance) {
        this.set("distance", distance);
    }

    /**
     * Set cantTestPassed value.
     * @param testPassed reference the new amount of tests passed
     */
    public void setCantTestPassed(final Integer testPassed) {
        this.set("cantTestPassed", testPassed);
    }

    /**
     * Method for creates new proposition in the database.
     * @param userId reference the user for the new proposition.
     * @param challengeId reference the challenge for the new proposition.
     * @return the proposition created in the database.
     */
    public static Proposition newProposition(final int userId,
            final int challengeId) {

        Proposition newProposition = new Proposition();
        Challenge currentChallenge = Challenge.findById(challengeId);
        newProposition.set("user_id", userId);
        newProposition.set("challenge_id", challengeId);
        newProposition.set("source", currentChallenge.get("source"));
        newProposition.set("isSubmit", 0);
        newProposition.set("distance", Integer.MAX_VALUE);
        newProposition.set("cantTestPassed", 0);
        newProposition.saveIt();
        return newProposition;
    }

    /**
     * Get the solutions for a challenge by a user.
     * @param userId reference the id of a user
     * @param challengeId reference the id of a challenge
     * @return List of proposition for user in challenge
     */
    public static LazyList<Proposition> solutionsUserChallenge(
            final Integer userId, final Integer challengeId) {
        return Proposition.where("user_id = ? and challenge_id = ? "
                + "and isSubmit = ?", userId, challengeId, 1);
    }

    /**
     * Save a user's solution in a challenge.
     * @param sourceCurrent reference the new code of the proposed solution
     * @param distanceObtained reference the new distance obtained
     */
    public void saveSolution(final String sourceCurrent, final Integer distanceObtained) {
        this.set("source", sourceCurrent);
        this.set("distance", distanceObtained);
        this.set("isSubmit", 1);
    }

    /**
     * save proposed solution in a challenge
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
    public static LazyList<Proposition> getPropositionNoSubmit(final int userId,
            final int challengeId) {
        return Proposition.where("user_id = ? and challenge_id = ? and "
                + "isSubmit = ?", userId, challengeId, 0);
    }

    /**
     * This method find the proposition that is solution
     * for one specific challenge.
     * @param challengerId represent the challenge.
     * @return null if there is no solution for the challenge,
     * else one list with all solutions.
     */
    public static LazyList<Proposition> allSolChall(final int challengerId) {
        return Proposition.where("challenge_id = ? and isSubmit = ?",
                challengerId, 1);
    }

    /**
     * This method calculates the editing distance of a Proposition.
     * @param p represents a Proposition
     * @return the editing distance of both string
     */
    public static int getDistanceProposition(final Proposition p) {
        String str1 = p.getSource();
        Challenge challenge = Challenge.findById(p.get("challenge_id"));
        String str2 = challenge.getString("source");

        return computeLevenshteinDistance(str1, str2);
    }

    /**
     * This method calculates the distance of editing between two string.
     * @param str1 represents the original string
     * @param str2 represents the modified string
     * @return the editing distance of both string
     */
    public static int computeLevenshteinDistance(final String str1,
            final String str2) {
        return computeLevenshteinDistance(str1.toCharArray(),
                str2.toCharArray());
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
    private static int computeLevenshteinDistance(final char[] str1,
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
	 * Generate a .java file with the code of a challenge
	 * @param source text whith code java
	 */
	public static void generateFileJava(final String source) {

		File f;
		f = new File("Source.java");

		try {
			FileWriter w = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);

			wr.write(source);
			wr.close();
			bw.close();

		} catch (IOException e) {};
    }
    
    /**
	 * Compile a file .java
	 * @param source file .java
	 * @return 0 if the compilation is successful, 1 opposite case
	 * @throws Exception
	 */
	private static int compilar(final String archivoSourceJava) throws Exception { 
        
        int k = runProcess("javac " +  archivoSourceJava); 
		return k;
    }
    
    /**
	 * The code to be compiled into the buffer
	 * @param name param javac Nombre.java
	 * @param ins
	 * @throws Exception
	 */
	private static void printLines(final String name, final InputStream ins) throws Exception { 
        
        String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		while ((line = in.readLine()) != null) {
			System.out.println(name + " " + line);
		}
    }
    
    /**
	 * run the compilation process
	 * @param command param javac Nombre.java
	 * @return 0 if the compilation is successful, 1 opposite case
	 * @throws Exception
	 */
	private static int runProcess(String command) throws Exception { 
        
        Process pro = Runtime.getRuntime().exec(command);
		printLines(command + " stdout:", pro.getInputStream());
		printLines(command + " stderr:", pro.getErrorStream());
		pro.waitFor();
		return pro.exitValue(); 
	}

    /**
     * Compile a proposal
     * @return True if compiled, false otherwise
     */
    public boolean compileProposition() {
       
        generateFileJava(this.getSource());
        try {
			int k = compilar("Source.java");
			if (k == 0) {
                return true;
            } else {
                return false;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    }

}
