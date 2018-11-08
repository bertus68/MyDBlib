/**
 * Unit Test
 */
package a.polverini.my;

import static org.junit.Assert.assertFalse;
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
public class RemoteTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("----------------------------------------");
		System.out.println("-- RemoteTest");
		System.out.println("----------------------------------------");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConnection() throws SQLException, AlreadyConnectedException, NotConnectedException, ClassNotFoundException {
		int    port = 5432;
		String host = "195.81.84.89";
		String user = "apolverini";
		String pswd = "Sabr1na$";
		String name = "egscc-specification";
		
		System.out.println("host="+host);
		System.out.println("port="+port);
		System.out.println("name="+name);
		
		DB remoteDB = new PGS(name, user, pswd, host, port);
		assertFalse("precondition not-connected", remoteDB.isConnected());

		System.out.println("connecting...");
		remoteDB.connect();
		assertTrue("connected", remoteDB.isConnected());

		System.out.println("OK!");

		System.out.println("disconnecting...");
		remoteDB.disconnect();
		assertFalse("not-connected", remoteDB.isConnected());
	}
	
}
