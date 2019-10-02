package unrc.dose;

/*

== Schema Info

	Table name: challenge_stats

    id 				:INTEGER auto_increment primary key
    challenge_id 	:INTEGER not null
    average_score 	:FLOAT
    solved_count 	:INTEGER 
    created_at 		:DATETIME
	updated_at 		:DATETIME

*/

import org.javalite.activejdbc.Model;

public class ChallengeStat extends Model {

	public 


	public int getNumberOfSolutions() {
		//TODO : Implement, this should return the number of valid solutions given to the challenge.
		return 0;
	}

	public int getNumberOfProposal() {
		//TODO :
		return 0;
	}

	public int getSuccessRate() {
		//TODO :
		return 0;
	}


}
