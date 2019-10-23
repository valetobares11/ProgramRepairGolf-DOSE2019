package unrc.dose;

import static org.junit.Assert.assertEquals;
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
		User u = new User();
		u.set("username","Hackerman");
		u.set("password", "T3H4ck303lC0r4z0n");
		u.set("email_address", "hackingnsa@gmail.com");
		u.saveIt();
		User u2 = new User();
		u2.set("password", "NotJohnConnor");
		u2.set("username", "Themosque");
		u2.set("email_address", "LaMosquita@gmail.com");
		u2.saveIt();
	}

	/**
	 * Test the method getUserId from
	 * UserStat class.
	 */
	@Test
	public void getUserIdTest() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat.createUserStat(u.getInteger("id"));
		UserStat stat = UserStat.findFirst("user_id = ?", u.get("id"));
		assertEquals(stat.getUserId(),stat.get("user_id"));
		
	}
	/**
	 * Test the method getCreatedChallenges from
	 * UserStat class.
	 */
	@Test
	public void getCreatedChallengesTest() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.createUserStat(u.getId());
		assertEquals(stat.getCreatedChallenges(),stat.get("created_challenges"));
	}
	/**
	 * Test the method getSolvedChallenges from
	 * UserStat class.
	 */
	@Test
	public void getSolvedChallengesTest() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.createUserStat(u.getId());
		assertEquals(stat.getSolvedChallenges(),stat.get("solved_challenges"));
	}
	/**
	 * Test the method getCurrentPoints from
	 * UserStat class.
	 */
	@Test
	public void getCurrentPointsTest() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.createUserStat(u.getId());
		assertEquals(stat.getCurrentPoints(),stat.get("current_points"));
	}

	/**
	 * Test the method createUserStat from
	 * UserStat class.
	 */
	@Test
	public void createUserStat() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat stat = UserStat.createUserStat(u.getId());
		assertNotNull(stat);
		assertEquals(stat.getCreatedChallenges(),0);
		assertEquals(stat.getSolvedChallenges(),0);
		assertEquals(stat.getCurrentPoints(),0);
	}

	/**
	 * Test the method getUserStat from
	 * UserStat class.
	 */
	@Test
	public void getUserStat() {
		User u = User.findFirst("username = ?","Hackerman");
		UserStat us = UserStat.createUserStat(u.getId());
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
		UserStat.createUserStat(u.getInteger("id"));
		User u2 = User.findFirst("username = ?","Themosque");
		UserStat.createUserStat(u2.getInteger("id"));
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
		 User u = User.findFirst("username = ?","Hackerman");
		 UserStat us1= UserStat.createUserStat(u.getId());
		 us1.setCurrentPoints(10);
		 User u2 = User.findFirst("username = ?","TheMosque");
		 UserStat us2 = UserStat.createUserStat(u2.getId());
		 us2.setCurrentPoints(20);
		 us2.saveIt();
		 LazyList<UserStat> userStats = UserStat.showBestScores(1);
		 assertTrue(userStats.get(0).getUserId()==u2.getId());
		 u.delete();
		 u2.delete();
	 }

}
