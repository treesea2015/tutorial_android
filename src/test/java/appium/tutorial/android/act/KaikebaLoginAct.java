package appium.tutorial.android.act;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import appium.tutorial.android.util.AppiumHelpers;

public class KaikebaLoginAct extends AppiumHelpers {
	private static Logger logger = LoggerFactory.getLogger(KaikebaLoginAct.class);

	/**
	 * 判断是否弹窗
	 */
	public void isAlertExist() {
		if (isElementExist("Cancel", 20)) {
			logger.info("点击弹窗的取消按钮");
			commonClick("Cancel");
		}
		if (isElementExist("取消", 20)) {
			logger.info("点击弹窗的取消按钮");
			commonClick("取消");
		}
	}
	

	/**
	 * 选择语言
	 */
	public void clickLangnage() {
		logger.info("选择语言");
		commonClick("com.tencent.mm:id/bz4");
	}

	/**
	 * 选择语言
	 */
	public void clickSimpleLangnage() {
		logger.info("选择简体中文");
		commonClick("简体中文");
	}

	/**
	 * 选择save
	 */
	public void clickSave() {
		if (isElementExist("Save", 20)) {
			logger.info("保存");
			commonClick("Save");
			
		}
		if (isElementExist("保存", 20)) {
			logger.info("保存");
			commonClick("保存");
		}
	}

	/**
	 * 选择Login
	 */
	public void clickLogin() {
		logger.info("选择Login");
		clickById("com.tencent.mm:id/bc5");
	}

	/**
	 * 选择其他方式登陆
	 */
	public void clickOtherWay() {
		logger.info("选择其他方式登陆");
		clickById("com.tencent.mm:id/aj7");
	}

	/**
	 * 输入用户名
	 */
	public void inputUserName(String username) {
		logger.info("输入用户名为：{}", username);
		inputById("com.tencent.mm:id/df", username);
	}

	/**
	 * 输入密码
	 */
	public void inputPassword(String password) {
		logger.info("输入密码名为：{}", password);
		inputByXpath("(//*[@resource-id='com.tencent.mm:id/df'])[2]", password);
	}

	/**
	 * 点击登录
	 */
	public void clickInto() {
		logger.info("点击登录");
		clickById("com.tencent.mm:id/aip");
	}
}