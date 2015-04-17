/*   1:    */package appium.tutorial.android.util.reporter;

/*   2:    */
/*   3:    */import java.io.File;
/*   4:    */
import java.io.IOException;
/*   5:    */
import java.io.InputStream;
/*   6:    */
import java.util.ArrayList;
/*   7:    */
import java.util.Collection;
/*   8:    */
import java.util.Collections;
/*   9:    */
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
/*  10:    */
import java.util.List;
/*  11:    */
import java.util.Map;
/*  13:    */
import java.util.SortedMap;
/*  14:    */
import java.util.SortedSet;
/*  15:    */
import java.util.TreeMap;
/*  16:    */
import java.util.TreeSet;
/*  17:    */
import org.apache.velocity.VelocityContext;
/*  18:    */
import org.testng.IClass;
/*  19:    */
import org.testng.IInvokedMethod;
/*  20:    */
import org.testng.IResultMap;
/*  21:    */
import org.testng.ISuite;
/*  22:    */
import org.testng.ISuiteResult;
/*  24:    */
import org.testng.ITestNGMethod;
/*  25:    */
import org.testng.ITestResult;
/*  26:    */
import org.testng.Reporter;
/*  27:    */
import org.testng.xml.XmlSuite;

import appium.tutorial.android.util.ftp.FtpUtil;

/*  28:    */
/*  29:    */public class HTMLReporter
/* 30: */extends AbstractReporter
/* 31: */{
	/* 55: 77 */private static final Comparator<ITestNGMethod> METHOD_COMPARATOR = new TestMethodComparator();
	/* 56: 78 */private static final Comparator<ITestResult> RESULT_COMPARATOR = new TestResultComparator();
	/* 57: 79 */private static final Comparator<IClass> CLASS_COMPARATOR = new TestClassComparator();

	/* 58: */
	/* 59: */public HTMLReporter()
	/* 60: */{
		/* 61: 83 */super("appium/testout/reporter/templates/html/");
		/* 62: */}

	/* 63: */
	/* 64: */public void generateReport(List<XmlSuite> xmlSuites,
			List<ISuite> suites, String outputDirectoryName)
	/* 65: */{
		/* 66: 97 */removeEmptyDirectories(new File(outputDirectoryName));
		/* 67: */
		/* 68: 99 */boolean useFrames = System.getProperty(
				"appium.testout.reporter.frames", "true").equals("true");
		/* 69:100 */boolean onlyFailures = System.getProperty(
				"appium.testout.reporter.failures-only", "false")
				.equals("true");
		/* 70: */
		/* 71:102 */File outputDirectory = new File(outputDirectoryName, "html");
		/* 72:103 */outputDirectory.mkdirs();
		/* 73: */try
		/* 74: */{
			/* 75:107 */if (useFrames) {
				/* 76:109 */createFrameset(outputDirectory);
				/* 77: */}
			/* 78:111 */createOverview(suites, outputDirectory, !useFrames,
					onlyFailures);
			/* 79:112 */createSuiteList(suites, outputDirectory, onlyFailures);
			/* 80:113 */createGroups(suites, outputDirectory);
			/* 81:114 */createResults(suites, outputDirectory, onlyFailures);
			/* 82: */
			/* 83: */
			/* 84:117 */createLog(outputDirectory, onlyFailures);
			/* 85:118 */copyResources(outputDirectory);
			if(ResourceBundle.getBundle("ftp").getString("send?").equals("true")){
				FtpUtil.upload(new Date());	
			}
						
			/* 86: */}
		/* 87: */catch (Exception ex)
		/* 88: */{
			/* 89:122 */throw new ReportNGException(
					"Failed generating HTML report.", ex);
			/* 90: */}
		/* 91: */}

	/* 92: */
	/* 93: */private void createFrameset(File outputDirectory)
	/* 94: */throws Exception
	/* 95: */{
		/* 96:133 */VelocityContext context = createContext();
		/* 97:134 */generateFile(new File(outputDirectory, "index.html"),
				"index.html.vm", context);
		/* 98: */}

	/* 99: */
	/* 100: */private void createOverview(List<ISuite> suites,
			File outputDirectory, boolean isIndex, boolean onlyFailures)
	/* 101: */throws Exception
	/* 102: */{
		/* 103:145 */VelocityContext context = createContext();
		/* 104:146 */context.put("suites", suites);
		/* 105:147 */context.put("onlyReportFailures",
				Boolean.valueOf(onlyFailures));
		/* 106:148 */generateFile(new File(outputDirectory,
				isIndex ? "index.html" : "overview.html"), "overview.html.vm",
				context);
		/* 107: */}

	/* 108: */
	/* 109: */private void createSuiteList(List<ISuite> suites,
			File outputDirectory, boolean onlyFailures)
	/* 110: */throws Exception
	/* 111: */{
		/* 112:162 */VelocityContext context = createContext();
		/* 113:163 */context.put("suites", suites);
		/* 114:164 */context.put("onlyReportFailures",
				Boolean.valueOf(onlyFailures));
		/* 115:165 */generateFile(new File(outputDirectory, "suites.html"),
				"suites.html.vm", context);
		/* 116: */}

	/* 117: */
	/* 118: */private void createResults(List<ISuite> suites,
			File outputDirectory, boolean onlyShowFailures)
	/* 119: */throws Exception
	/* 120: */{
		/* 121:179 */int index = 1;
		/* 122:180 */for (ISuite suite : suites)
		/* 123: */{
			/* 124:182 */int index2 = 1;
			/* 125:183 */for (ISuiteResult result : suite.getResults().values())
			/* 126: */{
				/* 127:185 */boolean failuresExist = (result.getTestContext()
						.getFailedTests().size() > 0)
						|| (result.getTestContext().getFailedConfigurations()
								.size() > 0);
				/* 128:187 */if ((!onlyShowFailures) || (failuresExist))
				/* 129: */{
					/* 130:189 */VelocityContext context = createContext();
					/* 131:190 */context.put("result", result);
					/* 132:191 */context.put("failedConfigurations",
							sortByTestClass(result.getTestContext()
									.getFailedConfigurations()));
					/* 133:192 */context.put("skippedConfigurations",
							sortByTestClass(result.getTestContext()
									.getSkippedConfigurations()));
					/* 134:193 */context.put("failedTests",
							sortByTestClass(result.getTestContext()
									.getFailedTests()));
					/* 135:194 */context.put("skippedTests",
							sortByTestClass(result.getTestContext()
									.getSkippedTests()));
					/* 136:195 */context.put("passedTests",
							sortByTestClass(result.getTestContext()
									.getPassedTests()));
					/* 137:196 */String fileName = String.format(
							"suite%d_test%d_%s",
							new Object[] { Integer.valueOf(index),
									Integer.valueOf(index2), "results.html" });
					/* 138:197 */generateFile(new File(outputDirectory,
							fileName), "results.html.vm", context);
					/* 139: */}
				/* 140:201 */index2++;
				/* 141: */}
			/* 142:203 */index++;
			/* 143: */}

		/* 144: */}

	/* 145: */
	/* 146: */private void createChronology(List<ISuite> suites,
			File outputDirectory)
	/* 147: */throws Exception
	/* 148: */{
		/* 149:210 */int index = 1;
		/* 150:211 */for (ISuite suite : suites)
		/* 151: */{
			/* 152:213 */List<IInvokedMethod> methods = suite
					.getAllInvokedMethods();
			/* 153:214 */if (!methods.isEmpty())
			/* 154: */{
				/* 155:216 */VelocityContext context = createContext();
				/* 156:217 */context.put("suite", suite);
				/* 157:218 */context.put("methods", methods);
				/* 158:219 */String fileName = String.format("suite%d_%s",
						new Object[] { Integer.valueOf(index),
								"chronology.html" });
				/* 159:220 */generateFile(new File(outputDirectory, fileName),
						"chronology.html.vm", context);
				/* 160: */}
			/* 161:224 */index++;
			/* 162: */}
		/* 163: */}

	/* 164: */
	/* 165: */private SortedMap<IClass, List<ITestResult>> sortByTestClass(
			IResultMap results)
	/* 166: */{
		/* 167:234 */SortedMap<IClass, List<ITestResult>> sortedResults = new TreeMap(
				CLASS_COMPARATOR);
		/* 168:235 */for (ITestResult result : results.getAllResults())
		/* 169: */{
			/* 170:237 */List<ITestResult> resultsForClass = sortedResults
					.get(result.getTestClass());
			/* 171:238 */if (resultsForClass == null)
			/* 172: */{
				/* 173:240 */resultsForClass = new ArrayList();
				/* 174:241 */sortedResults.put(result.getTestClass(),
						resultsForClass);
				/* 175: */}
			/* 176:243 */int index = Collections.binarySearch(resultsForClass,
					result, RESULT_COMPARATOR);
			/* 177:244 */if (index < 0) {
				/* 178:246 */index = Math.abs(index + 1);
				/* 179: */}
			/* 180:248 */resultsForClass.add(index, result);
			/* 181: */}
		/* 182:250 */return sortedResults;
		/* 183: */}

	/* 184: */
	/* 185: */private void createGroups(List<ISuite> suites,
			File outputDirectory)
	/* 186: */throws Exception
	/* 187: */{
		/* 188:262 */int index = 1;
		/* 189:263 */for (ISuite suite : suites)
		/* 190: */{
			/* 191:265 */SortedMap<String, SortedSet<ITestNGMethod>> groups = sortGroups(suite
					.getMethodsByGroups());
			/* 192:266 */if (!groups.isEmpty())
			/* 193: */{
				/* 194:268 */VelocityContext context = createContext();
				/* 195:269 */context.put("suite", suite);
				/* 196:270 */context.put("groups", groups);
				/* 197:271 */String fileName = String.format("suite%d_%s",
						new Object[] { Integer.valueOf(index), "groups.html" });
				/* 198:272 */generateFile(new File(outputDirectory, fileName),
						"groups.html.vm", context);
				/* 199: */}
			/* 200:276 */index++;
			/* 201: */}
		/* 202: */}

	/* 203: */
	/* 204: */private void createLog(File outputDirectory, boolean onlyFailures)
	/* 205: */throws Exception
	/* 206: */{
		/* 207:287 */if (!Reporter.getOutput().isEmpty())
		/* 208: */{
			/* 209:289 */VelocityContext context = createContext();
			/* 210:290 */context.put("onlyReportFailures",
					Boolean.valueOf(onlyFailures));
			/* 211:291 */generateFile(new File(outputDirectory, "output.html"),
					"output.html.vm", context);
			/* 212: */}
		/* 213: */}

	/* 214: */
	/* 215: */private SortedMap<String, SortedSet<ITestNGMethod>> sortGroups(
			Map<String, Collection<ITestNGMethod>> groups)
	/* 216: */{
		/* 217:304 */SortedMap<String, SortedSet<ITestNGMethod>> sortedGroups = new TreeMap();
		/* 218:305 */for (Map.Entry<String, Collection<ITestNGMethod>> entry : groups
				.entrySet())
		/* 219: */{
			/* 220:307 */SortedSet<ITestNGMethod> methods = new TreeSet(
					METHOD_COMPARATOR);
			/* 221:308 */methods.addAll(entry.getValue());
			/* 222:309 */sortedGroups.put(entry.getKey(), methods);
			/* 223: */}
		/* 224:311 */return sortedGroups;
		/* 225: */}

	/* 226: */
	/* 227: */private void copyResources(File outputDirectory)
	/* 228: */throws IOException
	/* 229: */{
		/* 230:323 */copyClasspathResource(outputDirectory, "reportng.css",
				"reportng.css");
		/* 231:324 */copyClasspathResource(outputDirectory, "reportng.js",
				"reportng.js");
		/* 232:325 */copyClasspathResource(outputDirectory, "sorttable.js",
				"sorttable.js");
		/* 233: */
		/* 234:327 */File customStylesheet = META.getStylesheetPath();
		/* 235:329 */if (customStylesheet != null) {
			/* 236:331 */if (customStylesheet.exists())
			/* 237: */{
				/* 238:333 */copyFile(outputDirectory, customStylesheet,
						"custom.css");
				/* 239: */}
			/* 240: */else
			/* 241: */{
				/* 242:339 */InputStream stream = ClassLoader
						.getSystemClassLoader().getResourceAsStream(
								customStylesheet.getPath());
				/* 243:340 */if (stream != null) {
					/* 244:342 */copyStream(outputDirectory, stream,
							"custom.css");
					/* 245: */}
				/* 246: */}
			/* 247: */}
		/* 248: */}
	/* 249: */
}

/*
 * Location: C:\Users\dell\Desktop\reportng-1.1.4.jar
 * 
 * Qualified Name: appium.testout.reporter.HTMLReporter
 * 
 * JD-Core Version: 0.7.0.1
 */