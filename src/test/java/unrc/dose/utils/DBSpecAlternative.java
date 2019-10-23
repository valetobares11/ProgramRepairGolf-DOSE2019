package unrc.dose.utils;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;


public class DBSpecAlternative {
	@Before
	public void setUp() {
		Base.open();
		Base.openTransaction();
	}
	
	@After
	public void tearDown() {
		Base.rollbackTransaction();
		Base.close();
	}
}
