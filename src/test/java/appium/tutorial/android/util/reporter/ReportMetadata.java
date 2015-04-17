/*   1:    */package appium.tutorial.android.util.reporter;

/*   2:    */
/*   3:    */import java.io.File;
/*   5:    */
import java.net.InetAddress;
/*   6:    */
import java.net.UnknownHostException;
/*   7:    */
import java.text.DateFormat;
/*   8:    */
import java.text.SimpleDateFormat;
/*   9:    */
import java.util.Date;
/*  10:    */
import java.util.Locale;

/*  12:    */
/*  13:    */public final class ReportMetadata
/* 14: */{
	/* 15: */static final String PROPERTY_KEY_PREFIX = "appium.testout.reporter.";
	/* 16: */static final String TITLE_KEY = "appium.testout.reporter.title";
	/* 17: */static final String DEFAULT_TITLE = "Test Results Report";
	/* 18: */static final String COVERAGE_KEY = "appium.testout.reporter.coverage-report";
	/* 19: */static final String EXCEPTIONS_KEY = "appium.testout.reporter.show-expected-exceptions";
	/* 20: */static final String OUTPUT_KEY = "appium.testout.reporter.escape-output";
	/* 21: */static final String XML_DIALECT_KEY = "appium.testout.reporter.xml-dialect";
	/* 22: */static final String STYLESHEET_KEY = "appium.testout.reporter.stylesheet";
	/* 23: */static final String LOCALE_KEY = "appium.testout.reporter.locale";
	/* 24: */static final String VELOCITY_LOG_KEY = "appium.testout.reporter.velocity-log";
	/* 25: 43 */private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
			"EEEE dd MMMM yyyy");
	/* 26: 44 */private static final DateFormat TIME_FORMAT = new SimpleDateFormat(
			"HH:mm z");
	/* 27: 50 */private final Date reportTime = new Date();

	/* 28: */
	/* 29: */public String getReportDate()
	/* 30: */{
		/* 31: 59 */return DATE_FORMAT.format(this.reportTime);
		/* 32: */}

	/* 33: */
	/* 34: */public String getReportTime()
	/* 35: */{
		/* 36: 69 */return TIME_FORMAT.format(this.reportTime);
		/* 37: */}

	/* 38: */
	/* 39: */public String getReportTitle()
	/* 40: */{
		/* 41: 75 */return System.getProperty("appium.testout.reporter.title",
				"Test Results Report");
		/* 42: */}

	/* 43: */
	/* 44: */public String getCoverageLink()
	/* 45: */{
		/* 46: 85 */return System
				.getProperty("appium.testout.reporter.coverage-report");
		/* 47: */}

	/* 48: */
	/* 49: */public File getStylesheetPath()
	/* 50: */{
		/* 51: 97 */String path = System
				.getProperty("appium.testout.reporter.stylesheet");
		/* 52: 98 */return path == null ? null : new File(path);
		/* 53: */}

	/* 54: */
	/* 55: */public boolean shouldShowExpectedExceptions()
	/* 56: */{
		/* 57:110 */return System.getProperty(
				"appium.testout.reporter.show-expected-exceptions", "false")
				.equalsIgnoreCase("true");
		/* 58: */}

	/* 59: */
	/* 60: */public boolean shouldEscapeOutput()
	/* 61: */{
		/* 62:124 */return System.getProperty(
				"appium.testout.reporter.escape-output", "true")
				.equalsIgnoreCase("true");
		/* 63: */}

	/* 64: */
	/* 65: */public boolean allowSkippedTestsInXML()
	/* 66: */{
		/* 67:135 */return !System.getProperty(
				"appium.testout.reporter.xml-dialect", "testng")
				.equalsIgnoreCase("junit");
		/* 68: */}

	/* 69: */
	/* 70: */public boolean shouldGenerateVelocityLog()
	/* 71: */{
		/* 72:144 */return System.getProperty(
				"appium.testout.reporter.velocity-log", "false")
				.equalsIgnoreCase("true");
		/* 73: */}

	/* 74: */
	/* 75: */public String getUser()
	/* 76: */throws UnknownHostException
	/* 77: */{
		/* 78:155 */String user = System.getProperty("user.name");
		/* 79:156 */String host = InetAddress.getLocalHost().getHostName();
		/* 80:157 */return user + '@' + host;
		/* 81: */}

	/* 82: */
	/* 83: */public String getJavaInfo()
	/* 84: */{
		/* 85:163 */return String.format(
				"Java %s (%s)",
				new Object[] { System.getProperty("java.version"),
						System.getProperty("java.vendor") });
		/* 86: */}

	/* 87: */
	/* 88: */public String getPlatform()
	/* 89: */{
		/* 90:171 */return String.format(
				"%s %s (%s)",
				new Object[] { System.getProperty("os.name"),
						System.getProperty("os.version"),
						System.getProperty("os.arch") });
		/* 91: */}

	/* 92: */
	/* 93: */public Locale getLocale()
	/* 94: */{
		/* 95:184 */if (System.getProperties().containsKey(
				"appium.testout.reporter.locale"))
		/* 96: */{
			/* 97:186 */String locale = System
					.getProperty("appium.testout.reporter.locale");
			/* 98:187 */String[] components = locale.split("_", 3);
			/* 99:188 */switch (components.length)
			/* 100: */{
			/* 101: */case 1:
				/* 102:190 */return new Locale(locale);
				/* 103: */case 2:
				/* 104:191 */return new Locale(components[0], components[1]);
				/* 105: */case 3:
				/* 106:192 */return new Locale(components[0], components[1],
						components[2]);
				/* 107: */}
			/* 108:193 */System.err.println("Invalid locale specified: "
					+ locale);
			/* 109: */}
		/* 110:196 */return Locale.getDefault();
		/* 111: */}
	/* 112: */
}

/*
 * Location: C:\Users\dell\Desktop\reportng-1.1.4.jar
 * 
 * Qualified Name: appium.testout.reporter.ReportMetadata
 * 
 * JD-Core Version: 0.7.0.1
 */