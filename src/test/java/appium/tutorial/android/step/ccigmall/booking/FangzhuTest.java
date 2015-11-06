package appium.tutorial.android.step.ccigmall.booking;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;



public class FangzhuTest {
	private AppiumDriver driver;
	
	@Before
	public void setUp() throws Exception{
		//set up appium
		File classpathRoot=new File(System.getProperty("user.dir"));
		File appDir=new File(classpathRoot,"apps/FangZhurApps");
		File app=new File(appDir,"FangZhurApps.apk");
		DesiredCapabilities capabilities=new DesiredCapabilities();
		capabilities.setCapability("deviceName","emulator-5554");
		capabilities.setCapability("platformVersion","4.4");
		//capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("appPackage", "com.fangzhur.app");
		//capabilities.setCapability("appActivity", ".activity.SplashActivity");
		capabilities.setCapability("unicodeKeyboard", "True");
		capabilities.setCapability("resetKeyboard", "True");
		//capabilities.setCapability("appActivity", ".MainActivity");
		driver=new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
	}
	
	@After
	public void tearDown() throws Exception{
		driver.quit();
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
			//logger.debug("execute adb shell failed:{}",e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	@Test
	public void testFangzhur() throws InterruptedException{
		/*
		//显性等待
		WebElement el=(new WebDriverWait(driver,10)).until(new ExpectedCondition<WebElement>(){
			public WebElement apply(WebDriver d){
				return d.findElement(By.id("com.fangzhur.app:id/iv_person"));
			}
		});
		*///开个模拟器
		//Thread.sleep(10000);
		//隐性等待
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		WebElement el=driver.findElement(By.id("com.fangzhur.app:id/iv_person"));
		el.click();
		//WebElement el1=driver.findElement(By.name("我的"));
		//el1.click();
		Thread.sleep(2000);
		//WebElement hy=driver.findElementByName("欢迎来到房主儿");
		//hy.click();
		driver.findElementById("com.fangzhur.app:id/iv_home").click();
		Thread.sleep(2000);
		driver.findElementByName("无中介租房").click();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElementByName("搜索房子联系房东哦").click();
		Thread.sleep(10000);
		//driver.swipe(240, 750, 240, 210, 3000);
		driver.findElementById("com.fangzhur.app:id/findedt").sendKeys("国美第一城");
		Thread.sleep(5000);
	     //使用adb shell 切换输入法-更改为谷歌拼音
        excuteAdbShell("adb shell ime set com.sohu.inputmethod.sogou/.SogouIME");
        //再次点击输入框，调取键盘，谷歌键盘被成功调出
        Thread.sleep(5000);
       // System.out.println  (driver.getPageSource());
        
      // driver.findElementById("com.fangzhur.app:id/findedt").click();
        //点击右下角的搜索，即ENTER键
       Thread.sleep(5000);
        //((AndroidDriver) driver).sendKeyEvent(AndroidKeyCode.ENTER);
        //再次切回 输入法键盘为Appium unicodeKeyboard，方便下次输入中文
        //excuteAdbShell("adb shell ime set io.appium.android.ime/.UnicodeIME");
	    
		//driver.findElementById("com.fangzhur.app:id/phone_login_yzedt").sendKeys("88033");
		
		//List<WebElement> textFiedsList=driver.findElementsByClassName("android.widget.EditText");
		//textFiedsList.get(0).sendKeys("48366");
		//Thread.sleep(10000);
		//driver.findElementById("com.fangzhur.app:id/phone_login_denglu").click();
		
	}
}