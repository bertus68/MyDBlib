/**
 * Unit Test
 */
package a.polverini.my;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class PGStest {

	private String dbname = "egscc-specification";

	private PGS pgs;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("PGS test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pgs = new PGS(dbname);
		assertNotNull("precondition constructor", pgs);
		assertFalse("precondition not-connected", pgs.isConnected());
		pgs.connect();
		assertTrue("precondition connected", pgs.isConnected());
	}

	@After
	public void tearDown() throws Exception {
		assertTrue("postcondition connected", pgs.isConnected());
		pgs.disconnect();
		assertFalse("postcondition not-connected", pgs.isConnected());
		pgs = null;
	}

	@Test
	public void testQuery() throws SQLException, AlreadyConnectedException, NotConnectedException {
		
		Map<String, Item> egsccSpecification = new HashMap<>();
		
		List<Item> results = pgs.query();
		for(Item result : results) {
			egsccSpecification.put(result.getName(), result);
		}
				
		assertTrue(egsccSpecification.containsKey("information"));
		assertTrue(egsccSpecification.containsKey("baselines"));
		assertTrue(egsccSpecification.containsKey("deployments"));
		assertTrue(egsccSpecification.containsKey("locks"));
		assertTrue(egsccSpecification.containsKey("requirements"));
		assertTrue(egsccSpecification.containsKey("projects"));
		
	}

}
