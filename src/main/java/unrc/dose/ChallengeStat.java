package unrc.dose;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

/*
* == Schema Info
*
* Table name: challenge_stats
*
*	id INTEGER auto_increment primary key,
*   challenge_id INTEGER not null,
*   solved_count INTEGER, //alter table
*   average_score INTEGER, //alter table
*   created_at DATETIME,
*	updated_at DATETIME
*
**/

public class ChallengeStat extends Model {

	/**
	 * Updates the number of times that a challenge has been solved.
	 *@param challengeId The id of the challenge that has been solved.
	 */
	public static void updateSolvedCount(int challengeId) {
		List <ChallengeStat> challengeSingleList=ChallengeStat.where("challenge_id == ?", challengeId);
		ChallengeStat challengeToUpdate=challengeSingleList.get(0);
		int currentSolvedCount=challengeToUpdate.get("solved_count");
		currentSolvedCount++;
		challengeToUpdate.set("solved_count", "currentSolvedCount");
		challengeToUpdate.saveIt();

	}

	/**
	 *Using the id of the last proposition submitted for a challenge, updates the average score obtained by all users who solved it.
	 *@param propositionId The id of the proposition submitted.
	 */
	public static void updateAverageScore(int propositionId) {
		//getting the info about the proposition
		Proposition solution=Proposition.findbyid(propositionId);
		int distance=solution.get("distance");
		int challengeId=solution.get("challenge_id");

		Challenge currentChallenge=Challenge.findbyid(challengeId);
		int challengePoints=currentChallenge.get("point");

		int userScore=challengePoints - distance;

		updateSolvedCount(challengeId);
		List <ChallengeStat> challengeSingleList=ChallengeStat.where("challenge_id == ?", challengeId);
		ChallengeStat challengeToUpdate=challengeSingleList.get(0);

		//getting the current average score and the current solved count
		float currentAverage=challengeToUpdate.get("average_score");
		int currentSolvedCount=challengeToUpdate.get("solved_count");

		float updatedAverageScore=currentAverage+((userScore - currentAverage) / currentSolvedCount)

		challengeToUpdate.set("average_score", "updatedAverageScore");
		challengeToUpdate.saveIt();

	}
}
