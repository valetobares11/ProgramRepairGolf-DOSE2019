package unrc.dose;

import org.javalite.activejdbc.Model;

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
