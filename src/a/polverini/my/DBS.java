package a.polverini.my;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import a.polverini.my.exceptions.NotConnectedException;

public abstract class DBS extends DB {

	private static final boolean DEBUG = false;
	
	/** {@inheritDoc} */
	abstract public String getURL();

	/**
	 * retrieve the data from the specification database
	 * @return the list of items
	 * @throws NotConnectedException
	 * @throws SQLException
	 */
	abstract public List<Item> query() throws NotConnectedException, SQLException;

	/**
	 * the tables in the SPECIFICATION database
	 */
	public enum Table {
		ADDITIONAL_INFORMATION,
		AUTOMATED_PROCEDURE,
		AUXILIARY_ROUTINE,
		BASELINE,
		BASELINE_ITEM,
		DEPLOYMENT,
		EDITING_LOCK,
		FEATURE,
		MANUAL_PROCEDURE,
		MANUAL_PROCEDURE_STEP,
		PERFORMANCE_MEASUREMENT,
		PROCEDURE,
		PROCEDURE_TESTCASE,
		PROJECT,
		PROJECT_REQUIREMENT,
		PROJECT_REQUIREMENT_DEPLOYMENT,
		REQUIREMENT,
		REQUIREMENT_DEPLOYMENT,
		SCENARIO,
		SCENARIO_ADDITIONAL_INFORMATION,
		SCENARIO_DEPLOYMENT,
		SCENARIO_PERFORMANCE_MEASUREMENT,
		SOFTWARE_REQUIREMENT,
		SOFTWARE_REQUIREMENT_USER_REQUIREMENT,
		TESTAREA,
		TESTCASE,
		TESTCASE_PROJECT_REQUIREMENT,
		USER_REQUIREMENT
	}
	
	Item root = new Item();
	
	Item additionalInformationRoot = new Item(root, "information");
	Map<Object, Item> additionalInformationPK = new HashMap<>();
	
	Item baselineRoot = new Item(root, "baselines");
	Map<Object, Item> baselinePK = new HashMap<>();
	
	Item deploymentRoot = new Item(root, "deployments");
	Map<Object, Item> deploymentPK = new HashMap<>();
	
	Item lockRoot = new Item(root, "locks");
	Map<Object, Item> lockPK = new HashMap<>();
	
	Item requirementRoot = new Item(root, "requirements");
	Map<Object, Item> requirementPK = new HashMap<>();
	
	Item projectRoot = new Item(root, "projects");
	Map<Object, Item> projectPK 				= new HashMap<>();
	Map<Object, Item> projectRequirementPK 		= new HashMap<>();
	Map<Object, Item> performanceMeasurementPK	= new HashMap<>();
	Map<Object, Item> testareaPK  				= new HashMap<>();
	Map<Object, Item> featurePK   				= new HashMap<>();
	Map<Object, Item> testcasePK  				= new HashMap<>();
	Map<Object, Item> scenarioPK  				= new HashMap<>();
	Map<Object, Item> procedurePK 				= new HashMap<>();

	/**
	 * constructor
	 * @param name the database name
	 * @param user the database user
	 * @param pswd the database password
	 */
	public DBS(String name, String user, String pswd) {
		super(name, user, pswd);
	}

	/**
	 * ADDITIONAL_INFORMATION
	 */
	public static class AdditionalInformation {

		private static final String TAG = "AdditionalInformation";

		/**
		 * the fields in the ADDITIONAL_INFORMATION table
		 */
		public enum Field {
			PK,
			KEY,
			DESCRIPTION
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s",item.get(Field.KEY));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbs.additionalInformationRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.additionalInformationPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+AdditionalInformation.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * AUTOMATED_PROCEDURE
	 */
	public static class AutomatedProcedure {

		private static final String TAG = "AutomatedProcedure";

		/**
		 * the fields in the AUTOMATED_PROCEDURE table
		 */
		public enum Field {
			PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", Procedure.getUniqueID(item));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object procedure_pk = result.get(Field.PK);

				Item procedure = dbs.procedurePK.get(procedure_pk);
				if(procedure==null) {
					System.err.println(Procedure.TAG+" (pk="+procedure_pk+")");
					continue;
				}
				
				if(DEBUG) System.out.println(TAG+" "+AutomatedProcedure.getUniqueID(procedure));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * AUXILIARY_ROUTINE
	 */
	public static class AuxiliaryRoutine {

		private static final String TAG = "AuxiliaryRoutine";

		/**
		 * the fields in the AUXILIARY_ROUTINE table
		 */
		public enum Field {
			PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", Procedure.getUniqueID(item));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object procedure_pk = result.get(Field.PK);

				Item procedure = dbs.procedurePK.get(procedure_pk);
				if(procedure==null) {
					System.err.println(Procedure.TAG+" (pk="+procedure_pk+")");
					continue;
				}
				
				if(DEBUG) System.out.println(TAG+" "+AuxiliaryRoutine.getUniqueID(procedure));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * BASELINE
	 */
	public static class Baseline {

		private static final String TAG = "Baseline";

		/**
		 * the fields in the BASELINE table
		 */
		public enum Field {
			PK,
			NAME,
			DESCRIPTION
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", item.get(Field.NAME));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbs.baselineRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.baselinePK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+Baseline.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
	}

	/**
	 * BASELINE_ITEM
	 */
	public static class BaselineItem {

		private static final String TAG = "BaselineItem";

		/**
		 * the fields in the BASELINE_ITEM table
		 */
		public enum Field {
			PK,
			ID,
			VERSION,
			BASELINE_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s",Baseline.getUniqueID(item.getParent()), item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object baseline_pk = result.get(Field.BASELINE_PK);
				Item baseline = dbs.baselinePK.get(baseline_pk);
				if(baseline==null) {
					System.err.println(Baseline.TAG+" (pk="+baseline_pk+")");
					continue;
				}

				Item item = new Item(baseline, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				if(DEBUG) System.out.println(TAG+" "+BaselineItem.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * DEPLOYMENT
	 */
	public static class Deployment {

		private static final String TAG = "Deployment";

		/**
		 * the fields in the DEPLOYMENT table
		 */
		public enum Field {
			PK,
			NAME,
			DESCRIPTION,
			MEASUREMENT_ONLY
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", item.get(Field.NAME));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbs.deploymentRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.deploymentPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+Deployment.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * EDITING_LOCK
	 */
	public static class EditingLock {

		private static final String TAG = "EditingLock";

		/**
		 * the fields in the EDITING_LOCK table
		 */
		public enum Field {
			PK,
			ID,
			OWNER,
			TYPE
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbs.lockRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.lockPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+EditingLock.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * FEATURE
	 */
	public static class Feature {

		private static final String TAG = "Feature";

		/**
		 * the fields in the FEATURE table
		 */
		public enum Field {
			PK,
			ID,
			TITLE,
			DESCRIPTION,
			TESTAREA_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", TestArea.getUniqueID(item.getParent()), item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object testarea_pk = result.get(Field.TESTAREA_PK);
				Item testarea = dbs.testareaPK.get(testarea_pk);
				if(testarea==null) {
					System.err.println(TestArea.TAG+" (pk="+testarea_pk+")");
					continue;
				}
				
				Item item = new Item(testarea, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.featurePK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+Feature.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * MANUAL_PROCEDURE
	 */
	public static class ManualProcedure {

		private static final String TAG = "ManualProcedure";

		/**
		 * the fields in the MANUAL_PROCEDURE table
		 */
		public enum Field {
			PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", Procedure.getUniqueID(item));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object procedure_pk = result.get(Field.PK);

				Item procedure = dbs.procedurePK.get(procedure_pk);
				if(procedure==null) {
					System.err.println(Procedure.TAG+" (pk="+procedure_pk+")");
					continue;
				}
				
				if(DEBUG) System.out.println(TAG+" "+ManualProcedure.getUniqueID(procedure));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * MANUAL_PROCEDURE_STEP
	 */
	public static class ManualProcedureStep {

		private static final String TAG = "ManualProcedureStep";

		/**
		 * the fields in the MANUAL_PROCEDURE_STEP table
		 */
		public enum Field {
			PK,
			STEP_NUMBER,
			ACTION,
			EXPECTED_RESULTS,
			COMMENTS,
			MANUAL_PROCEDURE_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%d", Procedure.getUniqueID(item.getParent()), item.get(Field.STEP_NUMBER));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object procedure_pk = result.get(Field.MANUAL_PROCEDURE_PK);

				Item procedure = dbs.procedurePK.get(procedure_pk);
				if(procedure==null) {
					System.err.println(Procedure.TAG+" (pk="+procedure_pk+")");
					continue;
				}

				Item item = new Item(procedure, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}

				if(DEBUG) System.out.println(TAG+" "+ManualProcedureStep.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * PERFORMANCE_MEASUREMENT
	 */
	public static class PerformanceMeasurement {

		private static final String TAG = "PerformanceMeasurement";

		/**
		 * the fields in the PERFORMANCE_MEASUREMENT table
		 */
		public enum Field {
			PK,
			KEY,
			DESCRIPTION,
			BASEVALUE,
			TARGETVALUE,
			PROJECT_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", Project.getUniqueID(item.getParent()), item.get(Field.KEY));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object project_pk = result.get(Field.PROJECT_PK);

				Item project = dbs.projectPK.get(project_pk);
				if(project==null) {
					System.err.println(Project.TAG+" (pk="+project_pk+")");
					continue;
				}

				Item item = new Item(project, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.performanceMeasurementPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+PerformanceMeasurement.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * PROCEDURE
	 */
	public static class Procedure {

		private static final String TAG = "Procedure";

		/**
		 * the fields in the PROCEDURE table
		 */
		public enum Field {
			PK,
			TYPE,
			ID,
			TITLE,
			DESCRIPTION,
			SCENARIO_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", Scenario.getUniqueID(item.getParent()), item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object scenario_pk = result.get(Field.SCENARIO_PK);
				Item scenario = dbs.scenarioPK.get(scenario_pk);
				if(scenario==null) {
					System.err.println(Scenario.TAG+" (pk="+scenario_pk+")");
					continue;
				}
				
				Item item = new Item(scenario, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.procedurePK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+Procedure.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * PROCEDURE_TESTCASE
	 */
	public static class ProcedureTestcase {

		private static final String TAG = "ProcedureTestcase";

		/**
		 * the fields in the PROCEDURE_TESTCASE table
		 */
		public enum Field {
			PROCEDURE_PK,
			TESTCASES_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", Procedure.getUniqueID(item.getParent()), TestCase.getUniqueID((Item)item.get(TestCase.TAG)));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object procedure_pk = result.get(Field.PROCEDURE_PK);
				Item procedure = dbs.procedurePK.get(procedure_pk);
				if(procedure==null) {
					System.err.println(ProcedureTestcase.TAG+" invalid procedure (pk="+procedure_pk+")");
					continue;
				}

				Object testcases_pk = result.get(Field.TESTCASES_PK);
				Item testcase = dbs.testcasePK.get(testcases_pk);
				if(testcase==null) {
					System.err.println(ProcedureTestcase.TAG+" invalid testcase (pk="+testcases_pk+")");
					continue;
				}

				Item reference = new Item(procedure, "reference");
				reference.set(TestCase.TAG, testcase);

				if(DEBUG) System.out.println(TAG+" "+ProcedureTestcase.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * PROJECT
	 */
	public static class Project {

		private static final String TAG = "Project";

		/**
		 * the fields in the PROJECT table
		 */
		public enum Field {
			PK,
			TYPE,
			ID ,
			VERSION,
			ARTIFACT,
			PACKAGE,
			BASEFOLDER,
			TARGETFOLDER,
			PARENT_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbs.projectRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.projectPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+Project.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * PROJECT_REQUIREMENT
	 */
	public static class ProjectRequirement {

		private static final String TAG = "ProjectRequirement";

		/**
		 * the fields in the PROJECT_REQUIREMENT table
		 */
		public enum Field {
			PK,
			REQUIREMENT_ID,
			STATUS,
			RFW,
			VERIFICATIONSTAGE,
			COMMENT,
			PROJECT_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", Project.getUniqueID(item.getParent()), item.get(Field.REQUIREMENT_ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				
				Object project_pk = result.get(Field.PROJECT_PK);
				Item project = dbs.projectPK.get(project_pk);
				if(project==null) {
					System.err.println(Project.TAG+" (pk="+project_pk+")");
					continue;
				}

				Item item = new Item(project, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.projectRequirementPK.put(item.get(Field.PK), item);

				Object requirement_id = result.get(Field.REQUIREMENT_ID);
				Item requirement = dbs.requirementPK.get(requirement_id);
				if(requirement==null) {
					System.err.println(Requirement.TAG+" (pk="+requirement_id+")");
					continue;
				}

				Item reference = new Item(item, "reference");
				reference.set(Requirement.TAG, requirement);

				if(DEBUG) System.out.printf("%s: %s\n", TAG, ProjectRequirement.getUniqueID(item));
			}
			if(DEBUG) System.out.printf("%d %s\n", dbs.count(table), TAG);
		}

	}

	/**
	 * PROJECT_REQUIREMENT_DEPLOYMENT
	 */
	public static class ProjectRequirementDeployment {

		private static final String TAG = "ProjectRequirementDeployment";

		/**
		 * the fields in the PROJECT_REQUIREMENT_DEPLOYMENT table
		 */
		public enum Field {
			REQUIREMENT_PK,
			DEPLOYMENTS_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", ProjectRequirement.getUniqueID(item.getParent()), Deployment.getUniqueID((Item)item.get(Deployment.TAG)));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object requirement_pk = result.get(Field.REQUIREMENT_PK);
				Item requirement = dbs.projectRequirementPK.get(requirement_pk);
				if(requirement==null) {
					System.err.println(ProjectRequirement.TAG+" (pk="+requirement_pk+")");
					continue;
				}

				Object deployments_pk = result.get(Field.DEPLOYMENTS_PK);
				Item deployment = dbs.deploymentPK.get(deployments_pk);
				if(deployment==null) {
					System.err.println(Deployment.TAG+" (pk="+deployments_pk+")");
					continue;
				}

				Item reference = new Item(requirement, "reference");
				reference.set(Deployment.TAG, deployment);

				if(DEBUG) System.out.println(TAG+" "+ProjectRequirementDeployment.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * REQUIREMENT
	 */
	public static class Requirement {

		private static final String TAG = "Requirement";

		/**
		 * the fields in the REQUIREMENT table
		 */
		public enum Field {
			TYPE,
			ID,
			NAME,
			DESCRIPTION,
			IMPORT_DATE,
			IMPORT_FILE,
			PRIORITY,
			VERIFICATION,
			VERSION
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Item item = new Item(dbs.requirementRoot, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.requirementPK.put(item.get(Field.ID), item);
				if(DEBUG) System.out.println(TAG+" "+Requirement.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * REQUIREMENT_DEPLOYMENT
	 */
	public static class RequirementDeployment {

		private static final String TAG = "RequirementDeployment";

		/**
		 * the fields in the REQUIREMENT_DEPLOYMENT table
		 */
		public enum Field {
			REQUIREMENT_ID,
			DEPLOYMENTS_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", Requirement.getUniqueID(item.getParent()), Deployment.getUniqueID((Item)item.get(Deployment.TAG)));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object requirement_id = result.get(Field.REQUIREMENT_ID);
				Item requirement = dbs.requirementPK.get(requirement_id);
				if(requirement==null) {
					System.err.println(Requirement.TAG+" (id="+requirement_id+")");
					continue;
				}

				Object deployments_pk = result.get(Field.DEPLOYMENTS_PK);
				Item deployment = dbs.deploymentPK.get(deployments_pk);
				if(deployment==null) {
					System.err.println(Deployment.TAG+" (pk="+deployments_pk+")");
					continue;
				}

				Item reference = new Item(requirement, "reference");
				reference.set(Deployment.TAG, deployment);

				if(DEBUG) System.out.println(TAG+" "+RequirementDeployment.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * SCENARIO
	 */
	public static class Scenario {

		private static final String TAG = "Scenario";

		/**
		 * the fields in the SCENARIO table
		 */
		public enum Field {
			PK,
			TYPE,
			ID,
			TITLE,
			DESCRIPTION,
			RESOURCES,
			TESTAREA_PK,
			PROJECT_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", Project.getUniqueID(item.getParent()), item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object project_pk = result.get(Field.PROJECT_PK);

				Item project = dbs.projectPK.get(project_pk);
				if(project==null) {
					System.err.println(Project.TAG+" (pk="+project_pk+")");
					continue;
				}

				Item item = new Item(project, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.scenarioPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+Scenario.getUniqueID(item));
				
				Object testarea_pk = result.get(Field.TESTAREA_PK);

				Item testarea = dbs.testareaPK.get(testarea_pk);
				if(testarea==null) {
					System.err.println(TestArea.TAG+" (pk="+testarea_pk+")");
					continue;
				}

				Item reference = new Item(item, "reference");
				reference.set(TestArea.TAG, testarea);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SCENARIO_ADDITIONAL_INFORMATION
	 */
	public static class ScenarioAdditionalInformation {

		private static final String TAG = "ScenarioAdditionalInformation";

		/**
		 * the fields in the SCENARIO_ADDITIONAL_INFORMATION table
		 */
		public enum Field {
			SCENARIO_PK,
			INFORMATIONS_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", Scenario.getUniqueID(item.getParent()), AdditionalInformation.getUniqueID((Item)item.get(AdditionalInformation.TAG)));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object scenario_pk = result.get(Field.SCENARIO_PK);
				Item scenario = dbs.scenarioPK.get(scenario_pk);
				if(scenario==null) {
					System.err.println(Scenario.TAG+" (pk="+scenario_pk+")");
					continue;
				}

				Object informations_pk = result.get(Field.INFORMATIONS_PK);
				Item information = dbs.additionalInformationPK.get(informations_pk);
				if(information==null) {
					System.err.println(AdditionalInformation.TAG+" (pk="+informations_pk+")");
					continue;
				}

				Item reference = new Item(scenario, "reference");
				reference.set(AdditionalInformation.TAG, information);

				if(DEBUG) System.out.println(TAG+" "+ScenarioAdditionalInformation.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SCENARIO_DEPLOYMENT
	 */
	public static class ScenarioDeployment {

		private static final String TAG = "ScenarioDeployment";

		/**
		 * the fields in the SCENARIO_DEPLOYMENT table
		 */
		public enum Field {
			SCENARIO_PK,
			DEPLOYMENTS_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", Scenario.getUniqueID(item.getParent()), Deployment.getUniqueID((Item)item.get(Deployment.TAG)));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object scenario_pk = result.get(Field.SCENARIO_PK);
				Item scenario = dbs.scenarioPK.get(scenario_pk);
				if(scenario==null) {
					System.err.println(Scenario.TAG+" (pk="+scenario_pk+")");
					continue;
				}

				Object deployments_pk = result.get(Field.DEPLOYMENTS_PK);
				Item deployment = dbs.deploymentPK.get(deployments_pk);
				if(deployment==null) {
					System.err.println(Deployment.TAG+" (pk="+deployments_pk+")");
					continue;
				}

				Item reference = new Item(scenario, "reference");
				reference.set(Deployment.TAG, deployment);

				if(DEBUG) System.out.println(TAG+" "+ScenarioDeployment.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SCENARIO_PERFORMANCE_MEASUREMENT
	 */
	public static class ScenarioPerformanceMeasurement {

		private static final String TAG = "ScenarioPerformanceMeasurement";

		/**
		 * the fields in the SCENARIO_PERFORMANCE_MEASUREMENT table
		 */
		public enum Field {
			SCENARIO_PK,
			MEASUREMENTS_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", Scenario.getUniqueID(item.getParent()), PerformanceMeasurement.getUniqueID((Item)item.get(PerformanceMeasurement.TAG)));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object scenario_pk = result.get(Field.SCENARIO_PK);
				Item scenario = dbs.scenarioPK.get(scenario_pk);
				if(scenario==null) {
					System.err.println(Scenario.TAG+" (pk="+scenario_pk+")");
					continue;
				}

				Object measurements_pk = result.get(Field.MEASUREMENTS_PK);
				Item measurement = dbs.performanceMeasurementPK.get(measurements_pk);
				if(measurement==null) {
					System.err.println(PerformanceMeasurement.TAG+" (pk="+measurements_pk+")");
					continue;
				}

				Item reference = new Item(scenario, "reference");
				reference.set(PerformanceMeasurement.TAG, measurement);

				if(DEBUG) System.out.println(TAG+" "+ScenarioPerformanceMeasurement.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SOFTWARE_REQUIREMENT
	 */
	public static class SoftwareRequirement {

		private static final String TAG = "SoftwareRequirement";

		/**
		 * the fields in the SOFTWARE_REQUIREMENT table
		 */
		public enum Field {
			ID,
			COMMENT,
			STABILITY,
			STRUCTURE
		}
		
		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				
				Object id = result.get(Field.ID);
				Item requirement = dbs.requirementPK.get(id);
				if(requirement==null) {
					System.err.println(Requirement.TAG+" (id="+id+")");
					continue;
				}
				
				Item item = new Item(requirement, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}

				if(DEBUG) System.out.println(TAG+" "+SoftwareRequirement.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SOFTWARE_REQUIREMENT_USER_REQUIREMENT
	 */
	public static class SoftwareRequirementUserRequirement {

		private static final String TAG = "SoftwareRequirementUserRequirement";

		/**
		 * the fields in the SOFTWARE_REQUIREMENT_USER_REQUIREMENT table
		 */
		public enum Field {
			SOFT_REQ_ID,
			USER_REQ_ID
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", SoftwareRequirement.getUniqueID(item.getParent()), UserRequirement.getUniqueID((Item)item.get(UserRequirement.TAG)));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object user_req_id = result.get(Field.USER_REQ_ID);
				Item ur = dbs.requirementPK.get(user_req_id);
				if(ur==null) {
					System.err.println(UserRequirement.TAG+" (id="+user_req_id+")");
					continue;
				}
				
				Object soft_req_id = result.get(Field.SOFT_REQ_ID);
				Item requirement = dbs.requirementPK.get(soft_req_id);
				if(requirement==null) {
					System.err.println(Requirement.TAG+" (id="+soft_req_id+")");
					continue;
				}
				
				Item sr = null;
				for(Item child : requirement.getChildren()) {
					if(child.getName().equals(SoftwareRequirement.TAG)) {
						sr = child;
					}
				}
				
				if(sr==null) {
					System.err.println(SoftwareRequirement.TAG+" (id="+soft_req_id+")");
					continue;
				}
	
				Item reference = new Item(sr, "reference");
				reference.set(UserRequirement.TAG, ur);

				if(DEBUG) System.out.println(TAG+" "+SoftwareRequirementUserRequirement.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * TESTAREA
	 */
	public static class TestArea {

		private static final String TAG = "TestArea";

		/**
		 * the fields in the TESTAREA table
		 */
		public enum Field {
			PK,
			ID,
			TITLE,
			DESCRIPTION,
			APPROACH,
			PROJECT_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", Project.getUniqueID(item.getParent()), item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object project_pk = result.get(Field.PROJECT_PK);

				Item project = dbs.projectPK.get(project_pk);
				if(project==null) {
					System.err.println(Project.TAG+" (pk="+project_pk+")");
					continue;
				}

				Item item = new Item(project, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.testareaPK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+TestArea.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
	
	}

	/**
	 * TESTCASE
	 */
	public static class TestCase {

		private static final String TAG = "TestCase";

		/**
		 * the fields in the TESTCASE table
		 */
		public enum Field {
			PK,
			ID,
			TITLE,
			SPECIFICATION,
			SCOPE,
			CRITERIA,
			COMMENT,
			FEATURE_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s-%s", Feature.getUniqueID(item.getParent()), item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object feature_pk = result.get(Field.FEATURE_PK);
				Item feature = dbs.featurePK.get(feature_pk);
				if(feature==null) {
					System.err.println(Feature.TAG+" (pk="+feature_pk+")");
					continue;
				}

				Item item = new Item(feature, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}
				dbs.testcasePK.put(item.get(Field.PK), item);
				if(DEBUG) System.out.println(TAG+" "+Feature.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * TESTCASE_PROJECT_REQUIREMENT
	 */
	public static class TestcaseProjectRequirement {

		private static final String TAG = "TestcaseProjectRequirement";

		/**
		 * the fields in the TESTCASE_PROJECT_REQUIREMENT table
		 */
		public enum Field {
			TESTCASE_PK,
			REQUIREMENTS_PK
		}

		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s=%s", TestCase.getUniqueID(item.getParent()), ProjectRequirement.getUniqueID((Item)item.get(ProjectRequirement.TAG)));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object testcase_pk = result.get(Field.TESTCASE_PK);

				Item testcase = dbs.testcasePK.get(testcase_pk);
				if(testcase==null) {
					System.err.println(TestcaseProjectRequirement.TAG+" invalid testcase (pk="+testcase_pk+")");
					continue;
				}

				Object requirements_pk = result.get(Field.REQUIREMENTS_PK);

				Item requirement = dbs.projectRequirementPK.get(requirements_pk);
				if(requirement==null) {
					System.err.println(TestcaseProjectRequirement.TAG+" invalid project-requirement (pk="+requirements_pk+")");
					continue;
				}

				Item reference = new Item(testcase, "reference");
				reference.set(ProjectRequirement.TAG, requirement);

				if(DEBUG) System.out.println(TAG+" "+TestcaseProjectRequirement.getUniqueID(reference));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
	}

	/**
	 * USER_REQUIREMENT
	 */
	public static class UserRequirement {

		private static final String TAG = "UserRequirement";

		/**
		 * the fields in the USER_REQUIREMENT table
		 */
		public enum Field {
			ID,
			NOTE,
			JUSTIFICATION,
			LAST_CHANGED,
			LEVEL,
			TYPE
		}
		
		/**
		 * @param item
		 * @return the corresponding unique id
		 */
		public static String getUniqueID(Item item) {
			return String.format("%s", item.get(Field.ID));
		}

		/**
		 * @param dbs the DataBase Specification 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				
				Object id = result.get(Field.ID);
				Item requirement = dbs.requirementPK.get(id);
				if(requirement==null) {
					System.err.println(Requirement.TAG+" (id="+id+")");
					continue;
				}
				
				Item item = new Item(requirement, TAG);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						item.set(key, val);
					}
				}

				if(DEBUG) System.out.println(TAG+" "+UserRequirement.getUniqueID(item));
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

}
