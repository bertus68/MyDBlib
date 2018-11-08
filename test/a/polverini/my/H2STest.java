/**
 * Unit Test
 */
package a.polverini.my;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import a.polverini.my.exceptions.AlreadyConnectedException;
import a.polverini.my.exceptions.NotConnectedException;

/**
 * @author A.Polverini
 */
public class H2STest {

	private String dbname = "test";
	private H2S db;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("H2S test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		db = new H2S(dbname);
		assertNotNull("precondition constructor", db);
		assertFalse("precondition not-connected", db.isConnected());
		db.connect();
		assertTrue("precondition connected", db.isConnected());
	}

	@After
	public void tearDown() throws Exception {
		assertTrue("postcondition connected", db.isConnected());
		db.disconnect();
		assertFalse("postcondition not-connected", db.isConnected());
		db = null;
	}

	@Test
	public void testQuery() throws SQLException, AlreadyConnectedException, NotConnectedException {
		db.query();
	}

}
