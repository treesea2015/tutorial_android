package appium.tutorial.android.util;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static appium.tutorial.android.util.AppiumHelpers.driver;

public class AppiumFactory {

	/** Keep the same date prefix to identify job sets. **/
	private static Date date = new Date();

	/** Run before each test,read properties from appium.properities **/
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		ResourceBundle bundle = ResourceBundle.getBundle("appium");
		capabilities.setCapability("appium-version",
				bundle.getString("appium-version"));
		capabilities.setCapability("platformName",
				bundle.getString("platformName"));
		capabilities
				.setCapability("deviceName", bundle.getString("deviceName"));
		capabilities.setCapability("platformVersion",
				bundle.getString("platformVersion"));
		capabilities.setCapability("name", "Java Android tutorial " + date);
		URL serverAddress;
		serverAddress = new URL(bundle.getString("serverAddress"));
		String localApp = bundle.getString("localApp");
		String userDir = System.getProperty("user.dir");
		String appPath = Paths.get(userDir, localApp).toAbsolutePath()
				.toString();
		capabilities.setCapability("app", appPath);
		driver = new AndroidDriver(serverAddress, capabilities);
		driver.manage()
				.timeouts()
				.implicitlyWait(Integer.parseInt(bundle.getString("timeout")),
						TimeUnit.SECONDS);
		AppiumHelpers.init(driver, serverAddress);
	}

	/** Run after each test **/

	public void tearDown() throws Exception {
		if (driver != null)
			driver.quit();
	}

}