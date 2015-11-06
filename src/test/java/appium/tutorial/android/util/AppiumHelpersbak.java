package appium.tutorial.android.util;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AppiumHelpersbak {

	public static AndroidDriver driver;
	public static URL serverAddress;
	private static WebDriverWait driverWait;
	private static ArrayList<String> data;
	private static ExcelDataProvider aExcelDataProvider;
	private static Logger logger = LoggerFactory.getLogger(AppiumHelpersbak.class);

	public void addData(String d) {
		data.add(d);
	}

	/**
	 * 文件写入
	 * 
	 * @param dataName
	 * @param beginCol
	 * @param row
	 * @param list
	 */
	public void saveData(String dataName, int beginCol, String row) {
		logger.debug("写入操作：正在向文件名为：{}，第{}行，第{}列起依次写入数据：{}", dataName, row,
				beginCol, data);
		aExcelDataProvider.writeData(dataName, beginCol, row, getData());
	}

	/**
	 * Initialize the webdriver. Must be called before using any helper methods.
	 * *
	 */
	public static void init(AndroidDriver webDriver, URL driverServerAddress) {
		data = new ArrayList<String>();
		aExcelDataProvider = new ExcelDataProvider();
		driver = webDriver;
		serverAddress = driverServerAddress;
		int timeoutInSeconds = 60;
		// must wait at least 60 seconds for running on Sauce.
		// waiting for 30 seconds works locally however it fails on Sauce.
		driverWait = new WebDriverWait(webDriver, timeoutInSeconds);
	}
	
	/**
	 * 根据id点击
	 * 
	 * @param id
	 */
	public void clickById(String id) {
		try {
			element(By.id(id)).click();
			logger.debug("click id: {} successfully", id);
		} catch (Exception e) {
			logger.error("failed to click id: {}", id);
			e.printStackTrace();
		}
	}

	/**
	 * 根据xpath点击
	 * 
	 * @param xpath
	 */
	public void clickByXpath(String xpath) {
		try {
			element(By.xpath(xpath)).click();
			logger.debug("click xpath: {} successfully", xpath);
		} catch (Exception e) {
			logger.error("failed to click xpath: {}", xpath);
			e.printStackTrace();
		}
	}

	/**
	 * 根据className点击
	 * 
	 * @param xpath
	 */
	public void clickByClassName(String className) {
		try {
			element(By.className(className)).click();
			logger.debug("click className: {} successfully", className);
		} catch (Exception e) {
			logger.error("failed to click className: {}", className);
			e.printStackTrace();
		}
	}

	/**
	 * 根据id 定位后 输入text
	 * 
	 * @param id
	 * @param text
	 */
	public void inputById(String id, String text) {
		try {
			WebElement e = element(By.id(id));
			e.clear();
			e.sendKeys(text);
			logger.debug("click id: {} ,and  input {} successfully", id, text);
		} catch (Exception e) {
			logger.error("failed to click id: {},and  input {} error ", id,
					text);
			e.printStackTrace();
		}
	}

	/**
	 * 根据xpath 定位后 输入text
	 * 
	 * @param xpath
	 * @param text
	 */
	public void inputByXpath(String xpath, String text) {
		try {
			WebElement e = element(By.xpath(xpath));
			e.clear();
			e.sendKeys(text);
			logger.debug("click xpath: {} ,and  input {} successfully", xpath,
					text);
		} catch (Exception e) {
			logger.error("failed to click xpath: {},and  input {} error ",
					xpath, text);
			e.printStackTrace();
		}
	}

	/**
	 * 根据className 定位后 输入text
	 * 
	 * @param className
	 * @param text
	 */
	public void inputByClassName(String className, String text) {
		try {
			WebElement e = element(By.className(className));
			e.clear();
			e.sendKeys(text);
			logger.debug("click className: {} ,and  input {} successfully",
					className, text);
		} catch (Exception e) {
			logger.error("failed to click className: {},and  input {} error ",
					className, text);
			e.printStackTrace();
		}
	}

	/**
	 * Set implicit wait in seconds * 设置超时时间
	 */
	public static void setWaitTime(int seconds) {
		logger.debug("设置超时时间：{}", seconds);
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);


	}

	/**
	 * Return an element by locator *
	 */
	public static WebElement element(By locator) {
		return driver.findElement(locator);

	}

	/**
	 * Return a list of elements by locator *
	 */
	public static List<WebElement> elements(By locator) {
		List<WebElement> eles = driver.findElements(locator);
		logger.debug("<集合>定位元素:{},集合大小:{},集合的值：{}", locator, eles.size(), eles);
		return eles;
	}

	/**
	 * 点击事件
	 * 
	 * @param element
	 */
	public static void click(WebElement element) {
		element.click();
	}

	/**
	 * 
	 * @param value
	 * @param index
	 */
	public static void clickListWelement(String value, int index) {
		click(elements(byXpath(value)).get(index - 1));

	}

	/**
	 * 输入事件
	 * 
	 * @param element
	 * @param text
	 */
	public static void input(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * 后退 Press the back button *
	 */
	public static void back() {
		logger.debug("后退");
		driver.navigate().back();
	}

	public static By byXpath(String value) {
		logger.debug("定位元素：{}", value);
		return By.xpath("//*[@content-desc=\"" + value
				+ "\" or @resource-id=\"" + value + "\" or @text=\"" + value
				+ "\"] | //*[contains(translate(@content-desc,\"" + value
				+ "\",\"" + value + "\"), \"" + value
				+ "\") or contains(translate(@text,\"" + value + "\",\""
				+ value + "\"), \"" + value + "\") or @resource-id=\"" + value
				+ "\"]");
	}

	/**
	 * click the item you can see ,
	 * 
	 * @param value
	 */
	public void commonClick(String value) {
		click(element(byXpath(value)));

	}

	/**
	 * 
	 * @param value
	 * @param text
	 */
	public void commonInput(String value, String text) {
		WebElement e = element(byXpath(value));
		input(e, text);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static WebElement findByVaule(String value) {
		return element(byXpath(value));
	}

	/**
	 * Wait 30 seconds for locator to find an element *
	 */
	public static WebElement waitElement(By locator) {
		return driverWait.until(ExpectedConditions
				.visibilityOfElementLocated(locator));
	}

	/**
	 * Wait 60 seconds for locator to find all elements *
	 */
	public static List<WebElement> waitAll(By locator) {
		return driverWait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * Wait 60 seconds for locator to not find a visible element *
	 */
	public static boolean waitInvisible(By locator) {
		return driverWait.until(ExpectedConditions
				.invisibilityOfElementLocated(locator));
	}

	/**
	 * Return an element that contains name or text *
	 */
	public static WebElement scroll_to(String value) {
		return driver.scrollTo(value);
	}

	/**
	 * Return an element that exactly matches name or text *
	 */
	public static WebElement scroll_to_exact(String value) {
		return driver.scrollToExact(value);
	}

	/**
	 * 用例执行成功/失败时 进行截图
	 * 
	 * @param desc
	 *            截图名称描述
	 */
	public static void screenShot(String desc) {
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		logger.info("开始截图，名称{}", desc + ".jpg");
		File screenshot = new File("test-output" + File.separator
				+ "html/screenshot" + File.separator + desc + ".jpg");
		try {
			FileUtils.copyFile(scrFile, screenshot);
		} catch (IOException e) {
			logger.error("截图操作失败" + e.getMessage());
		}
	}

	/**
	 * 查找元素是否存在
	 * 
	 * @param xpath
	 * @return
	 */
	public boolean isElementExist(String xpath, int seconds) {
		try {
			setWaitTime(seconds);
			driver.findElement(byXpath(xpath));
			logger.debug("found element by xpath:{}  in {} s", xpath, seconds);
			return true;
		} catch (NoSuchElementException e) {
			logger.error("not found element by xpath:{}  in {} s", xpath,
					seconds);
			
			return false;
		}
	}

	public ArrayList<String> getData() {
		return data;
	}
	/**
	 * 
	 * @param shell
	 */
	public void excuteAdbShell(String shell){
		try {
			Runtime.getRuntime().exec(shell);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("execute adb shell failed:{}",e.getMessage());
			e.printStackTrace();
		}

	}
	/**
	 * @param e
	 * @return 元素Text
	 */
	public String getText(WebElement e){
		return e.getText();
	}
	/*/
	 * 模拟键盘操作
	 */
	public void sendKeyEvent(int keyValue){
		driver.sendKeyEvent(keyValue);
	}
	public static void clickScreen(int x, int y, int duration
			) {
			JavascriptExecutor js = driver;
			HashMap<String, Integer> tapObject = new HashMap<String, Integer>();
			tapObject.put("x", x);
			tapObject.put("y", y);
			tapObject.put("duration", duration);
			js.executeScript("mobile: tap", tapObject);
			}
}