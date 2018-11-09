package a.polverini.my;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

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
			this(new FileOutputStream(new File(path)));
		}
		
		public Writer(OutputStream stream) throws FileNotFoundException {
			out = new PrintStream(stream);
			String s = "";
			for(int i=0;i<INDENT.length;i++) {
				INDENT[i] = s;
				s+="    ";
			}
		}
		
		public void close() {
			out.close();
		}

		public void writeTag(String indent, String tag, String text) {
			out.printf("%s  <%s>\n",indent,tag);
			for(String s : text.split("\n")) {
				out.printf("%s  %s\n",indent,s);
			}
			out.printf("%s  </%s>\n",indent,tag);
		}
		
		public void writeTestSpecification(Item specification) {
			
			String indent = INDENT[0];
			String tag = "TestSpecification";
			
			out.printf("%s<%s>\n",indent,tag);
			writeBaselines(		(Item)specification.get("baselines"		));
			writeDeployment(	(Item)specification.get("deployments"	));
			writeAdditionalInformations(	(Item)specification.get("informations"	));
			writePerformanceMeasurements(	(Item)specification.get("measurements"	));
			writeRequirement(	(Item)specification.get("requirements"	));
			writeProject(		(Item)specification.get("projects"		));
			out.printf("%s</%s>\n",INDENT[0],tag);
		}
		
		private void writeBaselines(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tag  = "Baseline";
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
				
				Baseline baseline = (Baseline) entry.getValue();
				
				String description = (String)baseline.get(AdditionalInformation.Field.DESCRIPTION);
				if(description!=null) writeTag(indent+"  ", "description", description);
				
				writeBaselineItems(baseline);
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
			
		}
		
		private void writeBaselineItems(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[2];
			String tag  = "BaselineItem";
			String tags = tag + "s";
			
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

				BaselineItem baselineItem = (BaselineItem)entry.getValue();
				
				StringBuilder sb = new StringBuilder();
				
				String id = (String) baselineItem.get(BaselineItem.Field.ID);
				if(id!=null) sb.append(String.format(" %s='%s'","id", id));

				String version = (String) baselineItem.get(BaselineItem.Field.VERSION);
				if(version!=null) sb.append(String.format(" %s='%s'","version", version));
				
				out.printf("%s  <%s pk='%s'%s/>\n",indent,tag,entry.getKey(),sb.toString());
				
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writeDeployment(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tag  = "Deployment";
			String tags = tag + "s";
			
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

				Deployment deployment = (Deployment)entry.getValue();
				
				StringBuilder sb = new StringBuilder();
				
				String name = (String) deployment.get(Deployment.Field.NAME);
				if(name!=null) sb.append(String.format(" %s='%s'","name", name));

				String measurementonly = (String) deployment.get(Deployment.Field.MEASUREMENT_ONLY);
				if(measurementonly!=null) sb.append(String.format(" %s='%s'","measurementonly", measurementonly));
				
				out.printf("%s  <%s pk='%s'%s>\n",indent,tag,entry.getKey(),sb.toString());
				
				String description = (String)deployment.get(Deployment.Field.DESCRIPTION);
				if(description!=null) writeTag(indent+"  ", "description", description);
				
				out.printf("%s  </%s>\n",indent,tag);
				
	        }
			out.printf("%s</%s>\n",indent,tags);
		}

		private void writeAdditionalInformations(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tag  = "AdditionalInformation";
			String tags = tag + "s";
			
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
				
				AdditionalInformation additionalInformation = (AdditionalInformation)entry.getValue();

				out.printf("%s  <%s pk='%s'>\n",indent,tag,entry.getKey());

				String description = (String)additionalInformation.get(AdditionalInformation.Field.DESCRIPTION);
				if(description!=null) writeTag(indent+"  ", "description", description);
				
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writePerformanceMeasurements(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tag  = "PerformanceMeasurement";
			String tags = tag + "s";
			
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
				
				PerformanceMeasurement performanceMeasurement = (PerformanceMeasurement)entry.getValue();
				
				StringBuilder sb = new StringBuilder();
				
				String key = (String) performanceMeasurement.get(PerformanceMeasurement.Field.KEY);
				if(key!=null) sb.append(String.format(" %s='%s'","key", key));

				String basevalue = (String) performanceMeasurement.get(PerformanceMeasurement.Field.BASEVALUE);
				if(basevalue!=null) sb.append(String.format(" %s='%s'","basevalue", basevalue));

				String targetvalue = (String) performanceMeasurement.get(PerformanceMeasurement.Field.TARGETVALUE);
				if(targetvalue!=null) sb.append(String.format(" %s='%s'","targetvalue", targetvalue));

				out.printf("%s  <%s pk='%s'%s>\n",indent,tag,entry.getKey(),sb.toString());
				
				String description = (String)performanceMeasurement.get(PerformanceMeasurement.Field.DESCRIPTION);
				if(description!=null) writeTag(indent+"  ", "description", description);

				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writeRequirement(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tag  = "Requirement";
			String tags = tag + "s";
			
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

				StringBuilder sb = new StringBuilder();
				
				String id = (String) requirement.get(Requirement.Field.ID);
				if(id!=null) sb.append(String.format(" %s='%s'","id", id));
				
				String type = (String) requirement.get(Requirement.Field.TYPE);
				if(type!=null) sb.append(String.format(" %s='%s'","type", type));
				
				String name = (String) requirement.get(Requirement.Field.NAME);
				if(name!=null) sb.append(String.format(" %s='%s'","name", name));
				
				String verification = (String) requirement.get(Requirement.Field.VERIFICATION);
				if(verification!=null)	sb.append(String.format(" %s='%s'","verification", verification));
				
				String priority = (String) requirement.get(Requirement.Field.PRIORITY);
				if(priority!=null) sb.append(String.format(" %s='%s'","priority", priority));
				
				String version = (String) requirement.get(Requirement.Field.VERSION);
				if(version!=null) sb.append(String.format(" %s='%s'","version", version));
				
				Timestamp timestamp = (Timestamp) requirement.get(Requirement.Field.IMPORT_DATE);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0Z");
				sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
				String importdate = sdf.format(timestamp);
				if(importdate!=null)	sb.append(String.format(" %s='%s'","importdate", name));
				
				String importfile = (String) requirement.get(Requirement.Field.IMPORT_FILE);
				if(importfile!=null) sb.append(String.format(" %s='%s'","importfile", importfile));
				
				out.printf("%s  <%s pk='%s'%s>\n",indent,tag,entry.getKey(), sb.toString());

				String description = (String) requirement.get(Requirement.Field.DESCRIPTION);
				if(description!=null) writeTag(indent+"  ", "description", description);
				
				out.printf("%s  </%s>\n",indent,tag);
	        }
			out.printf("%s</%s>\n",indent,tags);
		}
		
		private void writeProject(Item parent) {
			if(parent==null) return;
			
			String indent = INDENT[1];
			String tag  = "Project";
			String tags = tag + "s";
			
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
				
				Item item = entry.getValue();

				StringBuilder sb = new StringBuilder();
				
				String id = (String) item.get(Project.Field.ID);
				if(id!=null) sb.append(String.format(" %s='%s'","id", id));
				
				String type = (String) item.get(Project.Field.TYPE);
				if(type!=null) sb.append(String.format(" %s='%s'","type", type));
				
				String version = (String) item.get(Project.Field.VERSION);
				if(version!=null) sb.append(String.format(" %s='%s'","version", version));
				
				String artifact = (String) item.get(Project.Field.ARTIFACT);
				if(artifact!=null) sb.append(String.format(" %s='%s'","artifact", artifact));

				String pkg = (String) item.get(Project.Field.PACKAGE);
				if(pkg!=null) sb.append(String.format(" %s='%s'","package", pkg));
				
				String basefolder = (String) item.get(Project.Field.BASEFOLDER);
				if(basefolder!=null) sb.append(String.format(" %s='%s'","basefolder", basefolder));
				
				String targetfolder = (String) item.get(Project.Field.TARGETFOLDER);
				if(targetfolder!=null) sb.append(String.format(" %s='%s'","targetfolder", targetfolder));

				
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
