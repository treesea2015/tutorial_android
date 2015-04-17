package appium.tutorial.android.util.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import appium.tutorial.android.util.mail.HtmlMail;

/**
 * 实现FTP 客户端的各种操作
 * 
 * @author treesea888@qq.com
 * @version 1.0.0
 * 
 */
public class FtpUtil {

	/**
	 * slf4j logback
	 */
	private final static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	private static final String TEMPLATES_PATH = "appium/testout/reporter/templates/html/";

	private static FTPClient ftp;
	private static FtpConfig ftpConfig;

	/**
	 * 
	 * @param path
	 *            上传到ftp服务器哪个路径下
	 * @param addr
	 *            地址
	 * @param port
	 *            端口号
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	private static boolean connect(String path, String addr, int port,
			String username, String password, Date reportTime) throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, port);
		result = ftp.login(username, password);
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}

		String env = ResourceBundle.getBundle("appium").getString("env");
		ftp.changeWorkingDirectory(path + env);

		String current = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(reportTime);
		ftp.makeDirectory(current);
		ftp.changeWorkingDirectory(current);
		result = true;
		return result;
	}

	/**
	 * 
	 * @param file
	 *            上传的文件或文件夹
	 * @throws Exception
	 */
	public static void upload(Date reportTime) throws Exception {
		ftpConfig = new FtpConfig();
		connect(ftpConfig.getFtpPath(), ftpConfig.getServer(),
				ftpConfig.getPort(), ftpConfig.getUsername(),
				ftpConfig.getPassword(), reportTime);
		upload(new File(System.getProperty("user.dir")
				+ ftpConfig.getLocalPath()));
		generateMailHtml(reportTime);
		logger.info("测试报告上传服务器完成");
	}

	/**
	 * 
	 * @param file
	 *            上传的文件或文件夹
	 * @throws Exception
	 */
	private static void upload(File file) throws Exception {
		if (file.isDirectory()) {
			ftp.makeDirectory(file.getName());
			ftp.changeWorkingDirectory(file.getName());
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File file1 = new File(file.getPath() + "\\" + files[i]);
				if (file1.isDirectory()) {
					upload(file1);
					ftp.changeToParentDirectory();
				} else {
					File file2 = new File(file.getPath() + "\\" + files[i]);
					FileInputStream input = new FileInputStream(file2);
					ftp.storeFile(file2.getName(), input);
					input.close();
				}
			}
		} else {
			File file2 = new File(file.getPath());
			FileInputStream input = new FileInputStream(file2);
			ftp.storeFile(file2.getName(), input);
			input.close();
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件 编辑邮件内容 后期优化
	 * @throws IOException 
	 */
	public static void generateMailHtml(Date reportTime) throws IOException {
		ResourceBundle bundle = ResourceBundle.getBundle("ftp");
		String to = bundle.getString("toMail");
		if (to == null) {
			return;
		}
		String[] toMail = bundle.getString("toMail").split(";");
		String temp = ResourceBundle.getBundle("appium").getString("env");
		String env = "";
		if ("dev".equals(temp)) {
			env = "开发";
		} else if ("test".equals(temp)) {
			env = "测试";
		} else if ("reg".equals(temp)) {
			env = "回归";
		} else if ("pro".equals(temp)) {
			env = "生产";
		}
		String url = "http://" + bundle.getString("server") + ":8080"
				+ bundle.getString("htmlPath") + temp + "/"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(reportTime)
				+ "/html/" + "index.html";

		/*
		 * int passed = 0; int fail = 0; int skip = 0;
		 */

		StringBuilder html = new StringBuilder("<html>");
		//StringBuilder html = new StringBuilder("<html>");
		 File file=new File(TEMPLATES_PATH+"js.properties");
        if(file.isFile() && file.exists()){ //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
            new FileInputStream(file),"UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
           	 html.append(lineTxt);
            }
            read.close();
        }
		StringBuilder initJs = new StringBuilder("function init(){");
		//initJs.append(" var data_arr = ["+passed/(passed + fail + skip)+", "+skip/(passed + fail + skip)+","+fail/(passed + fail + skip)+"];");
		initJs.append(" var color_arr = [\"#00FF21\", \"#FFAA00\", \"#00AABB\"];");
		initJs.append(" var text_arr = [\"pass\", \"skip\", \"fail\"];");
		initJs.append("drawCircle(\"canvas_circle\", data_arr, color_arr, text_arr);}")   ;
           
		html.append(initJs);
            
        
		StringBuilder sb = new StringBuilder("");
		sb.append("<h4>" + "详细结果查看：" + url + "</h4><br></html>");
		html.append(sb);
		/*
		 * int lineCount = 0;
		 * 
		 * for (ISuite suite : suites) { if (suite.getAllMethods() == null ||
		 * suite.getAllMethods().size() == 0) { continue; } if (lineCount == 0)
		 * { sb.append("<h2 style='margin-left:40px;'>"); } else {
		 * sb.append("<h2>"); } sb.append("suite 模块：" +
		 * suite.getName()).append("/<h2>"); Map<String, ISuiteResult> results =
		 * suite.getResults(); Iterator<String> it =
		 * suite.getResults().keySet().iterator(); while (it.hasNext()) {
		 * ISuiteResult result = results.get(it.next()); ITestContext
		 * testContext = result.getTestContext(); passed = passed +
		 * testContext.getPassedTests().size(); fail = fail +
		 * testContext.getFailedTests().size(); skip = skip +
		 * testContext.getSkippedTests().size();
		 * 
		 * int total = testContext.getFailedTests().size() +
		 * testContext.getPassedTests().size() +
		 * testContext.getSkippedTests().size();
		 * 
		 * // sb.append("<h3 style='margin-left:80px;'>" + "test 模块：" // +
		 * testContext.getName() + "\t总计：" + total + "\t通过：" // +
		 * testContext.getPassedTests().size() + "\t失败：" // +
		 * testContext.getFailedTests().size() + "\t跳过：" // +
		 * testContext.getSkippedTests().size() + "\t通过率：" // +
		 * (testContext.getPassedTests().size() * 100 / total) // + " %" +
		 * "</h3>"); sb.append("<h3 style='margin-left:80px;'>" + "test 模块：" +
		 * testContext.getName() + "</h3>"); } lineCount++; } html.append(
		 * "<h4 style='color:red;'>" + env + "环境     结果统计：" + (passed + fail +
		 * skip) + "\t通过:" + passed + "&nbsp;&nbsp;失败:" + fail +
		 * "&nbsp;&nbsp;跳过:" + skip + "&nbsp;&nbsp;通过率:" + (passed * 100 /
		 * (passed + fail + skip+1)) + " %</h4>")
		 * .append(sb.toString()).append("</html>");
		 */
		String subject = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒")
				.format(reportTime)
				+ "  "
				+ env
				+ "环境      "
				+ bundle.getString("subject");
		HtmlMail.send(toMail, subject, html.toString());
	}

}