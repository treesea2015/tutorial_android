package appium.tutorial.android.util;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author treesea888@qq.com
 * @version 1.0.0
 */

public class BaseExcelData {

	public Object[][] getData(String caseName, String dataFile) {
		return getData(caseName, dataFile, 0);
	}

	public Object[][] getData(String caseName, String dataFile, int rowNum) {
		Object[][] data = null;
		try {
			data = addList(caseName, dataFile, rowNum);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 读取全部数据从第2行起
	 * 
	 * @param caseName
	 * @param dataFile
	 * @param beginRowNum
	 * @param endRowNum
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public Object[][] getData(String caseName, String dataFile,
			int beginRowNum, int endRowNum) throws BiffException, IOException {
		Object[][] data = null;
		try {
			data = addList2(caseName, dataFile, beginRowNum, endRowNum);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	private Object[][] addList(String caseName, String dataFile, int rowNum)
			throws FileNotFoundException {
		ArrayList<Object> list = new ArrayList<Object>();
		// 文件路径
		InputStream is = new FileInputStream(dataFile);

		Object[][] data = null;
		try {
			Workbook wb = Workbook.getWorkbook(is);
			Sheet rs = wb.getSheet(caseName);
			// 获取表格总行数
			int rsRows = rs.getRows();
			// 获取表格总列数
			int rsColumns = rs.getColumns();

			if (rs != null) {
				for (int i = 1; i <= rsRows - 1; i++) {
					for (int j = 0; j <= rsColumns - 1; j++) {
						Cell c = rs.getCell(j, i);
						String cz = c.getContents();
						list.add(cz);
					}
				}
				// System.out.println(list);
			}

			if (rowNum <= 0 || rowNum >= rsRows) {
				data = new Object[rsRows - 1][rsColumns];
				int k = -1;
				for (int i = 0; i < rsRows - 1; i++) {
					for (int j = 0; j < rsColumns; j++) {
						if (k < list.size())
							k++;
						data[i][j] = list.get(k);
						// System.out.println("i="+i+","+"j="+j+","+data[i][j]);
					}
				}
			} else {
				int k = -1;
				data = new Object[rowNum][rsColumns];
				for (int i = 0; i < rowNum; i++) {
					for (int j = 0; j < rsColumns; j++) {
						if (k < list.size())
							k++;
						if (i <= (rowNum - 1)) {
							data[i][j] = list.get(k);
						}
					}
				}
			}
			is.close();
			wb.close();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	private Object[][] addList2(String caseName, String dataFile, int beginNum,
			int endNum) throws BiffException, IOException {

		ArrayList<Object> list = new ArrayList<Object>();
		// 文件路径
		InputStream is = new FileInputStream(dataFile);

		Object[][] data = null;

		Workbook wb = Workbook.getWorkbook(is);
		Sheet rs = wb.getSheet(caseName);
		// 获取表格总行数
		int rsRows = rs.getRows();
		// 获取表格总列数
		int rsColumns = rs.getColumns();

		if (rs != null) {
			for (int i = 1; i <= rsRows - 1; i++) {
				for (int j = 0; j <= rsColumns - 1; j++) {
					Cell c = rs.getCell(j, i);
					String cz = c.getContents();
					list.add(cz);
				}
			}
			// System.out.println(list);
		}

		int sub = (endNum - beginNum) + 1;
		data = new Object[sub][rsColumns];
		if (beginNum <= 0 || endNum > rsRows) {
			if (beginNum <= 0 && endNum > rsRows) {
				beginNum = 0;
				endNum = rsRows;
				data = new Object[rsRows][rsColumns];
				for (int i = 0; i < sub; i++) {
					for (int j = 0; j < rsColumns; j++) {
						if (beginNum < rsRows * rsColumns)
							data[i][j] = list.get(beginNum);
						beginNum++;
					}
				}
			} else if (beginNum <= 0 && endNum <= rsRows) {
				beginNum = 0;
				sub = (endNum - beginNum);
				data = new Object[endNum][rsColumns];
				for (int i = 0; i < sub; i++) {
					for (int j = 0; j < rsColumns; j++) {
						if (beginNum < sub * rsColumns)
							data[i][j] = list.get(beginNum);
						beginNum++;
					}
				}
			} else {
				endNum = rsRows;
				sub = (endNum - beginNum) + 1;
				data = new Object[sub][rsColumns];
				for (int i = 0; i < sub; i++) {
					for (int j = 0; j < rsColumns; j++) {
						if (beginNum <= sub * rsColumns)
							data[i][j] = list.get(beginNum - 1);
						beginNum++;
					}
				}

			}
		} else {
			int k = 0;
			int a = beginNum * rsColumns;
			for (int i = 0; i < sub; i++) {
				for (int j = 0; j < rsColumns; j++) {
					if (k < sub * rsColumns)
						data[i][j] = list.get(a + k - 2);
					k++;

				}
			}
		}
		wb.close();

		return data;
	}

	/**
	 * 
	 * @param file
	 *            操作文件对象
	 * @param sheetName
	 *            sheet名称
	 * @param beginCols
	 *            开始列
	 * @param beginRow
	 *            开始行
	 * @param list
	 *            数据内容
	 * @throws IOException
	 * @throws WriteException
	 * @throws BiffException
	 */
	public void writeByRowNum(File file, String sheetName, int beginCols,
			int beginRow, List<String> list) throws IOException,
			WriteException, BiffException {
		WritableWorkbook book = null;
		WritableSheet sheet = null;
		// excel文件不存在 或 文件大小为0时 进行创建
		if (!file.exists() || file.length() == 0) {
			file.createNewFile();
			book = Workbook.createWorkbook(file);
			book.createSheet(sheetName, 0);
			book.write();
			book.close();
		}
		Workbook wb = Workbook.getWorkbook(file);
		book = Workbook.createWorkbook(file, wb);
		sheet = book.getSheet(sheetName);
		// sheet页不存在时 创建
		if (sheet == null) {
			sheet = book.createSheet(sheetName, 0);
		}
		beginCols = beginCols - 1;
		// 遍历文件内容 写入
		for (String data : list) {
			sheet.addCell(new Label(beginCols++, beginRow, data));
		}

		// 关闭
		book.write();
		book.close();
	}

}
