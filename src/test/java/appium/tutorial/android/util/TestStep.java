package appium.tutorial.android.util;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * @author treesea888@qq.com
 * 
 */
public class TestStep extends AppiumFactory {

	/**
	 * test before
	 * 
	 * @param args
	 * @throws Exception
	 */
	@BeforeSuite
	public void beforeSuite() throws Exception {

	}

	/**
	 * test after
	 * 
	 * @throws Exception
	 */
	@AfterSuite
	public void afterSuite() throws Exception {

	}

	/**
	 * 
	 */
	@BeforeTest
	public void beforeTest() {

	}

	/**
	 * 
	 */
	@AfterTest
	public void afterTest() {

	}

	/**
	 * 
	 * @param sys
	 * @throws Exception
	 */
	@BeforeClass
	public void beforeClass() throws Exception {

	}

	/**
	 * @throws Exception
	 * 
	 */
	@AfterClass
	public void afterClass() throws Exception {

	}

	/**
	 * @throws Exception
	 * 
	 */
	@BeforeMethod
	public void beforeMethod() throws Exception {
		setUp();

	}

	/**
	 * @throws Exception
	 * 
	 */
	@AfterMethod
	public void afterMethod() throws Exception {
		tearDown();
	}

}
