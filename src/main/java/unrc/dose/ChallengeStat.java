package unrc.dose;

import org.javalite.activejdbc.Model;

import java.util.List;

/*
* == Schema Info
*
* Table name: challenge_stats
*
*  id INTEGER auto_increment primary key,
*  challenge_id INTEGER not null,
*  solved_count INTEGER, //alter table
*  average_score INTEGER, //alter table
*  created_at DATETIME,
*  updated_at DATETIME
*
**/

/**
* ChallengeStat class represents a person into the system.
*/
public class ChallengeStat extends Model {

    /**
     * Updates the number of times that a challenge has been solved.
     *@param challengeSToupdate The challenge stat which contains the current
     number of solutions.
     *@return Returns the updated solved count.
     */
private static int incrementSolvedCount(
        final ChallengeStat challengeSToupdate) {
        int currentSolvedCount = (int) challengeSToupdate.get("solved_count");
        currentSolvedCount++;
        return currentSolvedCount;
    }

    /**
     *Using the id of the last proposition submitted for a challenge, updates
     the average score obtained by all users who solved it.
     *@param propositionId The id of the proposition submitted.
     */
    public static void updateAverageScore(final int propositionId) {
        //getting the info about the proposition
        Proposition solution = Proposition.findById(propositionId);
        int distance = (int) solution.get("distance");
        int challengeId = (int) solution.get("challenge_id");

        Challenge currentChallenge = Challenge.findById(challengeId);
        int challengePoints = (int) currentChallenge.get("point");

        int userScore = challengePoints - distance;

        List<ChallengeStat> challengeSingleList = ChallengeStat.where(
        "challenge_id == ?", challengeId);
        ChallengeStat challengeSToupdate = challengeSingleList.get(0);

        //getting the current average score and the current solved count
        float currentAverage = (float) challengeSToupdate.get("average_score");
        int currentSolvedCount = incrementSolvedCount(challengeSToupdate);

        float updatedAverageScore = currentAverage
        + ((userScore - currentAverage) / currentSolvedCount);

        challengeSToupdate.set("average_score", "updatedAverageScore");
        challengeSToupdate.saveIt();

    }

    /**
     *Generates a new ChallengeStat table for a incoming new challenge.
     *@param challengeId The id of the new challenge.
     */
    public static void newChallengeStat(final int challengeId) {
        ChallengeStat c = ChallengeStat.createIt(
        "challenge_id", challengeId, "average_score", 0, "solved_count", 0);
    }
}
