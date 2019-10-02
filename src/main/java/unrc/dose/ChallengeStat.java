/*
*	== Schema Info
*
*	Table name: challenge_stats
*   id integer auto_increment primary key,
*   challenge_id integer not null,
*
*/

package unrc.dose;

import org.javalite.activejdbc.Model;

public class ChallengeStat extends Model {
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
