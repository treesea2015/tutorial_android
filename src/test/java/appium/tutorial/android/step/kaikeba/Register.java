package appium.tutorial.android.step.kaikeba;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

import appium.tutorial.android.act.kaikeba.KaikebaAct;
import appium.tutorial.android.util.TestStep;

public class Register extends TestStep {
	private KaikebaAct kaikebaAct;

	/**
	 * 数据源文件名必须与测试类名相同，如WechatTest.xls 与WechatTest
	 * 数据源文件里的sheet页对应方法名，如WechatTest.xls的sheet页 testLogin 与下面方法名一致，
	 * 这样一个数据文件可以有多个sheet页为 该类下的多个测试方法 提供测试数据
	 * @throws InterruptedException 
	 */
	@Test(description="")
	public void testRegister() throws InterruptedException {
		String mobile = "185"+new  SimpleDateFormat("yyyyMMdd").format(new Date());
		kaikebaAct = new KaikebaAct();
		kaikebaAct.commonClick("立即体验");
		// 点击我的
		kaikebaAct.commonClick("我的");
		// 点击登录/注册
		kaikebaAct.commonClick("登录/注册");
		// 点击注册
		kaikebaAct.commonClick("注册");
		//输入你的手机号
		kaikebaAct.commonInput("请输入你的手机号", mobile);
		//点击获取验证码
		kaikebaAct.commonClick("发送校验码");
		//输入短信验证码
		kaikebaAct.commonInput("短信校验码", "123456");
		//输入密码
		kaikebaAct.commonInput("com.kaikeba.phone:id/et_regist_password", "123456");
		//输入昵称
		kaikebaAct.commonInput("昵称", "开课吧测试");
		//点击 注册
		kaikebaAct.commonClick("com.kaikeba.phone:id/tv_signup");
		
		//kaikebaAct.pasue(9);




	}
	@Test(description="")
	public void testRegister1() throws InterruptedException {
		String mobile = "185"+new  SimpleDateFormat("yyyyMMdd").format(new Date());
		kaikebaAct = new KaikebaAct();
		//kaikebaAct.commonClick("立即体验");
		// 点击我的
		kaikebaAct.commonClick("我的");
		// 点击登录/注册
		kaikebaAct.commonClick("登录/注册");
		// 点击注册
		kaikebaAct.commonClick("注册");
		//输入你的手机号
		kaikebaAct.commonInput("请输入你的手机号", mobile);
		//点击获取验证码
		kaikebaAct.commonClick("发送校验码");
		//输入短信验证码
		kaikebaAct.commonInput("短信校验码", "123456");
		//输入密码
		kaikebaAct.commonInput("com.kaikeba.phone:id/et_regist_password", "123456");
		//输入昵称
		kaikebaAct.commonInput("昵称", "开课吧测试");
		//点击 注册
		kaikebaAct.commonClick("com.kaikeba.phone:id/tv_signup");
		
		//kaikebaAct.pasue(9);




	}

}