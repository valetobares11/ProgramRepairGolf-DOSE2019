package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.javalite.activejdbc.LazyList;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import unrc.dose.utils.DBSpecAlternative;


public class UserStatTest extends DBSpecAlternative{

private static final Logger log = LoggerFactory.getLogger(UserStatTest.class);

	/**
	 * This perform the 'arrange' phase
	 * of all the tests below
	 */
	@Before
	public void before() {
		log.info("UserStatTest SetUp");
		User.deleteAll();
		User u = User.set("Hackerman", "T3H4ck303lC0r4z0n", "hackingnsa@gmail.com", false);
		u.saveIt();
		User u2 = User.set("Themosque", "NotJohnConnor", "LaMosquita@gmail.com", false);
		u2.saveIt();
		UserStat stat1 = UserStat.createUserStat(u.getId());
		stat1.setCurrentPoints(10);
		UserStat stat2 = UserStat.createUserStat(u2.getId());
		stat2.setCurrentPoints(20);
	}

	/**
	 * Test the method getUserId from
	 * UserStat class.
	 */
	@Test
	public void getUserIdTest() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.findFirst("user_id = ?", u.getId());
		assertEquals(stat.getUserId(),stat.get("user_id"));
		
	}
	/**
	 * Test the method getCreatedChallenges from
	 * UserStat class.
	 */
	@Test
	public void getCreatedChallengesTest() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.findFirst("user_id = ?", u.getId());
		assertEquals(stat.getCreatedChallenges(),stat.get("created_challenges"));
	}
	/**
	 * Test the method getSolvedChallenges from
	 * UserStat class.
	 */
	@Test
	public void getSolvedChallengesTest() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.findFirst("user_id = ?", u.getId());
		assertEquals(stat.getSolvedChallenges(),stat.get("solved_challenges"));
	}
	/**
	 * Test the method getCurrentPoints from
	 * UserStat class.
	 */
	@Test
	public void getCurrentPointsTest() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.findFirst("user_id = ?", u.getId());
		assertEquals(stat.getCurrentPoints(),stat.get("current_points"));
	}

	/**
	 * Test the method createUserStat from
	 * UserStat class.
	 */
	@Test
	public void createUserStat() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.findFirst("user_id = ?", u.getId());
		assertNotNull(stat);
		assertEquals(stat.getCreatedChallenges(), 0);
		assertEquals(stat.getSolvedChallenges(), 0);
		assertEquals(stat.getCurrentPoints(), 10);
	}

	/**
	 * Test the method getUserStat from
	 * UserStat class.
	 */
	@Test
	public void getUserStat() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat us = UserStat.findFirst("user_id = ?", u.getId());
		UserStat us2 = UserStat.getUserStat(u);
		assertEquals(us.getId().toString(), us2.getId().toString());
		assertEquals(us.get("user_id").toString(), us2.get("user_id").toString());
	}

	/**
	 * Test the method showAllUserStat
	 * UserStat class.
	 */
	@Test
	public void showAllUserStat() {
		User u = User.findFirst("username = ?","Hackerman");
		User u2 = User.findFirst("username = ?","Themosque");
		LazyList<UserStat> userStats = UserStat.showAllUserStat();
		for (int i = 0; i < userStats.size(); i++){
			UserStat stat = userStats.get(i);
			assertTrue(stat.getUserId() == u.getId() || stat.getUserId() == u2.getId());
		}
	}
	
	/**
	 * Test the method showBestScores
	 * UserStat class.
	 */
	 @Test
	 public void showBestScores() {
		 User u2 = User.findFirst("username = ?","TheMosque");
		 LazyList<UserStat> userStats = UserStat.showBestScores(1);
		 assertTrue(userStats.get(0).getUserId()==u2.getId());
	 }
	
	/*Test for validators*/

	/**
	 * Test the validator of
	 * presence of user_id 
	 */
	@Test
	public void validatePresenceOfUserId() {
		UserStat stat = new UserStat();
		stat.set("created_challenges", 0);
		stat.set("solved_challenges", 0);
		stat.set("current_points", 0);
		assertFalse(stat.isValid());
	}
	
	/**
	 * Test the validator of
	 * presence of created_challenges 
	 */
	@Test
	public void validatePresenceOfCreatedChallenges() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = new UserStat();
		stat.set("user_id", u.getId());
		stat.set("solved_challenges", 0);
		stat.set("current_points", 0);
		assertFalse(stat.isValid());
	}
	
	/**
	 * Test the validator of
	 * presence of solved_challenges 
	 */
	@Test
	public void validatePresenceOfSolvedChallenges() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = new UserStat();
		stat.set("user_id", u.getId());
		stat.set("created_challenges", 0);
		stat.set("current_points", 0);
		assertFalse(stat.isValid());
	}
	
	/**
	 * Test the validator of
	 * presence of current_points 
	 */
	@Test
	public void validatePresenceOfCurrentPoints() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = new UserStat();
		stat.set("user_id", u.getId());
		stat.set("created_challenges", 0);
		stat.set("solved_challenges", 0);
		assertFalse(stat.isValid());
	}
	
}
