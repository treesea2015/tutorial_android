/**
 * 
 */
package appium.tutorial.android.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jianping.gao
 * 
 */
public class UpdateOrderStatus {

	/**
	 * slf4j
	 */
	private final static Logger logger = LoggerFactory
			.getLogger(UpdateOrderStatus.class);

	static Connection con = null;// 创建一个数据库连接
	static PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	static ResultSet result; // 创建一个结果集对象

	/**
	 * wap 下单后根据 订单号 进行支付
	 * @param orderId
	 * @throws SQLException
	 */
	public static synchronized void wapPay(String orderId) throws SQLException {
		List<String> sqls = new ArrayList<String>();
		sqls.add("update C_ORDER t "+
				"set t.status  = 21,"+
					"t.pay_way  = '02',"+
					"t.paid_price = 40,"+
					"t.payment_no = '454545455',"+
					"t.order_seq_no = '12323434343',"+
					"t.pay_id = '12323434343',"+
					"t.is_binning='1'"+
				" where t.id = ?" );
		update(sqls,orderId);
		logger.info("wap支付完成 {}", orderId);
	}

	private static synchronized void update(List<String> sqls,String payId)
			throws SQLException {
		try {		ResourceBundle bundle = ResourceBundle.getBundle("appium");
					String  env = bundle.getString("env");
			String url = bundle.getString(env+"_jdbc_url");
			String username = bundle.getString(env+"_jdbc_name");
			String passwrod = bundle.getString(env+"_jdbc_password");
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
			logger.info("开始尝试连接数据库！{}  {}  {}", url, username, passwrod);
			con = DriverManager.getConnection(url, username, passwrod);// 获取连接
			if(!con.isClosed()){
				logger.info("连接成功!");
			}			
			for (int i = 0; i < sqls.size(); i++) {
				logger.info("执行 {}", sqls.get(i));
				pre = con.prepareStatement(sqls.get(i));// 实例化预编译语句
				pre.setString(1, payId);// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
				int result = pre.executeUpdate();
				logger.info("结果 {}",result);
			}
		} catch (SQLException e) {
			throw new SQLException("update order status error", e);
		} catch (ClassNotFoundException e) {
			throw new SQLException("not found oracle driver error", e);
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
				logger.info("数据库连接已关闭！");
			} catch (SQLException e) {
				throw new SQLException("close database error", e);
			}
		}
	}
	
	@Test
	public void d() throws SQLException{
		//first("503384301918858327");
	
		wapPay("503384301918858327");
	}
	
}
