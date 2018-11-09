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
	Map<Object, AdditionalInformation> additionalInformationPK = new HashMap<>();
	
	Item baselineRoot = new Item(root, "baselines");
	Map<Object, Baseline> baselinePK = new HashMap<>();
	
	Item deploymentRoot = new Item(root, "deployments");
	Map<Object, Deployment> deploymentPK = new HashMap<>();
	
	Item lockRoot = new Item(root, "locks");
	Map<Object, EditingLock> lockPK = new HashMap<>();
	
	Item requirementRoot = new Item(root, "requirements");
	Map<Object, Requirement> requirementPK = new HashMap<>();
	
	Item projectRoot = new Item(root, "projects");
	Map<Object, Project> projectPK = new HashMap<>();
	Map<Object, ProjectRequirement> projectRequirementPK = new HashMap<>();
	Map<Object, PerformanceMeasurement> performanceMeasurementPK = new HashMap<>();
	Map<Object, TestArea> testareaPK = new HashMap<>();
	Map<Object, Feature> featurePK = new HashMap<>();
	Map<Object, TestCase> testcasePK = new HashMap<>();
	Map<Object, Scenario> scenarioPK = new HashMap<>();
	Map<Object, Procedure> procedurePK = new HashMap<>();

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
	public static class AdditionalInformation extends Item {

		public static final String TAG = "AdditionalInformation";

		/**
		 * the fields in the ADDITIONAL_INFORMATION table
		 */
		public enum Field {
			PK,
			KEY,
			DESCRIPTION
		}

		public AdditionalInformation(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s", get(Field.KEY) );
		}

		/**
		 * @param dbs the specification database 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				AdditionalInformation additionalInformation = new AdditionalInformation(dbs.additionalInformationRoot);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						additionalInformation.set(key, val);
					}
				}
				dbs.additionalInformationPK.put(additionalInformation.get(Field.PK), additionalInformation);
				if(DEBUG) System.out.println(TAG+" "+additionalInformation);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * AUTOMATED_PROCEDURE
	 */
	public static class AutomatedProcedure extends Item {

		public static final String TAG = "AutomatedProcedure";

		/**
		 * the fields in the AUTOMATED_PROCEDURE table
		 */
		public enum Field {
			PK
		}

		public AutomatedProcedure(Item parent) {
			super(parent, TAG);
		}

		/**
		 * @param dbs the specification database 
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
					System.err.println(TAG+" invalid "+Procedure.TAG+" (pk="+procedure_pk+")");
					continue;
				}
				
				if(DEBUG) System.out.println(TAG+" "+procedure);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * AUXILIARY_ROUTINE
	 */
	public static class AuxiliaryRoutine extends Item {

		public static final String TAG = "AuxiliaryRoutine";

		/**
		 * the fields in the AUXILIARY_ROUTINE table
		 */
		public enum Field {
			PK
		}

		public AuxiliaryRoutine(Item parent) {
			super(parent, TAG);
		}

		/**
		 * @param dbs the specification database 
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
					System.err.println(TAG+" invalid "+Procedure.TAG+" (pk="+procedure_pk+")");
					continue;
				}
				
				if(DEBUG) System.out.println(TAG+" "+procedure);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * BASELINE
	 */
	public static class Baseline extends Item {

		public static final String TAG = "Baseline";

		/**
		 * the fields in the BASELINE table
		 */
		public enum Field {
			PK,
			NAME,
			DESCRIPTION
		}

		public Baseline(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s", get(Field.NAME));
		}

		/**
		 * @param dbs the specification database 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Baseline baseline = new Baseline(dbs.baselineRoot);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						baseline.set(key, val);
					}
				}
				dbs.baselinePK.put(baseline.get(Field.PK), baseline);
				if(DEBUG) System.out.println(TAG+" "+baseline);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
	}

	/**
	 * BASELINE_ITEM
	 */
	public static class BaselineItem extends Item {

		public static final String TAG = "BaselineItem";

		/**
		 * the fields in the BASELINE_ITEM table
		 */
		public enum Field {
			PK,
			ID,
			VERSION,
			BASELINE_PK
		}

		public BaselineItem(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s-%s", getParent(), get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {

				Object baseline_pk = result.get(Field.BASELINE_PK);
				Baseline baseline = (Baseline)dbs.baselinePK.get(baseline_pk);
				if(baseline==null) {
					System.err.println(Baseline.TAG+" (pk="+baseline_pk+")");
					continue;
				}

				BaselineItem baselineItem = new BaselineItem(baseline);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						baselineItem.set(key, val);
					}
				}
				if(DEBUG) System.out.println(TAG+" "+baselineItem);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * DEPLOYMENT
	 */
	public static class Deployment extends Item {

		public static final String TAG = "Deployment";

		/**
		 * the fields in the DEPLOYMENT table
		 */
		public enum Field {
			PK,
			NAME,
			DESCRIPTION,
			MEASUREMENT_ONLY
		}

		public Deployment(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s", get(Field.NAME));
		}

		/**
		 * @param dbs the specification database 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Deployment deployment = new Deployment(dbs.deploymentRoot);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						deployment.set(key, val);
					}
				}
				dbs.deploymentPK.put(deployment.get(Field.PK), deployment);
				if(DEBUG) System.out.println(TAG+" "+deployment);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * EDITING_LOCK
	 */
	public static class EditingLock extends Item {

		public static final String TAG = "EditingLock";

		/**
		 * the fields in the EDITING_LOCK table
		 */
		public enum Field {
			PK,
			ID,
			OWNER,
			TYPE
		}

		public EditingLock(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s", get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				EditingLock lock = new EditingLock(dbs.lockRoot);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						lock.set(key, val);
					}
				}
				dbs.lockPK.put(lock.get(Field.PK), lock);
				if(DEBUG) System.out.println(TAG+" "+lock);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * FEATURE
	 */
	public static class Feature extends Item {

		public static final String TAG = "Feature";

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

		public Feature(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s-%s", getParent(), get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
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
				
				Feature feature = new Feature(testarea);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						feature.set(key, val);
					}
				}
				dbs.featurePK.put(feature.get(Field.PK), feature);
				if(DEBUG) System.out.println(TAG+" "+feature);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * MANUAL_PROCEDURE
	 */
	public static class ManualProcedure extends Item {

		public static final String TAG = "ManualProcedure";

		/**
		 * the fields in the MANUAL_PROCEDURE table
		 */
		public enum Field {
			PK
		}

		public ManualProcedure(Item parent) {
			super(parent, TAG);
		}

		/**
		 * @param dbs the specification database 
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
				
				if(DEBUG) System.out.println(TAG+" "+procedure);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * MANUAL_PROCEDURE_STEP
	 */
	public static class ManualProcedureStep extends Item {

		public static final String TAG = "ManualProcedureStep";

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

		public ManualProcedureStep(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s-%d", getParent(), get(Field.STEP_NUMBER));
		}

		/**
		 * @param dbs the specification database 
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

				ManualProcedureStep step = new ManualProcedureStep(procedure);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						step.set(key, val);
					}
				}

				if(DEBUG) System.out.println(TAG+" "+step);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * PERFORMANCE_MEASUREMENT
	 */
	public static class PerformanceMeasurement extends Item {

		public static final String TAG = "PerformanceMeasurement";

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

		public PerformanceMeasurement(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s-%s", getParent(), get(Field.KEY));
		}

		/**
		 * @param dbs the specification database 
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

				PerformanceMeasurement performanceMeasurement = new PerformanceMeasurement(project);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						performanceMeasurement.set(key, val);
					}
				}
				dbs.performanceMeasurementPK.put(performanceMeasurement.get(Field.PK), performanceMeasurement);
				if(DEBUG) System.out.println(TAG+" "+performanceMeasurement);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * PROCEDURE
	 */
	public static class Procedure extends Item {

		public static final String TAG = "Procedure";

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

		public Procedure(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s-%s", getParent(), get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
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
				
				Procedure procedure = new Procedure(scenario);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						procedure.set(key, val);
					}
				}
				dbs.procedurePK.put(procedure.get(Field.PK), procedure);
				if(DEBUG) System.out.println(TAG+" "+procedure);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * PROCEDURE_TESTCASE
	 */
	public static class ProcedureTestCase extends Item {

		private static final String TAG = "ProcedureTestCase";

		/**
		 * the fields in the PROCEDURE_TESTCASE table
		 */
		public enum Field {
			PROCEDURE_PK,
			TESTCASES_PK
		}

		public ProcedureTestCase(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s=%s", getParent(), get(TestCase.TAG));
		}

		/**
		 * @param dbs the specification database 
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
					System.err.println(ProcedureTestCase.TAG+" invalid procedure (pk="+procedure_pk+")");
					continue;
				}

				Object testcases_pk = result.get(Field.TESTCASES_PK);
				Item testcase = dbs.testcasePK.get(testcases_pk);
				if(testcase==null) {
					System.err.println(ProcedureTestCase.TAG+" invalid testcase (pk="+testcases_pk+")");
					continue;
				}

				ProcedureTestCase reference = new ProcedureTestCase(procedure);
				reference.set(TestCase.TAG, testcase);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * PROJECT
	 */
	public static class Project extends Item {

		public static final String TAG = "Project";

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

		public Project(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s", get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Project project = new Project(dbs.projectRoot);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						project.set(key, val);
					}
				}
				dbs.projectPK.put(project.get(Field.PK), project);
				if(DEBUG) System.out.println(TAG+" "+project);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * PROJECT_REQUIREMENT
	 */
	public static class ProjectRequirement extends Item {

		public static final String TAG = "ProjectRequirement";

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

		public ProjectRequirement(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s:%s", getParent(), get(Field.REQUIREMENT_ID));
		}

		/**
		 * @param dbs the specification database 
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

				ProjectRequirement projectRequirement = new ProjectRequirement(project);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						projectRequirement.set(key, val);
					}
				}
				dbs.projectRequirementPK.put(projectRequirement.get(Field.PK), projectRequirement);

				Object requirement_id = result.get(Field.REQUIREMENT_ID);
				Requirement requirement = dbs.requirementPK.get(requirement_id);
				if(requirement==null) {
					System.err.println(Requirement.TAG+" (pk="+requirement_id+")");
					continue;
				}

				ProjectRequirement reference = new ProjectRequirement(projectRequirement);
				reference.set(Requirement.TAG, requirement);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * PROJECT_REQUIREMENT_DEPLOYMENT
	 */
	public static class ProjectRequirementDeployment extends Item {

		public static final String TAG = "ProjectRequirementDeployment";

		/**
		 * the fields in the PROJECT_REQUIREMENT_DEPLOYMENT table
		 */
		public enum Field {
			REQUIREMENT_PK,
			DEPLOYMENTS_PK
		}

		public ProjectRequirementDeployment(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s=%s", getParent(), get(Deployment.TAG));
		}

		/**
		 * @param dbs the specification database 
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

				ProjectRequirementDeployment reference = new ProjectRequirementDeployment(requirement);
				reference.set(Deployment.TAG, deployment);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * REQUIREMENT
	 */
	public static class Requirement extends Item {

		public static final String TAG = "Requirement";

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

		public Requirement(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s", get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
		 * @param table the table name
		 * @param keys the key/field mapping
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(DBS dbs, String table, Map<Object, String> keys) throws NotConnectedException, SQLException {
			List<Properties> results = dbs.query(table, keys);
			for(Properties result : results) {
				Requirement requirement = new Requirement(dbs.requirementRoot);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						requirement.set(key, val);
					}
				}
				dbs.requirementPK.put(requirement.get(Field.ID), requirement);
				if(DEBUG) System.out.println(TAG+" "+requirement);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * REQUIREMENT_DEPLOYMENT
	 */
	public static class RequirementDeployment extends Item {

		public static final String TAG = "RequirementDeployment";

		/**
		 * the fields in the REQUIREMENT_DEPLOYMENT table
		 */
		public enum Field {
			REQUIREMENT_ID,
			DEPLOYMENTS_PK
		}

		public RequirementDeployment(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s=%s", getParent(), get(Deployment.TAG));
		}

		/**
		 * @param dbs the specification database 
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

				RequirementDeployment reference = new RequirementDeployment(requirement);
				reference.set(Deployment.TAG, deployment);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}

	}

	/**
	 * SCENARIO
	 */
	public static class Scenario extends Item {

		public static final String TAG = "Scenario";

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

		public Scenario(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s:%s", getParent(), get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
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

				Scenario scenario = new Scenario(project);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						scenario.set(key, val);
					}
				}
				dbs.scenarioPK.put(scenario.get(Field.PK), scenario);
				if(DEBUG) System.out.println(TAG+" "+scenario);
				
				Object testarea_pk = result.get(Field.TESTAREA_PK);
				Item testarea = dbs.testareaPK.get(testarea_pk);
				if(testarea==null) {
					System.err.println(TestArea.TAG+" (pk="+testarea_pk+")");
					continue;
				}

				scenario.set(TestArea.TAG, testarea);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SCENARIO_ADDITIONAL_INFORMATION
	 */
	public static class ScenarioAdditionalInformation extends Item {

		public static final String TAG = "ScenarioAdditionalInformation";

		/**
		 * the fields in the SCENARIO_ADDITIONAL_INFORMATION table
		 */
		public enum Field {
			SCENARIO_PK,
			INFORMATIONS_PK
		}

		public ScenarioAdditionalInformation(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s=%s", getParent(), get(AdditionalInformation.TAG));
		}

		/**
		 * @param dbs the specification database 
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

				ScenarioAdditionalInformation reference = new ScenarioAdditionalInformation(scenario);
				reference.set(AdditionalInformation.TAG, information);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SCENARIO_DEPLOYMENT
	 */
	public static class ScenarioDeployment extends Item {

		public static final String TAG = "ScenarioDeployment";

		/**
		 * the fields in the SCENARIO_DEPLOYMENT table
		 */
		public enum Field {
			SCENARIO_PK,
			DEPLOYMENTS_PK
		}

		public ScenarioDeployment(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s=%s", getParent(), get(Deployment.TAG));
		}

		/**
		 * @param dbs the specification database 
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

				ScenarioDeployment reference = new ScenarioDeployment(scenario);
				reference.set(Deployment.TAG, deployment);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SCENARIO_PERFORMANCE_MEASUREMENT
	 */
	public static class ScenarioPerformanceMeasurement extends Item {

		public static final String TAG = "ScenarioPerformanceMeasurement";

		/**
		 * the fields in the SCENARIO_PERFORMANCE_MEASUREMENT table
		 */
		public enum Field {
			SCENARIO_PK,
			MEASUREMENTS_PK
		}

		public ScenarioPerformanceMeasurement(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s=%s", getParent(), get(PerformanceMeasurement.TAG));
		}

		/**
		 * @param dbs the specification database 
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

				ScenarioPerformanceMeasurement reference = new ScenarioPerformanceMeasurement(scenario);
				reference.set(PerformanceMeasurement.TAG, measurement);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SOFTWARE_REQUIREMENT
	 */
	public static class SoftwareRequirement extends Item {

		public static final String TAG = "SoftwareRequirement";

		/**
		 * the fields in the SOFTWARE_REQUIREMENT table
		 */
		public enum Field {
			ID,
			COMMENT,
			STABILITY,
			STRUCTURE
		}
		
		public SoftwareRequirement(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s", get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
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
				
				SoftwareRequirement softwareRequirement = new SoftwareRequirement(requirement);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						softwareRequirement.set(key, val);
					}
				}

				if(DEBUG) System.out.println(TAG+" "+softwareRequirement);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * SOFTWARE_REQUIREMENT_USER_REQUIREMENT
	 */
	public static class SoftwareRequirementUserRequirement extends Item {

		public static final String TAG = "SoftwareRequirementUserRequirement";

		/**
		 * the fields in the SOFTWARE_REQUIREMENT_USER_REQUIREMENT table
		 */
		public enum Field {
			SOFT_REQ_ID,
			USER_REQ_ID
		}

		public SoftwareRequirementUserRequirement(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s=%s", getParent(), get(UserRequirement.TAG));
		}

		/**
		 * @param dbs the specification database 
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
					if(child.getTag().equals(SoftwareRequirement.TAG)) {
						sr = child;
					}
				}
				
				if(sr==null) {
					System.err.println(SoftwareRequirement.TAG+" (id="+soft_req_id+")");
					continue;
				}
	
				SoftwareRequirementUserRequirement reference = new SoftwareRequirementUserRequirement(sr);
				reference.set(UserRequirement.TAG, ur);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * TESTAREA
	 */
	public static class TestArea extends Item {

		public static final String TAG = "TestArea";

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

		public TestArea(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s:%s", getParent(), get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
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

				TestArea testArea = new TestArea(project);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						testArea.set(key, val);
					}
				}
				dbs.testareaPK.put(testArea.get(Field.PK), testArea);
				if(DEBUG) System.out.println(TAG+" "+testArea);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
	
	}

	/**
	 * TESTCASE
	 */
	public static class TestCase extends Item {

		public static final String TAG = "TestCase";

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

		public TestCase(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			String id = (String)get(Field.ID);
			return String.format("%s-%s", getParent(), id.substring(id.lastIndexOf('-')+1));
		}

		/**
		 * @param dbs the specification database 
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

				TestCase testcase = new TestCase(feature);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						testcase.set(key, val);
					}
				}
				dbs.testcasePK.put(testcase.get(Field.PK), testcase);
				if(DEBUG) System.out.println(TAG+" "+testcase);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

	/**
	 * TESTCASE_PROJECT_REQUIREMENT
	 */
	public static class TestCaseProjectRequirement extends Item {

		public static final String TAG = "TestCaseProjectRequirement";

		/**
		 * the fields in the TESTCASE_PROJECT_REQUIREMENT table
		 */
		public enum Field {
			TESTCASE_PK,
			REQUIREMENTS_PK
		}

		public TestCaseProjectRequirement(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s=%s", getParent(), get(ProjectRequirement.TAG));
		}

		/**
		 * @param dbs the specification database 
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
					System.err.println(TestCaseProjectRequirement.TAG+" invalid testcase (pk="+testcase_pk+")");
					continue;
				}

				Object projectRequirements_pk = result.get(Field.REQUIREMENTS_PK);

				ProjectRequirement projectRequirement = dbs.projectRequirementPK.get(projectRequirements_pk);
				if(projectRequirement==null) {
					System.err.println(TestCaseProjectRequirement.TAG+" invalid "+ProjectRequirement.TAG+" (pk="+projectRequirements_pk+")");
					continue;
				}
	
				TestCaseProjectRequirement reference = new TestCaseProjectRequirement(testcase);
				reference.set(ProjectRequirement.TAG, projectRequirement);

				if(DEBUG) System.out.println(TAG+" "+reference);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
	}

	/**
	 * USER_REQUIREMENT
	 */
	public static class UserRequirement extends Item {

		public static final String TAG = "UserRequirement";

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
		
		public UserRequirement(Item parent) {
			super(parent, TAG);
		}

		/** {@inheritDoc} */
		@Override
		public String toString() {
			return String.format("%s", get(Field.ID));
		}

		/**
		 * @param dbs the specification database 
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
				
				UserRequirement userRequirement = new UserRequirement(requirement);
				for(Object key : keys.keySet()) {
					Object val = result.get(key);
					if(val!=null) {
						userRequirement.set(key, val);
					}
				}

				if(DEBUG) System.out.println(TAG+" "+userRequirement);
			}
			if(DEBUG) System.out.println(TAG+" "+dbs.count(table));
		}
		
	}

}
