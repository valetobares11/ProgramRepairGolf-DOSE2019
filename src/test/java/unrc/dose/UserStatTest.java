package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserStatTest {

private static final Logger log = LoggerFactory.getLogger(UserStatTest.class);

	@BeforeClass
	public static void beforeAll() {
		log.info("UserStatTest BeforeClass");
		Base.open();
	}
	
	@Before
	public void beforeTest() {
		Base.openTransaction();
	}

	@AfterClass
	public static void afterAll() {
		log.info("UserStatTest AfterClass");
		Base.close();
	}
	
	@After
	public void afterTest() {
		Base.rollbackTransaction();
	}

	/**
	 * Test the method createUserStat from
	 * UserStat class.
	 */
	@Test
	public void createUserStat() {
		User u = new User();
		u.set("username","Hackerman");
		u.set("password", "T3H4ck303lC0r4z0n");
		u.set("email_address", "hackingnsa@gmail.com");
		u.saveIt();
		UserStat.createUserStat(u.getInteger("id"));
		UserStat stat = UserStat.findFirst("user_id = ?", u.get("id"));
		assertNotNull(stat);
		assertEquals((int)stat.get("created_challenges"),0);
		assertEquals((int)stat.get("solved_challenges"),0);
		assertEquals((int)stat.get("current_points"),0);
	}

	/**
	 * Test the method getUserStat from
	 * UserStat class.
	 */
	@Test
	public void getUserStat() {
		User u = new User();
		u.set("password", "JohnDoe");
		u.set("username", "JohnDoe");
		u.set("email_address", "JohnDoe@gmail.com");
		u.save();
		UserStat.createUserStat(u.getInteger("id"));
		UserStat us = UserStat.findFirst("user_id = ?", u.get("id"));
		UserStat us2 = UserStat.getUserStat(u);
		assertEquals(us.getId().toString(), us2.getId().toString());
		assertEquals(us.get("user_id").toString(), us2.get("user_id").toString());
	}

	/**
	 * Test the method showAllUsers
	 * UserStat class.
	 */
	@Test
	public void showAllUsers() {
		User.deleteAll();
		User u = new User();
		u.set("password", "ElMejor");
		u.set("username", "LaMosca");
		u.set("email_address", "LaMosca@gmail.com");
		u.save();
		u = new User();
		u.set("password", "NotJohnConnor");
		u.set("username", "Themosque");
		u.set("email_address", "LaMosquita@gmail.com");
		u.save();
		LazyList<User> users = UserStat.showAllUsers();
		for (int i = 0; i < users.size(); i++){
			User user = users.get(i);
			assertTrue(user.get("username").toString().equals("LaMosca")|| user.get("username").toString().equals("Themosque"));
		}
	}
}
