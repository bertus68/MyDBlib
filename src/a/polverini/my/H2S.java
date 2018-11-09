package a.polverini.my;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a.polverini.my.exceptions.NotConnectedException;

public class H2S extends DBS {

	private final String 	host;
	private final int 		port;

	/**
	 * @return the URL string for this database
	 */
	public String getURL() {
		return String.format("jdbc:h2:tcp://%s:%s/c:/data/%s", host, port, getName());
	}

	/**
	 * retrieve the data from the specification database
	 * @return a list of items
	 * @throws NotConnectedException
	 * @throws SQLException
	 */
	public List<Item> query() throws NotConnectedException, SQLException {
		
		// AdditionalInformation
		AdditionalInformation.query(this);

		// Baseline
		Baseline.query(this);
		BaselineItem.query(this);

		// Deployment
		Deployment.query(this);
		
		// EditingLock
		EditingLock.query(this);
		
		// Requirement
		Requirement.query(this);
		RequirementDeployment.query(this);
		UserRequirement.query(this);
		SoftwareRequirement.query(this);
		SoftwareRequirementUserRequirement.query(this);

		// Project
		Project.query(this);
		ProjectRequirement.query(this);
		ProjectRequirementDeployment.query(this);
		PerformanceMeasurement.query(this);
		
		// TestArea
		TestArea.query(this);
		Feature.query(this);
		TestCase.query(this);
		TestCaseProjectRequirement.query(this);
		
		// Scenario
		Scenario.query(this);
		ScenarioAdditionalInformation.query(this);
		ScenarioDeployment.query(this);
		ScenarioPerformanceMeasurement.query(this);
		Procedure.query(this);
		ProcedureTestCase.query(this);
		AutomatedProcedure.query(this);
		ManualProcedure.query(this);
		ManualProcedureStep.query(this);
		AuxiliaryRoutine.query(this);
		
		return root.getChildren();
	}
	
	/**
	 * @param name the H2 database name
	 * @param user the user name
	 * @param pswd the user password
	 * @param host the host name
	 * @param port the port number
	 * @throws ClassNotFoundException
	 */
	public H2S(String name, String user, String pswd, String host, int port) throws ClassNotFoundException {
		super(name, user, pswd);
		this.host = host;
		this.port = port;
		Class.forName("org.h2.Driver");
	}

	/**
	 * @param name the H2 database name
	 * @throws ClassNotFoundException
	 */
	public H2S(String name) throws ClassNotFoundException {
		this(name, "sa", "", "localhost", 9092);
	}
	
	/**
	 * ADDITIONAL_INFORMATION
	 */
	public static class AdditionalInformation
	extends DBS.AdditionalInformation 
	{

		// TABLE
		public static final String TABLE = "ADDITIONAL_INFORMATION";

		// FIELDS
		public static final String PK					= "PK";
		public static final String KEY					= "KEY";
		public static final String DESCRIPTION			= "DESCRIPTION";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK, 					PK				);
			KEYS.put(Field.KEY, 				KEY				);
			KEYS.put(Field.DESCRIPTION, 		DESCRIPTION		);
		}

		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public AdditionalInformation(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * AUTOMATED_PROCEDURE
	 */
	public static class AutomatedProcedure 
	extends DBS.AutomatedProcedure
	{
		// TABLE
		public static final String TABLE = "AUTOMATED_PROCEDURE";

		// FIELDS
		public static final String PK	= "PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK,	PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public AutomatedProcedure(Item parent) {
			super(parent);
		}

	}

	/**
	 * AUXILIARY_ROUTINE
	 */
	public static class AuxiliaryRoutine 
	extends DBS.AuxiliaryRoutine 
	{
		// TABLE
		public static final String TABLE = "AUXILIARYROUTINE";

		// FIELDS
		public static final String PK	= "PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK,	PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public AuxiliaryRoutine(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * BASELINE
	 */
	public static class Baseline 
	extends DBS.Baseline 
	{
		// TABLE
		public static final String TABLE = "BASELINE";

		// FIELDS
		public static final String PK					= "PK";
		public static final String NAME					= "NAME";
		public static final String DESCRIPTION			= "DESCRIPTION";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK,				PK			);
			KEYS.put(Field.NAME,			NAME		);
			KEYS.put(Field.DESCRIPTION,		DESCRIPTION	);
		}

		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public Baseline(Item parent) {
			super(parent);
		}
	
	}

	/**
	 * BASELINE_ITEM
	 */
	public static class BaselineItem 
	extends DBS.BaselineItem 
	{
		// TABLE
		public static final String TABLE = "BASELINE_ITEM";

		// FIELDS
		public static final String PK					= "PK";
		public static final String ID					= "ID";
		public static final String VERSION				= "VERSION";
		public static final String BASELINE_PK			= "BASELINE_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();

		static {
			KEYS.put(Field.PK, 				PK			);
			KEYS.put(Field.ID, 				ID			);
			KEYS.put(Field.VERSION, 		VERSION		);
			KEYS.put(Field.BASELINE_PK, 	BASELINE_PK	);
		}

		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public BaselineItem(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * DEPLOYMENT
	 */
	public static class Deployment 
	extends DBS.Deployment 
	{
		// TABLE
		public static final String TABLE = "DEPLOYMENT";

		// FIELDS
		public static final String PK					= "PK";
		public static final String NAME					= "NAME";
		public static final String DESCRIPTION			= "DESCRIPTION";
		public static final String MEASUREMENT_ONLY		= "PERFMEASUREMENTONLY";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK, 					PK					);
			KEYS.put(Field.NAME, 				NAME				);
			KEYS.put(Field.DESCRIPTION, 		DESCRIPTION			);
			KEYS.put(Field.MEASUREMENT_ONLY,	MEASUREMENT_ONLY	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public Deployment(Item parent) {
			super(parent);
		}

	}

	/**
	 * EDITING_LOCK
	 */
	public static class EditingLock 
	extends DBS.EditingLock 
	{
		// TABLE
		public static final String TABLE = "EDITING_LOCK";

		// FIELDS
		public static final String PK					= "PK";
		public static final String ID					= "ID";
		public static final String OWNER				= "OWNER";
		public static final String TYPE					= "TYPE";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK, 		PK		);
			KEYS.put(Field.ID, 		ID		);
			KEYS.put(Field.TYPE, 	TYPE	);
			KEYS.put(Field.OWNER,	OWNER	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public EditingLock(Item parent) {
			super(parent);
		}
	
	}

	/**
	 * FEATURE
	 */
	public static class Feature 
	extends DBS.Feature 
	{
		// TABLE
		public static final String TABLE = "FEATURE";

		// FIELDS
		public static final String PK					= "PK";
		public static final String ID					= "ID";
		public static final String TITLE				= "TITLE";
		public static final String DESCRIPTION			= "DESCRIPTION";
		public static final String TESTAREA_PK			= "TESTAREA_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK, 				PK				);
			KEYS.put(Field.ID, 				ID				);
			KEYS.put(Field.TITLE, 			TITLE			);
			KEYS.put(Field.DESCRIPTION, 	DESCRIPTION		);
			KEYS.put(Field.TESTAREA_PK, 	TESTAREA_PK		);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public Feature(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * MANUAL_PROCEDURE
	 */
	public static class ManualProcedure 
	extends DBS.ManualProcedure 
	{
		// TABLE
		public static final String TABLE = "MANUAL_PROCEDURE";

		// FIELDS
		public static final String PK	= "PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK,	PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public ManualProcedure(Item parent) {
			super(parent);
		}

	}

	/**
	 * MANUAL_PROCEDURE_STEP
	 */
	public static class ManualProcedureStep 
	extends DBS.ManualProcedureStep 
	{
		// TABLE
		public static final String TABLE = "MANUAL_PROCEDURE_STEP";

		// FIELDS
		public static final String PK					= "PK";
		public static final String STEP_NUMBER			= "STEPNUMBER";
		public static final String ACTION				= "ACTION";
		public static final String EXPECTED_RESULTS		= "EXPECTEDRESULTS";
		public static final String COMMENTS				= "COMMENTS";
		public static final String MANUAL_PROCEDURE_PK	= "MANUALPROCEDURE_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.PK, 					PK					);
			KEYS.put(Field.STEP_NUMBER, 		STEP_NUMBER			);
			KEYS.put(Field.ACTION, 				ACTION				);
			KEYS.put(Field.EXPECTED_RESULTS,	EXPECTED_RESULTS	);
			KEYS.put(Field.COMMENTS, 			COMMENTS			);
			KEYS.put(Field.MANUAL_PROCEDURE_PK, MANUAL_PROCEDURE_PK	);
		}
	
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public ManualProcedureStep(Item parent) {
			super(parent);
		}

	}

	/**
	 * PERFORMANCE_MEASUREMENT
	 */
	public static class PerformanceMeasurement 
	extends DBS.PerformanceMeasurement 
	{
		// TABLE
		public static final String TABLE = "PERFORMANCE_MEASUREMENT";

		// FIELDS
		public static final String PK					= "PK";
		public static final String KEY					= "KEY";
		public static final String DESCRIPTION			= "DESCRIPTION";
		public static final String BASEVALUE			= "BASEVALUE";
		public static final String TARGETVALUE			= "TARGETVALUE";
		public static final String PROJECT_PK			= "PROJECT_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();

		static {
			KEYS.put(Field.PK, 				PK				);
			KEYS.put(Field.KEY, 			KEY				);
			KEYS.put(Field.DESCRIPTION, 	DESCRIPTION		);
			KEYS.put(Field.BASEVALUE, 		BASEVALUE		);
			KEYS.put(Field.TARGETVALUE, 	TARGETVALUE		);
			KEYS.put(Field.PROJECT_PK, 		PROJECT_PK		);
		}

		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public PerformanceMeasurement(Item parent) {
			super(parent);
		}

	}

	/**
	 * PROCEDURE
	 */
	public static class Procedure 
	extends DBS.Procedure 
	{
		// TABLE
		public static final String TABLE = "PROCEDURE";

		// FIELDS
		public static final String PK					= "PK";
		public static final String TYPE					= "PROCEDURE_TYPE";
		public static final String ID					= "ID";
		public static final String TITLE				= "TITLE";
		public static final String DESCRIPTION			= "DESCRIPTION";
		public static final String SCENARIO_PK			= "SCENARIO_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK, 				PK				);
			KEYS.put(Field.TYPE, 			TYPE			);
			KEYS.put(Field.ID, 				ID				);
			KEYS.put(Field.TITLE, 			TITLE			);
			KEYS.put(Field.DESCRIPTION, 	DESCRIPTION		);
			KEYS.put(Field.SCENARIO_PK, 	SCENARIO_PK		);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public Procedure(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * PROCEDURE_TESTCASE
	 */
	public static class ProcedureTestCase 
	extends DBS.ProcedureTestCase 
	{
		// TABLE
		public static final String TABLE = "PROCEDURE_TEST_CASE";

		// FIELDS
		public static final String PROCEDURE_PK			= "PROCEDURE_PK";
		public static final String TESTCASES_PK			= "TESTCASES_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();

		static {
			KEYS.put(Field.PROCEDURE_PK, 	PROCEDURE_PK	);
			KEYS.put(Field.TESTCASES_PK, 	TESTCASES_PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public ProcedureTestCase(Item parent) {
			super(parent);
		}

	}

	/**
	 * PROJECT
	 */
	public static class Project 
	extends DBS.Project 
	{
		// TABLE
		public static final String TABLE = "PROJECT";

		// FIELDS
		public static final String PK 					= "PK";
		public static final String TYPE 				= "PROJECTTYPE";
		public static final String ID 					= "ID";
		public static final String VERSION 				= "VERSION";
		public static final String ARTIFACT 			= "ARTIFACTID";
		public static final String PACKAGE 				= "PACKAGENAME";
		public static final String BASEFOLDER 			= "BASEFOLDER";
		public static final String TARGETFOLDER 		= "CODEGENERATIONTARGETFOLDER";
		public static final String PARENT_PK 			= "PARENT_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();

		static {
			KEYS.put(Field.PK, 				PK				);
			KEYS.put(Field.TYPE, 			TYPE			);
			KEYS.put(Field.ID, 				ID				);
			KEYS.put(Field.VERSION, 		VERSION			);
			KEYS.put(Field.PACKAGE, 		PACKAGE			);
			KEYS.put(Field.ARTIFACT, 		ARTIFACT		);
			KEYS.put(Field.BASEFOLDER, 		BASEFOLDER		);
			KEYS.put(Field.TARGETFOLDER, 	TARGETFOLDER	);
		}

		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public Project(Item parent) {
			super(parent);
		}

	}

	/**
	 * PROJECT_REQUIREMENT
	 */
	public static class ProjectRequirement 
	extends DBS.ProjectRequirement 
	{
		// TABLE
		public static final String TABLE = "PROJECT_REQUIREMENT";

		// FIELDS
		public static final String PK 					= "PK";
		public static final String REQUIREMENT_ID 		= "REQUIREMENT_ID";
		public static final String STATUS				= "IMPLEMENTATIONSTATUS";
		public static final String RFW 					= "REQUESTFORWAIVER";
		public static final String VERIFICATIONSTAGE	= "VERIFICATIONSTAGE";
		public static final String COMMENT 				= "COMMENT";
		public static final String PROJECT_PK 			= "PROJECT_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK, 					PK					);
			KEYS.put(Field.REQUIREMENT_ID, 		REQUIREMENT_ID		);
			KEYS.put(Field.STATUS, 				STATUS				);
			KEYS.put(Field.RFW, 				RFW					);
			KEYS.put(Field.VERIFICATIONSTAGE,	VERIFICATIONSTAGE	);
			KEYS.put(Field.COMMENT, 			COMMENT 			);
			KEYS.put(Field.PROJECT_PK, 			PROJECT_PK			);
		}

		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public ProjectRequirement(Item parent) {
			super(parent);
		}

	}

	/**
	 * PROJECT_REQUIREMENT_DEPLOYMENT
	 */
	public static class ProjectRequirementDeployment 
	extends DBS.ProjectRequirementDeployment 
	{
		// TABLE
		public static final String TABLE = "PROJECT_REQUIREMENT_DEPLOYMENT";

		// FIELDS
		public static final String REQUIREMENT_PK		= "PROJECT_REQUIREMENT_PK";
		public static final String DEPLOYMENTS_PK		= "DEPLOYMENTS_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.REQUIREMENT_PK, 	REQUIREMENT_PK	);
			KEYS.put(Field.DEPLOYMENTS_PK, 	DEPLOYMENTS_PK	);
		}

		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public ProjectRequirementDeployment(Item parent) {
			super(parent);
		}

	}

	/**
	 * REQUIREMENT
	 */
	public static class Requirement 
	extends DBS.Requirement 
	{
		// TABLE
		public static final String TABLE = "REQUIREMENT";

		// FIELDS
		public static final String TYPE 				= "REQUIREMENT_TYPE";
		public static final String ID 					= "ID";
		public static final String NAME 				= "NAME";
		public static final String DESCRIPTION 			= "DESCRIPTION";
		public static final String IMPORT_DATE 			= "IMPORTDATE";
		public static final String IMPORT_FILE 			= "IMPORTFILE";
		public static final String PRIORITY				= "PRIORITY";
		public static final String VERIFICATION 		= "VERIFICATION";
		public static final String VERSION				= "VERSION";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.TYPE, 			TYPE			);
			KEYS.put(Field.ID, 				ID				);
			KEYS.put(Field.DESCRIPTION, 	DESCRIPTION		);
			KEYS.put(Field.IMPORT_DATE, 	IMPORT_DATE		);
			KEYS.put(Field.IMPORT_FILE, 	IMPORT_FILE		);
			KEYS.put(Field.NAME, 			NAME			);
			KEYS.put(Field.PRIORITY, 		PRIORITY		);
			KEYS.put(Field.VERIFICATION, 	VERIFICATION	);
			KEYS.put(Field.VERSION, 		VERSION			);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public Requirement(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * REQUIREMENT_DEPLOYMENT
	 */
	public static class RequirementDeployment 
	extends DBS.RequirementDeployment 
	{
		// TABLE
		public static final String TABLE = "REQUIREMENT_DEPLOYMENT";

		// FIELDS
		public static final String REQUIREMENT_ID		= "REQUIREMENT_ID";
		public static final String DEPLOYMENTS_PK		= "DEPLOYMENTS_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.REQUIREMENT_ID, 	REQUIREMENT_ID	);
			KEYS.put(Field.DEPLOYMENTS_PK, 	DEPLOYMENTS_PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public RequirementDeployment(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * SCENARIO
	 */
	public static class Scenario 
	extends DBS.Scenario 
	{
		// TABLE
		public static final String TABLE = "SCENARIO";

		// FIELDS
		public static final String PK					= "PK";
		public static final String TYPE					= "SCENARIOTYPE";
		public static final String ID					= "ID";
		public static final String TITLE				= "TITLE";
		public static final String DESCRIPTION			= "DESCRIPTION";
		public static final String RESOURCES			= "RESOURCES";
		public static final String TESTAREA_PK			= "TESTAREA_PK";
		public static final String PROJECT_PK			= "PROJECT_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK, 				PK				);
			KEYS.put(Field.TYPE,	 		TYPE			);
			KEYS.put(Field.ID, 				ID				);
			KEYS.put(Field.TITLE, 			TITLE			);
			KEYS.put(Field.DESCRIPTION, 	DESCRIPTION		);
			KEYS.put(Field.RESOURCES, 		RESOURCES		);
			KEYS.put(Field.TESTAREA_PK, 	TESTAREA_PK		);
			KEYS.put(Field.PROJECT_PK, 		PROJECT_PK		);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public Scenario(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * SCENARIO_ADDITIONAL_INFORMATION
	 */
	public static class ScenarioAdditionalInformation 
	extends DBS.ScenarioAdditionalInformation 
	{
		// TABLE
		public static final String TABLE = "SCENARIO_ADDITIONAL_INFORMATION";

		// FIELDS
		public static final String SCENARIO_PK			= "SCENARIO_PK";
		public static final String INFORMATIONS_PK		= "ADDITIONALINFORMATION_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();

		static {
			KEYS.put(Field.SCENARIO_PK, 	SCENARIO_PK		);
			KEYS.put(Field.INFORMATIONS_PK, INFORMATIONS_PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public ScenarioAdditionalInformation(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * SCENARIO_DEPLOYMENT
	 */
	public static class ScenarioDeployment 
	extends DBS.ScenarioDeployment 
	{
		// TABLE
		public static final String TABLE = "SCENARIO_DEPLOYMENT";

		// FIELDS
		public static final String SCENARIO_PK			= "SCENARIO_PK";
		public static final String DEPLOYMENTS_PK		= "DEPLOYMENTS_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();

		static {
			KEYS.put(Field.SCENARIO_PK, 	SCENARIO_PK	);
			KEYS.put(Field.DEPLOYMENTS_PK, 	DEPLOYMENTS_PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public ScenarioDeployment(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * SCENARIO_PERFORMANCE_MEASUREMENT
	 */
	public static class ScenarioPerformanceMeasurement 
	extends DBS.ScenarioPerformanceMeasurement 
	{
		// TABLE
		public static final String TABLE = "SCENARIO_PERFORMANCE_MEASUREMENT";

		// FIELDS
		public static final String SCENARIO_PK			= "SCENARIO_PK";
		public static final String MEASUREMENTS_PK		= "PERFORMANCEMEASUREMENTS_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
		
		static {
			KEYS.put(Field.SCENARIO_PK, 	SCENARIO_PK		);
			KEYS.put(Field.MEASUREMENTS_PK, MEASUREMENTS_PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public ScenarioPerformanceMeasurement(Item parent) {
			super(parent);
		}
	
	}

	/**
	 * SOFTWARE_REQUIREMENT
	 */
	public static class SoftwareRequirement 
	extends DBS.SoftwareRequirement 
	{
		// TABLE
		public static final String TABLE = "SOFTWARE_REQUIREMENT";

		// FIELDS
		public static final String ID 					= "ID";
		public static final String COMMENT				= "COMMENT";
		public static final String STABILITY			= "STABILITY";
		public static final String STRUCTURE	 		= "STRUCTURE";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.ID, 				ID			);
			KEYS.put(Field.STRUCTURE, 		STRUCTURE	);
			KEYS.put(Field.STABILITY, 		STABILITY	);
			KEYS.put(Field.COMMENT, 		COMMENT		);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public SoftwareRequirement(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * SOFTWARE_REQUIREMENT_USER_REQUIREMENT
	 */
	public static class SoftwareRequirementUserRequirement 
	extends DBS.SoftwareRequirementUserRequirement 
	{
		// TABLE
		public static final String TABLE = "SOFTWARE_REQUIREMENT_USER_REQUIREMENT";

		// FIELDS
		public static final String SOFT_REQ_ID	= "SOFTWARE_REQUIREMENT_ID";
		public static final String USER_REQ_ID	= "USERREQUIREMENTS_ID";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.SOFT_REQ_ID, SOFT_REQ_ID	);
			KEYS.put(Field.USER_REQ_ID, USER_REQ_ID	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public SoftwareRequirementUserRequirement(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * TESTAREA
	 */
	public static class TestArea 
	extends DBS.TestArea 
	{
		// TABLE
		public static final String TABLE = "TEST_AREA";

		// FIELDS
		public static final String PK					= "PK";
		public static final String ID					= "ID";
		public static final String TITLE				= "TITLE";
		public static final String DESCRIPTION			= "DESCRIPTION";
		public static final String APPROACH				= "APPROACH";
		public static final String PROJECT_PK			= "PROJECT_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK, 				PK				);
			KEYS.put(Field.ID, 				ID				);
			KEYS.put(Field.TITLE, 			TITLE			);
			KEYS.put(Field.DESCRIPTION, 	DESCRIPTION		);
			KEYS.put(Field.APPROACH, 		APPROACH		);
			KEYS.put(Field.PROJECT_PK, 		PROJECT_PK		);
		}
		
		/**
		 * @param h2s the H2S specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public TestArea(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * TESTCASE
	 */
	public static class TestCase 
	extends DBS.TestCase 
	{
		// TABLE
		public static final String TABLE = "TEST_CASE";

		// FIELDS
		public static final String PK					= "PK";
		public static final String ID					= "ID";
		public static final String TITLE				= "TITLE";
		public static final String SPECIFICATION		= "SPECIFICATION";
		public static final String SCOPE				= "SCOPE";
		public static final String CRITERIA				= "CRITERIA";
		public static final String COMMENT				= "COMMENT";
		public static final String FEATURE_PK			= "FEATURE_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.PK, 				PK				);
			KEYS.put(Field.ID, 				ID				);
			KEYS.put(Field.TITLE, 			TITLE			);
			KEYS.put(Field.SPECIFICATION, 	SPECIFICATION	);
			KEYS.put(Field.SCOPE, 			SCOPE			);
			KEYS.put(Field.CRITERIA, 		CRITERIA		);
			KEYS.put(Field.COMMENT, 		COMMENT			);
			KEYS.put(Field.FEATURE_PK, 		FEATURE_PK		);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public TestCase(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * TESTCASE_PROJECT_REQUIREMENT
	 */
	public static class TestCaseProjectRequirement 
	extends DBS.TestCaseProjectRequirement 
	{
		// TABLE
		public static final String TABLE = "TEST_CASE_PROJECT_REQUIREMENT";

		// FIELDS
		public static final String TESTCASE_PK			= "TEST_CASE_PK";
		public static final String REQUIREMENTS_PK		= "PROJECTREQUIREMENTS_PK";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();
	
		static {
			KEYS.put(Field.TESTCASE_PK, 	TESTCASE_PK		);
			KEYS.put(Field.REQUIREMENTS_PK, REQUIREMENTS_PK	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public TestCaseProjectRequirement(Item parent) {
			super(parent);
		}
		
	}

	/**
	 * USER_REQUIREMENT
	 */
	public static class UserRequirement 
	extends DBS.UserRequirement 
	{
		// TABLE
		public static final String TABLE = "USER_REQUIREMENT";

		// FIELDS
		public static final String ID 					= "ID";
		public static final String NOTE					= "ADDITIONALNOTE";
		public static final String JUSTIFICATION		= "JUSTIFICATION";
		public static final String LAST_CHANGED 		= "LASTCHANGEDIN";
		public static final String LEVEL 				= "REQUIREMENTLEVEL";
		public static final String TYPE					= "REQUIREMENTTYPE";

		// KEYS
		public static final Map<Object, String> KEYS = new HashMap<>();

		static {
			KEYS.put(Field.ID, 				ID				);
			KEYS.put(Field.TYPE, 			TYPE			);
			KEYS.put(Field.LEVEL, 			LEVEL			);
			KEYS.put(Field.NOTE, 			NOTE			);
			KEYS.put(Field.JUSTIFICATION, 	JUSTIFICATION	);
			KEYS.put(Field.LAST_CHANGED, 	LAST_CHANGED	);
		}
		
		/**
		 * @param h2s the H2 specification database
		 * @throws NotConnectedException
		 * @throws SQLException
		 */
		public static void query(H2S h2s) throws NotConnectedException, SQLException {
			query(h2s, TABLE, KEYS);
		}
		
		/** {@inheritDoc} */
		public UserRequirement(Item parent) {
			super(parent);
		}
		
	}
	
}
