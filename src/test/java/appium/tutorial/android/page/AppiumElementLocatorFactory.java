package appium.tutorial.android.page;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class AppiumElementLocatorFactory{
	SearchContext driver;
/*	public void AppiumElementLocatorFactory(){
		SearchContext driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), pageObject);

	}*/
	
	public AppiumElementLocatorFactory(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, 30);
		PageFactory.initElements(finder, this);
	}
}
