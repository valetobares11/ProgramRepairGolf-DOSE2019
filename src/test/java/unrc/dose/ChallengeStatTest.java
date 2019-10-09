package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import unrc.dose.ChallengeStat;


public class ChallengeStatTest {

    @BeforeClass
	public static void before(){
		Base.open();
		Base.openTransaction();
		System.out.println("ChallengeStatTest");
	}

    @AfterClass
	public static void after(){
		System.out.println("ChallengeStatTest finished");
		Base.rollbackTransaction();
		Base.close();
	}

    /**
    * creates a new ChallengeStat record on the data base.
    * @result ChallengeStat c=("challenge_id"= 1, "average_score"= 0.0, "solved_count"= 0) record sucessfully created.
    */
    @Test
    public void newChallengeStatTest() {
        ChallengeStat.newChallengeStat(1);
        Base.commitTransaction();
        List<ChallengeStat> cStatList= ChallengeStat.where("challenge_id = 1");
        ChallengeStat c= cStatList.get(0);

        assertEquals(1, c.get("challenge_id"));
        assertEquals((float) 0.0, c.get("average_score"));
        assertEquals(0, c.get("solved_count"));
    }

    /**
    * Increments the attribute "solved_count" (initial value = 0) of a ChallengeStat record .
    * @result solved_count = 1
    */
    @Test
    public void incrementSolvedCountTest() {
        ChallengeStat.newChallengeStat(1);
        Base.commitTransaction();
        List<ChallengeStat> cStatList= ChallengeStat.where("challenge_id = 1");
        ChallengeStat c= cStatList.get(0);

        assertEquals(0, c.get("solved_count"));
        ChallengeStat.incrementSolvedCount(c);
        assertEquals(1, c.get("solved_count"));
    }



}
