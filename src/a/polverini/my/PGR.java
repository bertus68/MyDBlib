package a.polverini.my;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a.polverini.my.exceptions.NotConnectedException;

public class PGR extends DBR {

	private final String 	host;
	private final int 		port;

	/**
	 * @return the URL string for this database
	 */
	public String getURL() {
		return String.format("jdbc:postgresql://%s:%d/%s", host, port, getName());
	}
	
	/**
	 * retrieve the data from the results database
	 * @return a list of items
	 * @throws NotConnectedException
	 * @throws SQLException
	 */
	public List<Item> query() throws NotConnectedException, SQLException {
		
		// AdditionalInformationExecution
		AdditionalInformationExecution.query(this);
		
		// PerformanceMeasurementExecution
		PerformanceMeasurementExecution.query(this);
		
		// ScenarioExecution
		ScenarioExecution.query(this);
		ScenarioAdditionalInformationExecution.query(this);
		ScenarioPerformanceMeasurementExecution.query(this);
		ProcedureExecution.query(this);
		AutomatedProcedureExecution.query(this);
		ManualProcedureExecution.query(this);
		ManualProcedureStepExecution.query(this);
		TestCaseVerdict.query(this);
		
		return root.getChildren();
	}
	
	/**
	 * @param name the database name
	 * @param user the user name
	 * @param pswd the user password
	 * @param host the host name
	 * @param port the port number
	 * @throws ClassNotFoundException
	 */
	public PGR(String name, String user, String pswd, String host, int port) throws ClassNotFoundException {
		super(name, user, pswd);
		this.host = host;
		this.port = port;
		Class.forName("org.postgresql.Driver");
	}

	/**
	 * @param name the database name
	 * @throws ClassNotFoundException
	 */
	public PGR(String name) throws ClassNotFoundException {
		this(name, "postgres", "postgres", "localhost", 5432);
	}
	
	/**
	 * ADDITIONAL_INFORMATION_EXECUTION table
	 */
	public static class AdditionalInformationExecution 
	extends DBR.AdditionalInformationExecution 
	{
		// TABLE
		public static final String TABLE = "additional_information_execution";

		// FIELDS
		public static final String PK		= "pk";
		public static final String KEY		= "key";
		public static final String VALUE	= "value";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK, 		PK		);
			KEYS.put(Field.KEY, 	KEY		);
			KEYS.put(Field.VALUE, 	VALUE	);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}
		
	}

	/**
	 * AUTOMATED_PROCEDURE_EXECUTION table
	 */
	public static class AutomatedProcedureExecution 
	extends DBR.AutomatedProcedureExecution 
	{
		// TABLE
		public static final String TABLE	= "automatedprocedureexecution";

		// FIELDS
		public static final String PK	= "pk";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK,	PK	);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}

	/**
	 * MANUAL_PROCEDURE_EXECUTION table
	 */
	public static class ManualProcedureExecution 
	extends DBR.ManualProcedureExecution 
	{
		public static final String TABLE	= "manualprocedureexecution";

		// FIELDS
		public static final String PK	= "pk";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK,	PK	);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}

	/**
	 * MANUAL_PROCEDURE_STEP_EXECUTION table
	 */
	public static class ManualProcedureStepExecution
	extends DBR.ManualProcedureStepExecution 
	{
		// TABLE
		public static final String TABLE	= "manual_procedure_step_execution";

		// FIELDS
		public static final String PK								= "pk";
		public static final String DEVIATIONS						= "deviations";
		public static final String MANUAL_PROCEDURE_STEP_PK			= "manualproceduuresteppk";
		public static final String RESULTS							= "results";
		public static final String STEP_VERDICT						= "stepverdict";
		public static final String MANUAL_PROCEDURE_EXECUTION_PK	= "manualprocedureexecution_pk";
		public static final String STEP_NUMBER						= "stepnumber";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK, 								PK								);
			KEYS.put(Field.DEVIATIONS, 						DEVIATIONS						);
			KEYS.put(Field.MANUAL_PROCEDURE_STEP_PK, 		MANUAL_PROCEDURE_STEP_PK		);
			KEYS.put(Field.RESULTS, 						RESULTS							);
			KEYS.put(Field.STEP_VERDICT, 					STEP_VERDICT					);
			KEYS.put(Field.MANUAL_PROCEDURE_EXECUTION_PK, 	MANUAL_PROCEDURE_EXECUTION_PK	);
			KEYS.put(Field.STEP_NUMBER, 					STEP_NUMBER						);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}

	/**
	 * PERFORMANCE_MEASUREMENT_EXECUTION table
	 */
	public static class PerformanceMeasurementExecution 
	extends DBR.PerformanceMeasurementExecution 
	{
		// TABLE
		public static final String TABLE	= "performance_measurement_execution";

		// FIELDS
		public static final String PK		= "pk";
		public static final String KEY		= "key";
		public static final String VALUE	= "value";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK, 		PK		);
			KEYS.put(Field.KEY, 	KEY		);
			KEYS.put(Field.VALUE, 	VALUE	);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}

	/**
	 * PROCEDURE_EXECUTION table
	 */
	public static class ProcedureExecution 
	extends DBR.ProcedureExecution 
	{
		// TABLE
		public static final String TABLE	= "procedure_execution";

		// FIELDS
		public static final String PROCEDURE_EXECUTION_TYPE	= "procedure_execution_type";
		public static final String PK						= "pk";
		public static final String COMMENT					= "comment";
		public static final String END_TIME					= "endtime";
		public static final String PROCEDURE_ID				= "procedureid";
		public static final String PROCEDURE_VERDICT		= "procedureverdict";
		public static final String PROJECT_ID				= "projectid";
		public static final String START_TIME				= "starttime";
		public static final String TEST_EXECUTION			= "testexecution";
		public static final String SCENARIO_EXECUTION_PK	= "scenarioexecution_pk";
		public static final String DEPLOYMENT_NAME			= "deploymentname";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PROCEDURE_EXECUTION_TYPE,	PROCEDURE_EXECUTION_TYPE	);
			KEYS.put(Field.PK, 							PK							);
			KEYS.put(Field.COMMENT, 					COMMENT						);
			KEYS.put(Field.END_TIME, 					END_TIME					);
			KEYS.put(Field.PROCEDURE_ID, 				PROCEDURE_ID				);
			KEYS.put(Field.PROCEDURE_VERDICT,			PROCEDURE_VERDICT			);
			KEYS.put(Field.PROJECT_ID, 					PROJECT_ID					);
			KEYS.put(Field.START_TIME, 					START_TIME					);
			KEYS.put(Field.TEST_EXECUTION, 				TEST_EXECUTION				);
			KEYS.put(Field.SCENARIO_EXECUTION_PK, 		SCENARIO_EXECUTION_PK		);
			KEYS.put(Field.DEPLOYMENT_NAME, 			DEPLOYMENT_NAME				);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}

	/**
	 * SCENARIO_EXECUTION table
	 */
	public static class ScenarioExecution 
	extends DBR.ScenarioExecution 
	{
		// TABLE
		public static final String TABLE	= "scenario_execution";

		// FIELDS
		public static final String PK						= "pk";
		public static final String DEPLOYMENT_NAME			= "deploymentname";
		public static final String END_TIME					= "endtime";
		public static final String START_TIME				= "starttime";
		public static final String PROJECT_ID				= "projectid";
		public static final String SCENARIO_ID				= "scenarioid";
		public static final String SPECIFICATION_REVISION	= "specificationrevision";
		public static final String TEST_LOG_PATH			= "testlogpath";
		public static final String TEST_LOG_REVISION		= "testlogrevision";
		public static final String VERSION					= "version";
		public static final String COMMENT					= "comment";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK, 						PK						);
			KEYS.put(Field.DEPLOYMENT_NAME, 		DEPLOYMENT_NAME			);
			KEYS.put(Field.END_TIME, 				END_TIME				);
			KEYS.put(Field.START_TIME, 				START_TIME				);
			KEYS.put(Field.PROJECT_ID, 				PROJECT_ID				);
			KEYS.put(Field.SCENARIO_ID, 			SCENARIO_ID				);
			KEYS.put(Field.SPECIFICATION_REVISION, 	SPECIFICATION_REVISION	);
			KEYS.put(Field.TEST_LOG_PATH, 			TEST_LOG_PATH			);
			KEYS.put(Field.TEST_LOG_REVISION, 		TEST_LOG_REVISION		);
			KEYS.put(Field.VERSION, 				VERSION					);
			KEYS.put(Field.COMMENT, 				COMMENT					);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}

	/**
	 * SCENARIO_ADDITIONAL_INFORMATION_EXECUTION table
	 */
	public static class ScenarioAdditionalInformationExecution 
	extends DBR.ScenarioAdditionalInformationExecution 
	{
		// TABLE
		public static final String TABLE	= "scenario_execution_additional_information_execution";

		// FIELDS
		public static final String SCENARIO_EXECUTION_PK					= "scenario_execution_pk";
		public static final String ADDITIONAL_INFORMATION_EXECUTIONS_PK		= "additionalinformationexecutions_pk";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.SCENARIO_EXECUTION_PK, 					SCENARIO_EXECUTION_PK					);
			KEYS.put(Field.ADDITIONAL_INFORMATION_EXECUTIONS_PK, 	ADDITIONAL_INFORMATION_EXECUTIONS_PK	);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}

	/**
	 * SCENARIO_PERFORMANCE_MEASUREMENT_EXECUTION table
	 */
	public static class ScenarioPerformanceMeasurementExecution 
	extends DBR.ScenarioPerformanceMeasurementExecution 
	{
		// TABLE
		public static final String TABLE	= "scenario_execution_performance_measurement_execution";

		// FIELDS
		public static final String SCENARIO_EXECUTION_PK					= "scenario_execution_pk";
		public static final String PERFORMANCE_MEASUREMNET_EXECUTIONS_PK	= "performancemeasurementexecutions_pk";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.SCENARIO_EXECUTION_PK, 					SCENARIO_EXECUTION_PK					);
			KEYS.put(Field.PERFORMANCE_MEASUREMNET_EXECUTIONS_PK,	PERFORMANCE_MEASUREMNET_EXECUTIONS_PK	);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}

	/**
	 * TESTCASE_VERDICT table
	 */
	public static class TestCaseVerdict 
	extends DBR.TestCaseVerdict 
	{
		// TABLE
		public static final String TABLE	= "test_case_verdict";

		// FIELDS
		public static final String PK						= "pk";
		public static final String PROJECT_ID				= "projectid";
		public static final String TESTCASE_ID				= "testcaseid";
		public static final String VERDICT					= "verdict";
		public static final String PROCEDURE_EXECUTION_PK	= "procedureexecution_pk";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK, 						PK						);
			KEYS.put(Field.PROJECT_ID, 				PROJECT_ID				);
			KEYS.put(Field.TESTCASE_ID, 			TESTCASE_ID				);
			KEYS.put(Field.VERDICT, 				VERDICT					);
			KEYS.put(Field.PROCEDURE_EXECUTION_PK,	PROCEDURE_EXECUTION_PK	);
		}

		/**
		 * @param pgr the PostgreSQL results database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(PGR pgr) throws NotConnectedException, SQLException {
			query(pgr, TABLE, KEYS);
		}

	}
	
}
