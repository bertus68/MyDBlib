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
public class H2STest {

	private String dbname = "test";
	private H2S h2s;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("H2S test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		h2s = new H2S(dbname);
		assertNotNull("precondition constructor", h2s);
		assertFalse("precondition not-connected", h2s.isConnected());
		h2s.connect();
		assertTrue("precondition connected", h2s.isConnected());
	}

	@After
	public void tearDown() throws Exception {
		assertTrue("postcondition connected", h2s.isConnected());
		h2s.disconnect();
		assertFalse("postcondition not-connected", h2s.isConnected());
		h2s = null;
	}

	@Test
	public void testQuery() throws SQLException, AlreadyConnectedException, NotConnectedException {
		
		Map<String, Item> egsccSpecification = new HashMap<>();
		
		List<Item> results = h2s.query();
		for(Item result : results) {
			egsccSpecification.put(result.getTag(), result);
		}
				
		assertTrue(egsccSpecification.containsKey("baselines"	 ));
		assertTrue(egsccSpecification.containsKey("information"	 ));
		assertTrue(egsccSpecification.containsKey("deployments"	 ));
		assertTrue(egsccSpecification.containsKey("locks"		 ));
		assertTrue(egsccSpecification.containsKey("requirements" ));
		assertTrue(egsccSpecification.containsKey("projects"	 ));
		
		try {
			XML.Writer writer = new XML.Writer("c:/tmp/h2s.xml");
			writer.writeSpecification(egsccSpecification);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
