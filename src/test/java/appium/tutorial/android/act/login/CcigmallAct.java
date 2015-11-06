package appium.tutorial.android.act.login;


import java.sql.SQLException;

import io.appium.java_client.android.AndroidKeyCode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import appium.tutorial.android.util.AppiumHelpers;
import appium.tutorial.android.util.UpdateOrderStatus;

public class CcigmallAct extends AppiumHelpers {
	private static Logger logger = LoggerFactory.getLogger(CcigmallAct.class);

	
	/**
	 * 点击首页
	 */
	public void clickFrontPage() {
		logger.info("点击首页");
		commonClick("首页");
	}
	
	/**
	 * 点击我的鑫网
	 */
	public void clickMyCenter() {
		logger.info("点击我的鑫网");
		clickById("com.ccigmall.b2c.android:id/tab_4_text");
	}
	
	/**
	 * 点击登录按钮
	 */
	public void clickLoginBtn() {
		logger.info("点击登录按钮");
		clickById("com.ccigmall.b2c.android:id/btn_login");
	}
	/**
	 * 输入用户名
	 */
	public void inputUsername(String username) {
		logger.info("输入用户名|手机号：{}",username);
		inputById("com.ccigmall.b2c.android:id/login_usernameEdt",username);
	}
	
	/**
	 * 输入密码
	 */
	public void inputPassword(String pwd) {
		logger.info("输入用户密码：{}",pwd);
		inputById("com.ccigmall.b2c.android:id/login_psdEdt",pwd);
	}
	
	/**
	 * 点击登录
	 */
	public void clickLogin() {
		logger.info("点击登入");
		commonClick("com.ccigmall.b2c.android:id/login_loginBtn");
	}

	/**
	 * 输入商品名称进行搜索
	 * @throws InterruptedException 
	 */
	public void searchProductName(String productName) throws InterruptedException {
		logger.info("输入商品名称:{},进行搜索",productName);
		clickById("com.ccigmall.b2c.android:id/search_view_txt");
		//可输入中文，但此时键盘不可用，输入法键盘为Appium unicodeKeyboard
		inputById("com.ccigmall.b2c.android:id/search_input","回归150608163530");
		//使用adb shell 切换输入法-更改为谷歌拼音
		excuteAdbShell("adb shell ime set com.android.inputmethod.pinyin/.PinyinIME");
		this.pasue(3);
		//再次点击输入框，调取键盘，谷歌键盘被成功调出
		clickById("com.ccigmall.b2c.android:id/search_input");
		//点击右下角的搜索，即ENTER键	
		sendKeyEvent(AndroidKeyCode.ENTER);
		//再次切回 输入法键盘为Appium unicodeKeyboard，方便下次输入中文
		excuteAdbShell("adb shell ime set io.appium.android.ime/.UnicodeIME");
	}
	/**
	 * 点击查询结果
	 * @param productName
	 */
	public void clickSearchResult(String productName){
		String actPName = getText(element(By.id("com.ccigmall.b2c.android:id/tv_goods_name")));
		Assert.assertEquals(productName, actPName);
		clickById("com.ccigmall.b2c.android:id/tv_goods_name");
	}
	/**
	 * 点击加入购物车
	 */
	public void clickSearchResult(){
		logger.info("点击加入购物车");
		clickById("com.ccigmall.b2c.android:id/tv_goods_name");
	}
	
	/**
	 * 点击立即购买
	 */
	public void clickOneKeyBuy(){
		logger.info("点击立即购买");
		clickById("com.ccigmall.b2c.android:id/img_onekey_buy");
	}
	/**
	 * 点击提交订单
	 */
	public void clickSubitOrder(){
		logger.info("点击提交订单");
		clickById("com.ccigmall.b2c.android:id/btn_submit_order");
	}
	
	/**
	 * 点击全部订单
	 */
	public void clickAllOrder(){
		logger.info("点击全部订单");
		clickById("com.ccigmall.b2c.android:id/my_wallet_text");
	}
	
	/**
	 * 进入订单详情
	 */
	public void enterOrderDetails(String productName){
		logger.info("进入 {}：的订单详情。",productName);
		clickById("com.ccigmall.b2c.android:id/order_single_name");
	}
	/**
	 * 获取订单状态 订单编号
	 */
	public String getOrderStatu(String orderStatu){
		WebElement eStatu = element(By.id("com.ccigmall.b2c.android:id/order_status"));
		WebElement eOrderId = element(By.id("com.ccigmall.b2c.android:id/order_id"));
		String statu = getText(eStatu);
		String orderId = getText(eOrderId).substring(5);
		Assert.assertEquals(statu, orderStatu);
		logger.info("获取订单状态：{},订单编号：{}",statu,orderId);
		try {
			UpdateOrderStatus.wapPay(orderId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderId;
	}
	/**
	 * 点击返回
	 */
	public void clickBack(){
		logger.info("点击返回");
		clickById("com.ccigmall.b2c.android:id/default_top_bar_left_btn");
	}
	
	
	
/*	*//**
	 * 选择语言
	 *//*
	public void clickSimpleLangnage() {
		logger.info("选择简体中文");
		commonClick("简体中文");
	}

	*//**
	 * 选择save
	 *//*
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

	*//**
	 * 选择Login
	 *//*
	public void clickLogin() {
		logger.info("选择Login");
		clickById("com.tencent.mm:id/bc5");
	}

	*//**
	 * 选择其他方式登陆
	 *//*
	public void clickOtherWay() {
		logger.info("选择其他方式登陆");
		clickById("com.tencent.mm:id/aj7");
	}

	*//**
	 * 输入用户名
	 *//*
	public void inputUserName(String username) {
		logger.info("输入用户名为：{}", username);
		inputById("com.tencent.mm:id/df", username);
	}

	*//**
	 * 输入密码
	 *//*
	public void inputPassword(String password) {
		logger.info("输入密码名为：{}", password);
		inputByXpath("(//*[@resource-id='com.tencent.mm:id/df'])[2]", password);
	}

	*//**
	 * 点击登录
	 *//*
	public void clickInto() {
		logger.info("点击登录");
		clickById("com.tencent.mm:id/aip");
	}*/
}