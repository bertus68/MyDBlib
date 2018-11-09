package a.polverini.my;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import org.h2.jdbc.JdbcClob;

import a.polverini.my.DBS.AdditionalInformation;
import a.polverini.my.DBS.Baseline;
import a.polverini.my.DBS.BaselineItem;
import a.polverini.my.DBS.Deployment;
import a.polverini.my.DBS.Feature;
import a.polverini.my.DBS.PerformanceMeasurement;
import a.polverini.my.DBS.Procedure;
import a.polverini.my.DBS.ProcedureTestCase;
import a.polverini.my.DBS.Project;
import a.polverini.my.DBS.ProjectRequirement;
import a.polverini.my.DBS.Requirement;
import a.polverini.my.DBS.Scenario;
import a.polverini.my.DBS.TestArea;
import a.polverini.my.DBS.TestCase;
import a.polverini.my.DBS.TestCaseProjectRequirement;

public class XML {
	
	public static class Writer {
		
		private final PrintStream out;
		private String[] INDENT = new String[16];
		
		public Writer(String path) throws FileNotFoundException {
			out = new PrintStream(new FileOutputStream(new File(path)));
			String s = "";
			for(int i=0;i<INDENT.length;i++) {
				INDENT[i] = s;
				s+="    ";
			}
		}
		
		public void close() {
			out.close();
		}

		private void writeDescription(String indent, String text) {
			String tag  = "description";
			out.printf("%s  <%s>\n",indent,tag);
			for(String s : text.split("\n")) {
				out.printf("%s  %s\n",indent,s);
			}
			out.printf("%s  </%s>\n",indent,tag);
		}
		
		public void writeSpecification(Map<String, Item> egsccSpecification) {
			String indent = INDENT[0];
			String tag = "specification";
			out.printf("%s<%s>\n",indent,tag);
			writeBaselines(		egsccSpecification.get("baselines"		));
			writeDeployment(	egsccSpecification.get("deployments"	));
			writeInformation(	egsccSpecification.get("informations"	));
			writeMeasurement(	egsccSpecification.get("measurements"	));
			writeRequirement(	egsccSpecification.get("requirements"	));
			writeProject(		egsccSpecification.get("projects"		));
			out.printf("%s</%s>\n",INDENT[0],tag);
		}
		
		private void writeBaselines(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tag  = "baseline";
			String tags = tag + "s";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof Baseline) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeBaselineItem(entry.getValue());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
			
		}
		
		private void writeBaselineItem(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[2];
			String tags = "baselineItems";
			String tag  = "baselineItem";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof BaselineItem) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeBaselineItem(entry.getValue());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writeDeployment(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tags = "deployments";
			String tag  = "deployment";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof Deployment) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeInformation(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tag  = "information";
			String tags = tag+"s";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof  AdditionalInformation) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeDescription(indent+"  ", (String)entry.getValue().get(AdditionalInformation.Field.DESCRIPTION));
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writeMeasurement(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tags = "measurements";
			String tag  = "measurement";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof PerformanceMeasurement) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writeRequirement(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tags = "requirements";
			String tag  = "requirement";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof Requirement) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				Requirement requirement = (Requirement) entry.getValue();

				StringBuilder			sb = new StringBuilder();
				
				String type	 		= (String) requirement.get(Requirement.Field.TYPE);
				if(type!=null)			sb.append(String.format(" %s='%s'","type", type));
				
				String id	 		= (String) requirement.get(Requirement.Field.ID);
				if(id!=null)			sb.append(String.format(" %s='%s'","id", id));
				
				String name	 		= (String) requirement.get(Requirement.Field.NAME);
				if(name!=null)			sb.append(String.format(" %s='%s'","name", name));
				
				String verification = (String) requirement.get(Requirement.Field.VERIFICATION);
				if(verification!=null)	sb.append(String.format(" %s='%s'","verification", verification));
				
				String priority		= (String) requirement.get(Requirement.Field.PRIORITY);
				if(priority!=null)		sb.append(String.format(" %s='%s'","priority", priority));
				
				String version		= (String) requirement.get(Requirement.Field.VERSION);
				if(version!=null)		sb.append(String.format(" %s='%s'","version", version));
				
				Timestamp timestamp = (Timestamp) requirement.get(Requirement.Field.IMPORT_DATE);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0Z");
				sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
				String importdate = sdf.format(timestamp);
				if(importdate!=null)	sb.append(String.format(" %s='%s'","importdate", name));
				
				String importfile = (String) requirement.get(Requirement.Field.IMPORT_FILE);
				if(importfile!=null)	sb.append(String.format(" %s='%s'","importfile", importfile));
				
				out.printf("%s  <%s pk='%s'%s>\n",indent,tag,entry.getKey(), sb.toString());

				String description	= (String) requirement.get(Requirement.Field.DESCRIPTION);
				writeDescription(indent+"  ", description);
				
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writeProject(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tags = "projects";
			String tag  = "project";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof Project) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeProjectRequirement(entry.getValue());
				writeTestArea(entry.getValue());
				writeScenario(entry.getValue());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeProjectRequirement(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[2];
			String tags = "projectRequirements";
			String tag  = "projectRequirement";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof ProjectRequirement) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeTestArea(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[2];
			String tags = "testareas";
			String tag  = "testarea";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof TestArea) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeFeature(entry.getValue());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeFeature(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[3];
			String tags = "features";
			String tag  = "feature";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof Feature) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeTestCase(entry.getValue());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeTestCase(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[4];
			String tags = "testcases";
			String tag  = "testcase";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof TestCase) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeTestCaseProjectRequirement(entry.getValue());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeTestCaseProjectRequirement(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[5];
			String tag  = "testCaseProjectRequirement";
			String tags = tag + "s";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof TestCaseProjectRequirement) {
					ProjectRequirement projectRequirement = (ProjectRequirement)child.get(ProjectRequirement.TAG);
					if(projectRequirement!=null) {
						unsorted.put(projectRequirement.toString(), projectRequirement);
					}
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'/>\n",indent,tag,entry.getKey());
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeScenario(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[2];
			String tags = "scenarios";
			String tag  = "scenario";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof Scenario) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeScenarioTestArea(entry.getValue());
				writeProcedure(entry.getValue());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeScenarioTestArea(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[3];
			String tag  = "scenarioTestArea";
			
			TestArea testarea = (TestArea)parent.get(TestArea.TAG);
			if(testarea==null) return;
			
			out.printf("%s  <%s pk='%s'/>\n",indent,tag,testarea.toString());
		}

		private void writeProcedure(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[3];
			String tags = "procedures";
			String tag  = "procedure";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof Procedure) {
					unsorted.put(child.toString(), child);
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());
				writeProcedureTestCase(entry.getValue());
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writeProcedureTestCase(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[4];
			String tags = "procedureTestCases";
			String tag  = "procedureTestCase";
			
			// filter
			Map<String, Item> unsorted = new HashMap<String, Item>();
			for(Item child : parent.getChildren()) {
				if(child instanceof ProcedureTestCase) {
					TestCase testcase = (TestCase)child.get(TestCase.TAG);
					if(testcase!=null) {
						unsorted.put(testcase.toString(), testcase);
					}
				}
			}
			
			// sort
			Map<String, Item> sorted = new TreeMap<String, Item>(unsorted);
			
			// print
			out.printf("%s<%s>\n",indent,tags);
			for(Entry<String, Item> entry : sorted.entrySet()) {
				out.printf("%s  <%s pk='%s'/>\n",indent,tag,entry.getKey());
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
	}

}
