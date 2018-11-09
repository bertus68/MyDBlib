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
public class PGRtest {

	private String dbname = "egscc-results";

	private PGR pgr;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("PGR test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pgr = new PGR(dbname);
		assertNotNull("precondition constructor", pgr);
		assertFalse("precondition not-connected", pgr.isConnected());
		pgr.connect();
		assertTrue("precondition connected", pgr.isConnected());
	}

	@After
	public void tearDown() throws Exception {
		assertTrue("postcondition connected", pgr.isConnected());
		pgr.disconnect();
		assertFalse("postcondition not-connected", pgr.isConnected());
		pgr = null;
	}

	@Test
	public void testQuery() throws SQLException, AlreadyConnectedException, NotConnectedException {
		
		Map<String, Item> egsccResults = new HashMap<>();
		
		List<Item> results = pgr.query();
		for(Item result : results) {
			egsccResults.put(result.getTag(), result);
		}
				
		assertTrue(egsccResults.containsKey("information"));
		assertTrue(egsccResults.containsKey("measurements"));
		assertTrue(egsccResults.containsKey("verdicts"));
		assertTrue(egsccResults.containsKey("scenarios"));
		
	}

}
