package a.polverini.my;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import a.polverini.my.exceptions.NotConnectedException;

public abstract class DBR extends DB {

	private static final boolean DEBUG = false;
	
	/** {@inheritDoc} */
	abstract public String getURL();

	/**
	 * retrieve the data from the specification database
	 * @return a list of items
	 * @throws NotConnectedException
	 * @throws SQLException
	 */
	abstract public List<Item> query() throws NotConnectedException, SQLException;
	
	/**
	 * the tables in the RESULTS database
	 */
	public enum Table {
		ADDITIONAL_INFORMATION_EXECUTION,
		AUTOMATED_PROCEDURE_EXECUTION,
		MANUAL_PROCEDURE_EXECUTION,
		MANUAL_PROCEDURE_MEASUREMENT_EXECUTION,
		PERFORMANCE_MEASUREMENT_EXECUTION,
		PROCEDURE_EXECUTION,
		SCENARIO_EXECUTION,
		SCENARIO,EXECUTION_ADDITIONAL_INFORMATION_EXECUTION,
		SCENARIO_EXECUTION_PERFORMANCE_MEASUREMENT_EXECUTION,
		TEST_CASE_VERDICT
	}

	Item root = new Item();
	
	Item additionalInformationExecutionRoot = new Item(root, "information");
	Map<Object, Item> additionalInformationExecutionPK = new HashMap<>();

	Item performanceMeasurementExecutionRoot = new Item(root, "measurements");
	Map<Object, Item> performanceMeasurementExecutionPK = new HashMap<>();

	Item testcaseVerdictRoot = new Item(root, "verdicts");
	Map<Object, Item> testcaseVerdictPK = new HashMap<>();

	Item scenarioExecutionRoot = new Item(root, "scenarios");
	Map<Object, Item> scenarioExecutionPK = new HashMap<>();
	Map<Object, Item> procedureExecutionPK = new HashMap<>();

	/**
	 * constructor
	 * @param name the database name
	 * @param user the database user
	 * @param pswd the database password
	 */
	public DBR(String name, String user, String pswd) {
		super(name, user, pswd);
	}

	/**
	 * ADDITIONAL_INFORMATION_EXECUTION table
	 */
	public static class AdditionalInformationExecution {

		public static final String TAG = "AdditionalInformationExecution";
		
		/**
		 * the fields in the ADDITIONAL_INFORMATION_EXECUTION table
		 */
		public enum Field {
			PK,
			KEY,
			VALUE
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s",item.get(Field.KEY));
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbr.additionalInformationExecutionRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbr.additionalInformationExecutionPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+AdditionalInformationExecution.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * AUTOMATED_PROCEDURE_EXECUTION table
	 */
	public static class AutomatedProcedureExecution {

		public static final String TAG = "AutomatedProcedureExecution";
		
		/**
		 * the fields in the AUTOMATED_PROCEDURE_EXECUTION table
		 */
		public enum Field {
			PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return ProcedureExecution.getUniqueID(item);
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {

				Object procedureExecution_pk = result.get(Field.PK);

				Item procedureExecution = dbr.procedureExecutionPK.get(procedureExecution_pk);
				if(procedureExecution==null) {
					System.err.println(ProcedureExecution.TAG+" (pk="+procedureExecution_pk+")");
					continue;
				}
				
				if(DEBUG) System.out.println(TAG+" "+AutomatedProcedureExecution.getUniqueID(procedureExecution));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * MANUAL_PROCEDURE_EXECUTION table
	 */
	public static class ManualProcedureExecution {

		public static final String TAG = "ManualProcedureExecution";

		/**
		 * the fields in the MANUAL_PROCEDURE_EXECUTION table
		 */
		public enum Field {
			PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return ProcedureExecution.getUniqueID(item);
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {

				Object procedureExecution_pk = result.get(Field.PK);

				Item procedureExecution = dbr.procedureExecutionPK.get(procedureExecution_pk);
				if(procedureExecution==null) {
					System.err.println(ProcedureExecution.TAG+" (pk="+procedureExecution_pk+")");
					continue;
				}
				
				if(DEBUG) System.out.println(TAG+" "+ManualProcedureExecution.getUniqueID(procedureExecution));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * MANUAL_PROCEDURE_STEP_EXECUTION table
	 */
	public static class ManualProcedureStepExecution {

		public static final String TAG = "ManualProcedureStepExecution";

		/**
		 * the fields in the MANUAL_PROCEDURE_STEP_EXECUTION table
		 */
		public enum Field {
			PK,
			STEP_NUMBER,
			STEP_VERDICT,
			RESULTS,
			DEVIATIONS,
			MANUAL_PROCEDURE_STEP_PK,
			MANUAL_PROCEDURE_EXECUTION_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%d", ManualProcedureExecution.getUniqueID(item.getParent()), item.get(Field.STEP_NUMBER));
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {
				
				Object procedureExecution_pk = result.get(Field.MANUAL_PROCEDURE_EXECUTION_PK);
				
				Item procedureExecution = dbr.procedureExecutionPK.get(procedureExecution_pk);
				if(procedureExecution==null) {
					System.err.println(ManualProcedureStepExecution.TAG+" invalid procedure (pk="+procedureExecution_pk+")");
					continue;
				}
				
				Item item = new Item(procedureExecution, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbr.additionalInformationExecutionPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+ManualProcedureStepExecution.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * PERFORMANCE_MEASUREMENT_EXECUTION table
	 */
	public static class PerformanceMeasurementExecution {

		public static final String TAG = "PerformanceMeasurementExecution";

		/**
		 * the fields in the PERFORMANCE_MEASUREMENT_EXECUTION table
		 */
		public enum Field {
			PK,
			KEY,
			VALUE
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", item.get(Field.KEY));
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbr.performanceMeasurementExecutionRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbr.performanceMeasurementExecutionPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+PerformanceMeasurementExecution.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * PROCEDURE_EXECUTION table
	 */
	public static class ProcedureExecution {

		public static final String TAG = "ProcedureExecution";

		/**
		 * the fields in the PROCEDURE_EXECUTION table
		 */
		public enum Field {
			SCENARIO_EXECUTION_PK,
			PK,
			DEPLOYMENT_NAME,
			START_TIME,
			END_TIME,
			PROJECT_ID,
			PROCEDURE_ID,
			PROCEDURE_VERDICT,
			PROCEDURE_EXECUTION_TYPE,
			TEST_EXECUTION,
			COMMENT
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", item.get(Field.PROJECT_ID), item.get(Field.PROCEDURE_ID));
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {

				Object scenarioExecution_pk = result.get(Field.SCENARIO_EXECUTION_PK);
				Item scenarioExecution = dbr.scenarioExecutionPK.get(scenarioExecution_pk);
				if(scenarioExecution==null) {
					System.err.println(TAG+" invalid scenario-execution (pk="+scenarioExecution_pk+")");
					continue;
				}
				
				Item item = new Item(scenarioExecution, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbr.procedureExecutionPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+ProcedureExecution.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * SCENARIO_EXECUTION table
	 */
	public static class ScenarioExecution {

		public static final String TAG = "ScenarioExecution";

		/**
		 * the fields in the PROCEDURE_EXECUTION table
		 */
		public enum Field {
			PK,
			DEPLOYMENT_NAME,
			START_TIME,
			END_TIME,
			PROJECT_ID,
			SCENARIO_ID,
			TEST_LOG_PATH,
			TEST_LOG_REVISION,
			SPECIFICATION_REVISION,
			VERSION,
			COMMENT
		}
		
		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", item.get(Field.PROJECT_ID), item.get(Field.SCENARIO_ID));
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbr.scenarioExecutionRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbr.scenarioExecutionPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+ScenarioExecution.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * SCENARIO_ADDITIONAL_INFORMATION_EXECUTION table
	 */
	public static class ScenarioAdditionalInformationExecution {

		public static final String TAG = "ScenarioAdditionalInformationExecution";

		/**
		 * the fields in the SCENARIO_ADDITIONAL_INFORMATION_EXECUTION table
		 */
		public enum Field {
			SCENARIO_EXECUTION_PK,
			ADDITIONAL_INFORMATION_EXECUTIONS_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", ScenarioExecution.getUniqueID(item.getParent()), AdditionalInformationExecution.getUniqueID((Item)item.get(AdditionalInformationExecution.TAG)));
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {

				Object scenarioExecution_pk = result.get(Field.SCENARIO_EXECUTION_PK);
				Item scenarioExecution = dbr.scenarioExecutionPK.get(scenarioExecution_pk);
				if(scenarioExecution==null) {
					System.err.println(TAG+" invalid scenario-execution (pk="+scenarioExecution_pk+")");
					continue;
				}

				Object additioalInformationExecution_pk = result.get(Field.ADDITIONAL_INFORMATION_EXECUTIONS_PK);
				Item additioalInformationExecution = dbr.additionalInformationExecutionPK.get(additioalInformationExecution_pk);
				if(additioalInformationExecution==null) {
					System.err.println(TAG+" invalid additional-information-execution (pk="+additioalInformationExecution_pk+")");
					continue;
				}

				Item reference = new Item(scenarioExecution, "reference");
				reference.set(AdditionalInformationExecution.TAG, additioalInformationExecution);

				if(DEBUG) System.out.println(TAG+" "+ScenarioAdditionalInformationExecution.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * SCENARIO_PERFORMANCE_MEASUREMENT_EXECUTION table
	 */
	public static class ScenarioPerformanceMeasurementExecution {

		public static final String TAG = "ScenarioPerformanceMeasurementExecution";

		/**
		 * the fields in the SCENARIO_PERFORMANCE_MEASUREMENT_EXECUTION table
		 */
		public enum Field {
			SCENARIO_EXECUTION_PK,
			PERFORMANCE_MEASUREMNET_EXECUTIONS_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", ScenarioExecution.getUniqueID(item.getParent()), PerformanceMeasurementExecution.getUniqueID((Item)item.get(PerformanceMeasurementExecution.TAG)));
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {

				Object scenarioExecution_pk = result.get(Field.SCENARIO_EXECUTION_PK);
				Item scenarioExecution = dbr.scenarioExecutionPK.get(scenarioExecution_pk);
				if(scenarioExecution==null) {
					System.err.println(TAG+" invalid scenario-execution (pk="+scenarioExecution_pk+")");
					continue;
				}

				Object performanceMeasurementExecution_pk = result.get(Field.PERFORMANCE_MEASUREMNET_EXECUTIONS_PK);
				Item performanceMeasurementExecution = dbr.additionalInformationExecutionPK.get(performanceMeasurementExecution_pk);
				if(performanceMeasurementExecution==null) {
					System.err.println(TAG+" invalid performance-measurement-execution (pk="+performanceMeasurementExecution_pk+")");
					continue;
				}

				Item reference = new Item(scenarioExecution, "reference");
				reference.set(PerformanceMeasurementExecution.TAG, performanceMeasurementExecution);

				if(DEBUG) System.out.println(TAG+" "+ScenarioPerformanceMeasurementExecution.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

	/**
	 * TESTCASE_VERDICT table
	 */
	public static class TestCaseVerdict {

		public static final String TAG = "TestCaseVerdict";

		/**
		 * the fields in the TESTCASE_VERDICT table
		 */
		public enum Field {
			PK,
			PROJECT_ID,
			TESTCASE_ID,
			VERDICT,
			PROCEDURE_EXECUTION_PK
		}
		
		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", item.get(Field.PROJECT_ID), item.get(Field.TESTCASE_ID));
		}

		/**
		 * @param dbr the results database
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBR dbr, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbr.query(table, keys);
			for(Properties result : results) {
				Item testcaseVerdict = new Item(dbr.testcaseVerdictRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						testcaseVerdict.set(key, val);
					}
				}
				dbr.testcaseVerdictPK.put(testcaseVerdict.get(Field.PK), testcaseVerdict);
				if(DEBUG) System.out.println(TAG+" "+TestCaseVerdict.getUniqueID(testcaseVerdict));
				
				Object procedureExecution_pk = result.get(Field.PROCEDURE_EXECUTION_PK);
				Item procedureExecution = dbr.procedureExecutionPK.get(procedureExecution_pk);
				if(procedureExecution==null) {
					System.err.println(TAG+" invalid procedure-execution (pk="+procedureExecution_pk+")");
					continue;
				}
				
				Item reference = new Item(procedureExecution, "reference");
				reference.set(TestCaseVerdict.TAG, testcaseVerdict);
				
			}
			if(DEBUG) System.out.println(TAG+" "+dbr.count(table));
		}

	}

}
