package appium.tutorial.android.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.testng.annotations.DataProvider;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 */

public class ExcelDataProvider {
	private static String defaultPath = System.getProperty("user.dir")
			+ "/testData/"
			+ ResourceBundle.getBundle("appium").getString("env")+"/";
	private static String dataPath;
	private static String methodName;

	// 从EXCL文本文件中获得数据
	@DataProvider(name = "GetDataFromExcel")
	public static Object[][] getTestDataFromCSV(Method m) {
		String[] s = m.getDeclaringClass().getName().toString().split("\\.");
		// 获取类所在的包路径
		dataPath = s[s.length - 1] + ".xls";
		methodName = m.getName();
		// 通过反射获得函数名称，可以为多个测试方法提供数据驱动
		// Object[][] o = new Object[][] {};

		// 取用例数据集d1Test的全部数据(excel数据源)
		/*
		 * if (m.getName().equals("test1")) { return new
		 * BaseExcelData().getData("testA", className,2,5); }
		 * 
		 * if (m.getName().equals("test2")) { return new
		 * BaseExcelData().getData("testB", "DataProvider.xls",2,5); }
		 */
		// 取用例数据集d2Test的全部数据(excel数据源)
		/*
		 * if (m.getName().equals("test1")) { return new
		 * BaseExcelData.getData("d1Test","caipiaoTest.xls") }
		 */
		return new BaseExcelData().getData(methodName, defaultPath + dataPath);
	}

	public void setTestDataToCSV() {

	}

	/**
	 * 数据写入到文件
	 * 
	 * @param sheetName
	 * @param beginCols
	 * @param Row
	 * @param list
	 */
	public void writeData(String dataName, int beginCols, String Row,
			List<String> list) {
		int row = Integer.parseInt(Row);
		File file = new File(defaultPath + dataName + ".xls");
		try {
			new BaseExcelData().writeByRowNum(file, methodName, beginCols, row,
					list);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
