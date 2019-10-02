package unrc.dose;
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
import org.javalite.activejdbc.Model;

public class ChallengeStat extends Model {

	public static float getAverageScore(int challengeId) {
		//Getting the score of currentChallenge
		Challenge currentChallenge=Challenge.findbyid(challengeId);
		int challengeScore=currentChallenge.get("point");

		//Getting all distances from users who solved currentChallenge
		List <Proposition> allPropositions=Proposition.where("challenge_id == ?", challengeId);
		float sum=0;
		Proposition aux;

		for(int i=0; i<allPropositions.size(); i++){
			aux=allPropositions.get(i);
			sum+=challengeScore-(aux.get("distance"));
		}

		return sum/allPropositions.size();
	}


	public static void updateSolvedCount(int challengeId) {
		List <ChallengeStat> challengeSingleList=ChallengeStat.where("challenge_id == ?", challengeId);
		ChallengeStat challengeToUpdate=challengeSingleList.get(0);
		int currentSolvedCount=challengeToUpdate.get("solved_count");
		currentSolvedCount++;
		challengeToUpdate.set("solved_count", "currentSolvedCount");
		challengeToUpdate.saveIt();

	}

	public static void updateAverageScore(int challengeId, int newScore) {
			

		List <ChallengeStat> challengeSingleList=ChallengeStat.where("challenge_id == ?", challengeId);
		ChallengeStat challengeToUpdate=challengeSingleList.get(0);
		
		//getting the current average score and the current solved count
		float currentAverage=challengeToUpdate.get("average_score");
		int currentSolvedCount=challengeToUpdate.get("solved_count");
		
		float updatedAverageScore=currentAverage+((newScore-currentAverage)/currentSolvedCount)

		challengeToUpdate.set("average_score", "updatedAverageScore");
		challengeToUpdate.saveIt();

	}
}
	