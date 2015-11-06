package appium.tutorial.android.act;
import org.testng.annotations.Test;

import appium.tutorial.android.util.AppiumHelpers;

public class CodeMakerTest extends AppiumHelpers{
	//测试注册
	@Test
	public void testRegister(){
		
		commonClick("立即体验");
		//点击我的
		commonClick("我的");
		//点击登录/注册
		commonClick("登录/注册");
		//点击注册
		commonClick("注册");
		//
		this.commonInput("请输入你的手机号", "18500818021");
		//
		commonClick("发送校验码");
	}
}
