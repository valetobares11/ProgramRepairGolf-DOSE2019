package unrc.dose.utils;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
/**
 * Class that opens the database before the tests,
 * open a transaction before each test,
 * rollback the transaction after each test
 * and close the database after all the tests
 * @author agustin
 *
 */

public class DBSpecAlternative {
	/**
	 * Opens the database
	 * before all the tests
	 */
	@BeforeClass
	public static void classSetUp() {
		Base.open();
	}
	/**
	 * Opens a transaction
	 * before each test
	 */
	@Before
	public void setUp() {
		Base.openTransaction();
	}
	
	/**
	 * Rollback a transaction
	 * after each test
	 */
	@After
	public void tearDown() {
		Base.rollbackTransaction();
	}
	
	/**
	 * Closes the database
	 * after all the tests
	 */
	@AfterClass
	public static void classTearDown() {
		Base.close();
	}
}
