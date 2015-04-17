/*   1:    */package appium.tutorial.android.util.reporter;

/*   2:    */
/*   3:    */import java.io.File;
/*   4:    */
import java.util.Collection;
/*   5:    */
import java.util.HashMap;
/*   6:    */
import java.util.LinkedList;
/*   7:    */
import java.util.List;
/*   8:    */
import java.util.Map;
/*   9:    */
import java.util.Set;
/*  10:    */
import org.apache.velocity.VelocityContext;
/*  11:    */
import org.testng.IClass;
/*  13:    */
import org.testng.ISuite;
/*  14:    */
import org.testng.ISuiteResult;
/*  16:    */
import org.testng.ITestResult;
/*  17:    */
import org.testng.xml.XmlSuite;

/*  18:    */
/*  19:    */public class JUnitXMLReporter
/* 20: */extends AbstractReporter
/* 21: */{
	/* 22: */private static final String RESULTS_KEY = "results";
	/* 23: */private static final String TEMPLATES_PATH = "appium/testout/reporter/reportng/templates/xml/";
	/* 24: */private static final String RESULTS_FILE = "results.xml";
	/* 25: */private static final String REPORT_DIRECTORY = "xml";

	/* 26: */
	/* 27: */public JUnitXMLReporter()
	/* 28: */{
		/* 29: 49 */super("appium/testout/reporter/templates/xml/");
		/* 30: */}

	/* 31: */
	/* 32: */public void generateReport(List<XmlSuite> xmlSuites,
			List<ISuite> suites, String outputDirectoryName)
	/* 33: */{
		/* 34: 63 */removeEmptyDirectories(new File(outputDirectoryName));
		/* 35: */
		/* 36: 65 */File outputDirectory = new File(outputDirectoryName, "xml");
		/* 37: 66 */outputDirectory.mkdirs();
		/* 38: */
		/* 39: 68 */Collection<TestClassResults> flattenedResults = flattenResults(suites);
		/* 40: 70 */for (TestClassResults results : flattenedResults)
		/* 41: */{
			/* 42: 72 */VelocityContext context = createContext();
			/* 43: 73 */context.put("results", results);
			/* 44: */try
			/* 45: */{
				/* 46: 77 */generateFile(new File(outputDirectory, results
						.getTestClass().getName() + '_' + "results.xml"),
						"results.xml.vm", context);
				/* 47: */}
			/* 48: */catch (Exception ex)
			/* 49: */{
				/* 50: 83 */throw new ReportNGException(
						"Failed generating JUnit XML report.", ex);
				/* 51: */}
			/* 52: */}
		/* 53: */}

	/* 54: */
	/* 55: */private Collection<TestClassResults> flattenResults(
			List<ISuite> suites)
	/* 56: */{
		/* 57: 96 */Map<IClass, TestClassResults> flattenedResults = new HashMap();
		/* 58: 97 */for (ISuite suite : suites) {
			/* 59: 99 */for (ISuiteResult suiteResult : suite.getResults()
					.values())
			/* 60: */{
				/* 61:102 */organiseByClass(suiteResult.getTestContext()
						.getFailedConfigurations().getAllResults(),
						flattenedResults);
				/* 62:103 */organiseByClass(suiteResult.getTestContext()
						.getSkippedConfigurations().getAllResults(),
						flattenedResults);
				/* 63: */
				/* 64: */
				/* 65:106 */organiseByClass(suiteResult.getTestContext()
						.getFailedTests().getAllResults(), flattenedResults);
				/* 66:107 */organiseByClass(suiteResult.getTestContext()
						.getSkippedTests().getAllResults(), flattenedResults);
				/* 67:108 */organiseByClass(suiteResult.getTestContext()
						.getPassedTests().getAllResults(), flattenedResults);
				/* 68: */}
			/* 69: */}
		/* 70:111 */return flattenedResults.values();
		/* 71: */}

	/* 72: */
	/* 73: */private void organiseByClass(Set<ITestResult> testResults,
			Map<IClass, TestClassResults> flattenedResults)
	/* 74: */{
		/* 75:118 */for (ITestResult testResult : testResults) {
			/* 76:120 */getResultsForClass(flattenedResults, testResult)
					.addResult(testResult);
			/* 77: */}
		/* 78: */}

	/* 79: */
	/* 80: */private TestClassResults getResultsForClass(
			Map<IClass, TestClassResults> flattenedResults,
			ITestResult testResult)
	/* 81: */{
		/* 82:131 */TestClassResults resultsForClass = flattenedResults
				.get(testResult.getTestClass());
		/* 83:132 */if (resultsForClass == null)
		/* 84: */{
			/* 85:134 */resultsForClass = new TestClassResults(
					testResult.getTestClass());
			/* 86:135 */flattenedResults.put(testResult.getTestClass(),
					resultsForClass);
			/* 87: */}
		/* 88:137 */return resultsForClass;
		/* 89: */}

	/* 90: */
	/* 91: */public static final class TestClassResults
	/* 92: */{
		/* 93: */private final IClass testClass;
		/* 94:148 */private final Collection<ITestResult> failedTests = new LinkedList();
		/* 95:149 */private final Collection<ITestResult> skippedTests = new LinkedList();
		/* 96:150 */private final Collection<ITestResult> passedTests = new LinkedList();
		/* 97:152 */private long duration = 0L;

		/* 98: */
		/* 99: */private TestClassResults(IClass testClass)
		/* 100: */{
			/* 101:157 */this.testClass = testClass;
			/* 102: */}

		/* 103: */
		/* 104: */public IClass getTestClass()
		/* 105: */{
			/* 106:163 */return this.testClass;
			/* 107: */}

		/* 108: */
		/* 109: */void addResult(ITestResult result)
		/* 110: */{
			/* 111:172 */switch (result.getStatus())
			/* 112: */{
			/* 113: */case 3:
				/* 114:176 */if (AbstractReporter.META.allowSkippedTestsInXML()) {
					/* 115:178 */this.skippedTests.add(result);
					/* 116: */}
				/* 117:179 */break;
			/* 118: */case 2:
				/* 119: */
			case 4:
				/* 120:186 */this.failedTests.add(result);
				/* 121:187 */break;
			/* 122: */case 1:
				/* 123:191 */this.passedTests.add(result);
				/* 124: */}
			/* 125:195 */this.duration += result.getEndMillis()
					- result.getStartMillis();
			/* 126: */}

		/* 127: */
		/* 128: */public Collection<ITestResult> getFailedTests()
		/* 129: */{
			/* 130:201 */return this.failedTests;
			/* 131: */}

		/* 132: */
		/* 133: */public Collection<ITestResult> getSkippedTests()
		/* 134: */{
			/* 135:207 */return this.skippedTests;
			/* 136: */}

		/* 137: */
		/* 138: */public Collection<ITestResult> getPassedTests()
		/* 139: */{
			/* 140:213 */return this.passedTests;
			/* 141: */}

		/* 142: */
		/* 143: */public long getDuration()
		/* 144: */{
			/* 145:219 */return this.duration;
			/* 146: */}
		/* 147: */
	}
	/* 148: */
}

/*
 * Location: C:\Users\dell\Desktop\reportng-1.1.4.jar
 * 
 * Qualified Name: org.uncommons.reportng.JUnitXMLReporter
 * 
 * JD-Core Version: 0.7.0.1
 */