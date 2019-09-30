package unrc.dose;

import static org.junit.Assert.assertEquals;

import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
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
	
	@AfterClass
	public static void afterAll() {
		log.info("UserStatTest AfterClass");
		Base.close();
	}
	
	@Test
	public void getUserStat() {
	    User u = new User();
	    u.set("password", "JohnDoe");
	    u.set("username", "JohnDoe");
	    u.set("email_address", "JohnDoe@gmail.com");
	    u.save();
	    UserStat us = new UserStat();
	    us.set("user_id", u.getId());
	    us.save();
	    UserStat us2 = UserStat.getUserStat(u);
	    assertEquals(us.getId().toString(), us2.getId().toString());
	    assertEquals(us.get("user_id").toString(), us2.get("user_id").toString());
	}
	

}
