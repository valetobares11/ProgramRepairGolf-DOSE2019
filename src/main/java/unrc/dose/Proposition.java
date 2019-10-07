package unrc.dose;


import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import java.util.*;
import org.javalite.activejdbc.LazyList;

/*
* == Schema Info
*
* Table name: proposition
*
*  id              :integer(11)    not null, primary key
*  user_id         :integer(11)    not null,
*  challenge_id    :integer(11)    not null,
*  source          :text
*  isSubmit        :integer(11)
*  distance        :integer(11)
*  cantTestPassed  :integer(11)
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
     * 
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.set("user_id", userId);
    }

    /**
     * 
     * @param challengeId
     */
    public void setChallengeId(Integer challengeId) {
        this.set("challenge_id", challengeId);
    }

    /**
     * 
     * @param source
     */
    public void setSource(String source) {
        this.set("source", source);
    }

    /**
     * 
     * @param isSubmit
     */
    public void setIsSubmit(Integer isSubmit) {
        this.set("isSubmit", isSubmit);
    }

    /**
     * 
     * @param distance
     */
    public void setDistance(Integer distance) {
        this.set("distance", distance);
    }

    /**
     * 
     * @param cantTestPassed
     */
    public void setCantTestPassed(Integer cantTestPassed) {
        this.set("cantTestPassed", cantTestPassed);
    }

    /**
     * Get the solutions for a challenge by a user
     * 
     * @param userId
     * @param challengeId
     * @return List of proposition for user in challenge
     */
    public LazyList<Proposition> solutionsForUserInChallenge(Integer userId, Integer challengeId) {
        return Proposition.where("user_id = ? and challenge_id = ? and isSubmit = ?", userId, challengeId, 1);
    }

    /**
     * Save a user's solution in a challenge
     * 
     * @param sourceCurrent
     * @param distanceObtained
     */
    public void saveSolution(String sourceCurrent, Integer distanceObtained) {
        this.set("source", sourceCurrent);
        this.set("distance", distanceObtained);
        this.set("isSubmit", 1);
    }
 


    
    /**
     * This method find the proposition that is not a solution yet.
     * @param userId represents the user
     * @param challengeId represent the challenge
     * @return null if there is no proposition or the proposition, or the proposition found.
     */
    public static LazyList<Proposition> getPropositionNoSubmit(int userId, int challengeId) {
    	
    	return Proposition.where("user_id = ? and challenge_id = ? and isSubmit = ?", userId, challengeId, 0);
    
    }
    
    /**
     * This method find the proposition that is solution for one specific challenge.
     * @param challengerId represent the challenge.
     * @return null if there is no solution for the challenge, else one list with all solutions.
     */
    public static LazyList<Proposition> allSolutionChallenge(int challengerId) {
    	
    	return Proposition.where("challenge_id = ? and isSubmit = ?", challengerId, 1);
    
    }
    /**
     * This method calculates the editing distance of a Proposition
     * @param p represents a Proposition
     * @return the editing distance of both string
     */
    public static int getDistanceProposition(Proposition p) {
    	String str1 = p.getSource();
    	Challenge challenge = Challenge.findById(p.get("challenge_id"));
    	String str2 = challenge.getString("source");
    	
    	return computeLevenshteinDistance(str1, str2);
    }
    
	/**
	 * This method calculates the distance of editing between two string
	 * @param str1 represents the original string
	 * @param str2 represents the modified string
	 * @return the editing distance of both string
	 */
    public static int computeLevenshteinDistance(String str1, String str2) {        
    	return computeLevenshteinDistance(str1.toCharArray(),
                                          str2.toCharArray());
    }

	/**
	 * get the minimum between three characters
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
    private static int minimum(int a, int b, int c) {
        if (a<=b && a<=c) {
            return a;
        } 
        if (b<=a && b<=c) {
            return b;
        }
        return c;
    }
 	
    /**
     * This method calculates the distance of editing between two character arrangements
     * @param str1 represents the original arrangement
     * @param str2 represents the modified arrangement
     * @return the editing distance of both arrangements
     */
 	private static int computeLevenshteinDistance(char [] str1, char [] str2) {
        int [][]distance = new int[str1.length+1][str2.length+1];
 
        for (int i=0;i<=str1.length;i++) {
                distance[i][0]=i;
        }
        for (int j=0;j<=str2.length;j++) {
                distance[0][j]=j;
        }
        for (int i=1;i<=str1.length;i++) {
            for (int j=1;j<=str2.length;j++) { 
                  distance[i][j]= minimum(distance[i-1][j]+1,
                                        distance[i][j-1]+1,
                                        distance[i-1][j-1]+
                                        ((str1[i-1]==str2[j-1])?0:1));
            }
        }
        return distance[str1.length][str2.length];
    }
}
