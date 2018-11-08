package a.polverini.my;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import a.polverini.my.DB.Type;
import a.polverini.my.exceptions.AlreadyConnectedException;
import a.polverini.my.exceptions.NotConnectedException;
import a.polverini.my.exceptions.UnexpectedTypeException;

public class DBtest {

	private DB db;
	private String dbname 	= "test";
	private String dbtable 	= "test";
	private String dbuser 	= "sa";
	private String dbpswd 	= "";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("DB test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		db = new DB(dbname, dbuser, dbpswd) {

			@Override
			public String getURL() {
				return String.format("jdbc:h2:file://c:/data/%s", getName());
			}
			
		};
		assertNotNull(db);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetURL() {
		assertEquals("url", "jdbc:h2:file://~/data/"+dbname, db.getURL() );
	}

	@Test
	public void testGetName() {
		assertEquals("name", dbname, db.getURL() );
	}

	@Test
	public void testDB() {
		assertNotNull(db);
	}

	@Test
	public void testGetTypes() throws SQLException, NotConnectedException, UnexpectedTypeException {
		Map<String, Type> types = db.getTypes(dbtable);
		assertNotNull("types", types);
	}

	@Test
	public void testIsConnected() throws SQLException {
		assertFalse("not-connected", db.isConnected());
	}

	@Test
	public void testConnect() throws SQLException, AlreadyConnectedException {
		assertFalse("precondition not-connected", db.isConnected());
		db.connect();
		assertTrue("connected", db.isConnected());
	}

	@Test (expected = AlreadyConnectedException.class)
	public void testAlreadyConnected() throws SQLException, AlreadyConnectedException, NotConnectedException {
		assertFalse("precondition not-connected", db.isConnected());
		db.connect();
		assertTrue("connected", db.isConnected());
		db.connect();
	}
	
	@Test
	public void testDisconnect() throws SQLException, AlreadyConnectedException, NotConnectedException {
		assertFalse("precondition not-connected", db.isConnected());
		db.connect();
		assertTrue("connected", db.isConnected());
		db.disconnect();
		assertFalse("not-connected", db.isConnected());
	}
	
	@Test (expected = NotConnectedException.class)
	public void testNotConnectedException() throws SQLException, AlreadyConnectedException, NotConnectedException {
		assertFalse("precondition not-connected", db.isConnected());
		db.connect();
		assertTrue("connected", db.isConnected());
		db.disconnect();
		assertFalse("not-connected", db.isConnected());
		db.disconnect();
	}

	@Test
	public void testCount() throws SQLException, AlreadyConnectedException, NotConnectedException {
		assertFalse("precondition not-connected", db.isConnected());
		db.connect();
		assertTrue("connected", db.isConnected());
		
		int count = db.count(dbtable);
		assertEquals("count", 1, count);
		
		db.disconnect();
		assertFalse("not-connected", db.isConnected());
	}

	@Test
	public void testPk() throws SQLException, AlreadyConnectedException, NotConnectedException {
		assertFalse("precondition not-connected", db.isConnected());
		db.connect();
		assertTrue("connected", db.isConnected());
		
		int pk = db.pk(dbtable);
		assertEquals("pk", 2, pk);
		
		db.disconnect();
		assertFalse("not-connected", db.isConnected());
	}

	@Test
	public void testTruncate() throws SQLException, AlreadyConnectedException, NotConnectedException {
		assertFalse("precondition not-connected", db.isConnected());
		db.connect();
		assertTrue("connected", db.isConnected());
		db.truncate(dbtable);
		db.disconnect();
		assertFalse("not-connected", db.isConnected());
	}

	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
