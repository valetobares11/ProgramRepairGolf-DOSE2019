package unrc.dose;

import java.util.List;

import org.javalite.activejdbc.Model;

/**
* == Schema Info
*
* Table name: challenge_stats
*
*  id              :int(11)    not null, primary key,
*  challenge_id    :int(11)    not null,
*  created_at      :datetime,
*  updated_at      :datetime,
*  average_score   :float,
*  solved_count    :int(11)
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
        "challenge_id = ?", challengeId);
        ChallengeStat challengeSToupdate = challengeSingleList.get(0);

        //getting the current average score and the current solved count
        float currentAverage = (float) challengeSToupdate.get("average_score");
        int currentSolvedCount = incrementSolvedCount(challengeSToupdate);

        float updatedAverageScore = currentAverage
        + ((userScore - currentAverage) / currentSolvedCount);

        challengeSToupdate.set("average_score", updatedAverageScore);
        challengeSToupdate.saveIt();

    }

    /**
     *Generates a new ChallengeStat table for a incoming new challenge.
     *@param challengeId The id of the new challenge.
     *@return the challenge stat created in the database.
     */
    public static ChallengeStat newChallengeStat(final int challengeId) {
        validatePresenceOf("challenge_id");
        ChallengeStat c = ChallengeStat.createIt(
        "challenge_id", challengeId, "average_score", 0, "solved_count", 0);

        return c;
    }

    public static ChallengeStat getChallengeStat(final int challengeId) {
        return (ChallengeStat.findFirst("challenge_id = ? ", challengeId));
    }

    /**
     *Compares ChallengeStat objects.
     *@param cs the ChallengeStat Object to compare.
     *@return the boolean result of comparing each field of the ChallengeStat Objects.
     */
    @Override
    public boolean equals (Object cs) {
        ChallengeStat cs2 = (ChallengeStat) cs;

        return ((this.getInteger("id") == cs2.getInteger("id")) &&
        (this.get("challenge_id") == cs2.get("challenge_id")) &&
        ((float) this.get("average_score") == (float) cs2.get("average_score")) &&
        (this.get("solved_count") == cs2.get("solved_count")));
    }
}
