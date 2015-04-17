/*   1:    */package appium.tutorial.android.util.reporter;

/*   2:    */
/*   3:    */import java.io.BufferedReader;
/*   4:    */
import java.io.BufferedWriter;
/*   5:    */
import java.io.File;
/*   6:    */
import java.io.FileFilter;
/*   7:    */
import java.io.FileInputStream;
/*   8:    */
import java.io.FileOutputStream;
/*   9:    */
import java.io.FileWriter;
/*  10:    */
import java.io.IOException;
/*  11:    */
import java.io.InputStream;
/*  12:    */
import java.io.InputStreamReader;
/*  13:    */
import java.io.OutputStreamWriter;
/*  14:    */
import java.io.Writer;
/*  15:    */
import java.util.ResourceBundle;
/*  16:    */
import org.apache.velocity.VelocityContext;
/*  17:    */
import org.apache.velocity.app.Velocity;
/*  18:    */
import org.testng.IReporter;

/*  19:    */
/*  20:    */public abstract class AbstractReporter
/* 21: */implements IReporter
/* 22: */{
	/* 23: */private static final String ENCODING = "UTF-8";
	/* 24: */protected static final String TEMPLATE_EXTENSION = ".vm";
	/* 25: */private static final String META_KEY = "meta";
	/* 26: 46 */protected static final ReportMetadata META = new ReportMetadata();
	/* 27: */private static final String UTILS_KEY = "utils";
	/* 28: 48 */private static final ReportNGUtils UTILS = new ReportNGUtils();
	/* 29: */private static final String MESSAGES_KEY = "messages";
	/* 30: 50 */private static final ResourceBundle MESSAGES = ResourceBundle
			.getBundle("appium.testout.reporter.messages.reportng",
					META.getLocale());
	/* 31: */private final String classpathPrefix;

	/* 32: */
	/* 33: */protected AbstractReporter(String classpathPrefix)
	/* 34: */{
		/* 35: 61 */this.classpathPrefix = classpathPrefix;
		/* 36: 62 */Velocity.setProperty("resource.loader", "classpath");
		/* 37: 63 */Velocity
				.setProperty("classpath.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		/* 38: 65 */if (!META.shouldGenerateVelocityLog()) {
			/* 39: 67 */Velocity.setProperty("runtime.log.logsystem.class",
					"org.apache.velocity.runtime.log.NullLogSystem");
			/* 40: */}
		/* 41: */try
		/* 42: */{
			/* 43: 73 */Velocity.init();
			/* 44: */}
		/* 45: */catch (Exception ex)
		/* 46: */{
			/* 47: 77 */throw new ReportNGException(
					"Failed to initialise Velocity.", ex);
			/* 48: */}
		/* 49: */}

	/* 50: */
	/* 51: */protected VelocityContext createContext()
	/* 52: */{
		/* 53: 89 */VelocityContext context = new VelocityContext();
		/* 54: 90 */context.put("meta", META);
		/* 55: 91 */context.put("utils", UTILS);
		/* 56: 92 */context.put("messages", MESSAGES);
		/* 57: 93 */return context;
		/* 58: */}

	/* 59: */
	/* 60: */protected void generateFile(File file, String templateName,
			VelocityContext context)
	/* 61: */throws Exception
	/* 62: */{
		/* 63:105 */Writer writer = new BufferedWriter(new FileWriter(file));
		/* 64: */try
		/* 65: */{
			/* 66:108 */Velocity.mergeTemplate(this.classpathPrefix
					+ templateName, "UTF-8", context, writer);
			/* 67: */
			/* 68: */
			/* 69: */
			/* 70:112 */writer.flush();
			/* 71: */}
		/* 72: */finally
		/* 73: */{
			/* 74:116 */writer.close();
			/* 75: */}
		/* 76: */}

	/* 77: */
	/* 78: */protected void copyClasspathResource(File outputDirectory,
			String resourceName, String targetFileName)
	/* 79: */throws IOException
	/* 80: */{
		/* 81:132 */String resourcePath = this.classpathPrefix + resourceName;
		/* 82:133 */InputStream resourceStream = getClass().getClassLoader()
				.getResourceAsStream(resourcePath);
		/* 83:134 */copyStream(outputDirectory, resourceStream, targetFileName);
		/* 84: */}

	/* 85: */
	/* 86: */protected void copyFile(File outputDirectory, File sourceFile,
			String targetFileName)
	/* 87: */throws IOException
	/* 88: */{
		/* 89:149 */InputStream fileStream = new FileInputStream(sourceFile);
		/* 90: */try
		/* 91: */{
			/* 92:152 */copyStream(outputDirectory, fileStream, targetFileName);
			/* 93: */}
		/* 94: */finally
		/* 95: */{
			/* 96:156 */fileStream.close();
			/* 97: */}
		/* 98: */}

	/* 99: */
	/* 100: */protected void copyStream(File outputDirectory,
			InputStream stream, String targetFileName)
	/* 101: */throws IOException
	/* 102: */{
		/* 103:172 */File resourceFile = new File(outputDirectory,
				targetFileName);
		/* 104:173 */BufferedReader reader = null;
		/* 105:174 */Writer writer = null;
		/* 106: */try
		/* 107: */{
			/* 108:177 */reader = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));
			/* 109:178 */writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(resourceFile), "UTF-8"));
			/* 110: */
			/* 111:180 */String line = reader.readLine();
			/* 112:181 */while (line != null)
			/* 113: */{
				/* 114:183 */writer.write(line);
				/* 115:184 */writer.write(10);
				/* 116:185 */line = reader.readLine();
				/* 117: */}
			/* 118:187 */writer.flush();
			/* 119: */}
		/* 120: */finally
		/* 121: */{
			/* 122:191 */if (reader != null) {
				/* 123:193 */reader.close();
				/* 124: */}
			/* 125:195 */if (writer != null) {
				/* 126:197 */writer.close();
				/* 127: */}
			/* 128: */}
		/* 129: */}

	/* 130: */
	protected void removeEmptyDirectories(File outputDirectory) {
		if (outputDirectory.exists()) {
			for (File file : outputDirectory.listFiles()) {
				file.delete();
			}
		}
	}

	/* 139: */
	/* 140: */private static final class EmptyDirectoryFilter
	/* 141: */implements FileFilter
	/* 142: */{
		/* 143: */public boolean accept(File file)
		/* 144: */{
			/* 145:227 */return (file.isDirectory())
					&& (file.listFiles().length == 0);
			/* 146: */}
		/* 147: */
	}
	/* 148: */
}
