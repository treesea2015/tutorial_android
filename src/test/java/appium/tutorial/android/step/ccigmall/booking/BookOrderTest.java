package appium.tutorial.android.step.ccigmall.booking;

import org.testng.annotations.Test;

import appium.tutorial.android.act.login.BookOrderAct;
import appium.tutorial.android.util.AppiumHelpers;
import appium.tutorial.android.util.ExcelDataProvider;
import appium.tutorial.android.util.TestException;
import appium.tutorial.android.util.TestStep;

public class BookOrderTest extends TestStep {
	private BookOrderAct bookOrderAct;
/**
 *       数据源文件名必须与测试类名相同，如WechatTest.xls 与WechatTest
 *       数据源文件里的sheet页对应方法名，如WechatTest.xls的sheet页 testLogin 与下面方法名一致，
 *       这样一个数据文件可以有多个sheet页为 该类下的多个测试方法 提供测试数据
 */
	@Test(dataProvider = "GetDataFromExcel", dataProviderClass = ExcelDataProvider.class)
	public void testBooking(String num, String username, String pwd ,String productName) {
		try {
			System.out.println(num);
			bookOrderAct = new BookOrderAct();
			Thread.sleep(10*1000);
			bookOrderAct.swipe(5, 5, 5, 10, 2000);
			//点击我的鑫网                               /                                                                                                                                                                    
			bookOrderAct.clickMyCenter();
			//点击登录按钮
			bookOrderAct.clickLoginBtn();
			System.out.println(AppiumHelpers.driver.getPageSource());
			//输入用户
			bookOrderAct.inputUsername(username);
			System.out.println(AppiumHelpers.driver.getPageSource());
			//输入密码
			bookOrderAct.inputPassword(pwd);
			//点击登入
			bookOrderAct.clickLogin();
			//点击首页
			bookOrderAct.clickFrontPage();
			//输入商品名称进行搜索
			bookOrderAct.searchProductName(productName);
			//点击查询结果
			bookOrderAct.clickSearchResult(productName);
			//bookOrderAct.wait(3000);
			bookOrderAct.clickOneKeyBuy();
			//点击提交订单
			bookOrderAct.clickSubitOrder();
			//点击返回
			AppiumHelpers.back();
			AppiumHelpers.back();
			AppiumHelpers.back();
			AppiumHelpers.back();
			AppiumHelpers.back();
			AppiumHelpers.back();

			//点击我的鑫网                               /                                                                                                                                                                    
			bookOrderAct.clickMyCenter();
			//点击所有订单
			bookOrderAct.clickAllOrder();
			//进入订单详情
			bookOrderAct.enterOrderDetails(productName);
			//获取订单状态
			bookOrderAct.getOrderStatu("待支付");
			AppiumHelpers.back();
			bookOrderAct.enterOrderDetails(productName);
			bookOrderAct.getOrderStatu("已支付");
			//将其存入文件名为【产生的数据.xls，第四列起】
			bookOrderAct.saveData("订单编号", 4, num);			
			
		} catch (Exception e) {
			bookOrderAct.excuteAdbShell("adb shell ime set com.android.inputmethod.latin/.LatinIME");
			throw new TestException(this.getClass().getName(), "测试下单失败！>>"+e);
		}

	}

}