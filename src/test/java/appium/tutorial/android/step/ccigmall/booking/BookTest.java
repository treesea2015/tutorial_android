package appium.tutorial.android.step.ccigmall.booking;

import org.testng.annotations.Test;

import appium.tutorial.android.act.login.CcigmallAct;
import appium.tutorial.android.util.AppiumHelpers;
import appium.tutorial.android.util.ExcelDataProvider;
import appium.tutorial.android.util.TestException;
import appium.tutorial.android.util.TestStep;

public class BookTest extends TestStep {
	private CcigmallAct ccigmallAct;
/**
 *       数据源文件名必须与测试类名相同，如WechatTest.xls 与WechatTest
 *       数据源文件里的sheet页对应方法名，如WechatTest.xls的sheet页 testLogin 与下面方法名一致，
 *       这样一个数据文件可以有多个sheet页为 该类下的多个测试方法 提供测试数据
 */
	@Test(dataProvider = "GetDataFromExcel", dataProviderClass = ExcelDataProvider.class)
	public void testBooking(String num, String username, String pwd ,String productName) {
		try {
			System.out.println(num);
			ccigmallAct = new CcigmallAct();
			//点击我的鑫网                               /                                                                                                                                                                    
			ccigmallAct.clickMyCenter();
			//点击登录按钮
			ccigmallAct.clickLoginBtn();
			//输入用户
			ccigmallAct.inputUsername(username);
			//输入密码
			ccigmallAct.inputPassword(pwd);
			//点击登入
			ccigmallAct.clickLogin();
			//点击首页
			ccigmallAct.clickFrontPage();
			//输入商品名称进行搜索
			ccigmallAct.searchProductName(productName);
			//点击查询结果
			ccigmallAct.clickSearchResult(productName);
			//ccigmallAct.wait(3000);
			ccigmallAct.clickOneKeyBuy();
			//点击提交订单
			ccigmallAct.clickSubitOrder();
			//点击返回
			AppiumHelpers.back();
			AppiumHelpers.back();
			AppiumHelpers.back();
			AppiumHelpers.back();
			AppiumHelpers.back();
			AppiumHelpers.back();

			//点击我的鑫网                               /                                                                                                                                                                    
			ccigmallAct.clickMyCenter();
			//点击所有订单
			ccigmallAct.clickAllOrder();
			//进入订单详情
			ccigmallAct.enterOrderDetails(productName);
			//获取订单状态
			ccigmallAct.getOrderStatu("待支付");
			AppiumHelpers.back();
			ccigmallAct.enterOrderDetails(productName);
			ccigmallAct.getOrderStatu("已支付");

			//
			
		} catch (Exception e) {
			throw new TestException(this.getClass().getName(), "测试下单失败！>>"+e);
		}

	}

}