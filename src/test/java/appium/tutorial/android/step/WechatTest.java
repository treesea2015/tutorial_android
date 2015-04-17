package appium.tutorial.android.step;

import org.testng.annotations.Test;

import appium.tutorial.android.act.WechatAct;
import appium.tutorial.android.util.ExcelDataProvider;
import appium.tutorial.android.util.TestException;
import appium.tutorial.android.util.TestStep;

public class WechatTest extends TestStep {
	private WechatAct wechatAct;
/**
 *       数据源文件名必须与测试类名相同，如WechatTest.xls 与WechatTest
 *       数据源文件里的sheet页对应方法名，如WechatTest.xls的sheet页 testLogin 与下面方法名一致，
 *       这样一个数据文件可以有多个sheet页为 该类下的多个测试方法 提供测试数据
 */
	@Test(dataProvider = "GetDataFromExcel", dataProviderClass = ExcelDataProvider.class)
	public void testLogin(String num, String qq, String pwd) {
		try {
			wechatAct = new WechatAct();
			// 判断弹框是否存在
			wechatAct.isAlertExist();
			//语言随机
			wechatAct.clickLangnage();
			//选择中文
			wechatAct.clickSimpleLangnage();
			//保存
			wechatAct.clickSave();
			//登录
			wechatAct.clickLogin();
			//其他方式登录
			wechatAct.clickOtherWay();
			//输入qq
			wechatAct.inputUserName(qq);
			//qq密码
			wechatAct.inputPassword(pwd);
			//点击登进
			wechatAct.clickInto();
			//测试产生的数据
			wechatAct.addData("产生的数据1");
			wechatAct.addData("产生的数据2");
			wechatAct.addData("产生的数据3");
			//将其存入文件名为【产生的数据.xls，第四列起】
			wechatAct.saveData("产生的数据", 4, num);
		} catch (Exception e) {
			throw new TestException(this.getClass().getName(), "测试登录失败！");
		}

	}

}