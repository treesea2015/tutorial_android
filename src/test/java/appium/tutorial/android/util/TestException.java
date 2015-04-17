package appium.tutorial.android.util;

import org.testng.TestNGException;

public class TestException extends TestNGException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestException(String A, String B) {
		super(B);
		AppiumHelpers.screenShot(A);
	}

	public TestException(String string, Throwable throwable) {
		super(string, throwable);
	}

}