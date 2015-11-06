package appium.tutorial.android.act.kaikeba;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import appium.tutorial.android.util.AppiumHelpers;

public class KaikebaAct extends AppiumHelpers {
	private static Logger logger = LoggerFactory.getLogger(KaikebaAct.class);

	
	
	/**
	 * 点击首页
	 */
	public void click() {
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
	
	/**
	 * 点击我的鑫网
	 */
	public void clickMyCenter() {
		logger.info("点击我的鑫网");
		clickById("com.ccigmall.b2c.android:id/tab_4_text");
	}
	
	
}