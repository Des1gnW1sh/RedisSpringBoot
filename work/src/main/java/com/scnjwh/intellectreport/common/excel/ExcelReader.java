package com.scnjwh.intellectreport.common.excel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

/**
 * excel操作工具类
 * 
 * @author timo
 */
public class ExcelReader {
	private BufferedReader reader = null;// 创建文件输入流
	private String filetype;// 文件类型
	private InputStream is = null;// 文件二进制输入流
	private int currSheet;// 当前sheet
	private int currPosition;// 当前位置
	private int numOfSheets;// sheet数量
	HSSFWorkbook workbook = null;// HSSFWorkbook
	private static String EXCEL_LINE_DELIMITER = " ";// 设置cell之间以空格分隔

	// private static int MAX_EXCEL_COLUMNS=64;//设置最大列数

	public ExcelReader() {
	}

	public ExcelReader(String inputFile) throws IOException, Exception {
		// 判断参数是否为空或者没有意义
		if (StringUtils.isBlank(inputFile)) {
			throw new IOException("参数不正确");
		}
		// 取得文件名的后缀名赋值给filetype
		this.filetype = inputFile.substring(inputFile.lastIndexOf(".") + 1);
		currPosition = 0;
		currSheet = 0;
		is = new FileInputStream(inputFile);
		if (filetype.equalsIgnoreCase("text")) {
			reader = new BufferedReader(new InputStreamReader(is));
		} else if (filetype.equalsIgnoreCase("xls")) {
			workbook = new HSSFWorkbook(is);
			numOfSheets = workbook.getNumberOfSheets();
		} else {
			throw new Exception("类型暂不支持");
		}
	}

	// 读取文件的一行
	public String readLine() throws IOException {
		if (filetype.equalsIgnoreCase("text")) {
			String str = reader.readLine();
			while (str.trim().equals("")) {
				str = reader.readLine();
			}
			return str;
		} else if (filetype.equalsIgnoreCase("xls")) {
			HSSFSheet sheet = workbook.getSheetAt(currSheet);
			if (currPosition > sheet.getLastRowNum()) {// 判断当前行是否到当前sheet结尾
				currPosition = 0;
				while (currSheet != numOfSheets - 1) {// 是否还有sheet
					sheet = workbook.getSheetAt(currSheet + 1);
					currSheet++;// 当前sheet指向下一张sheet
					int row = currPosition;
					currPosition++;
					return getLine(sheet, row);
				}
				return null;
			}
			int row = currPosition;
			currPosition++;
			return getLine(sheet, row);
		}
		return null;
	}

	// 函数返回sheet的一行数据
	@SuppressWarnings("deprecation")
	private String getLine(HSSFSheet sheet, int row) {
		HSSFRow rowline = sheet.getRow(row);
		StringBuffer buffer = new StringBuffer();
		int filledColumns = rowline.getLastCellNum();// 获取当前行的列数
		HSSFCell cell = null;
		for (int i = 0; i < filledColumns; i++) {
			cell = rowline.getCell((short) i);
			String cellvalue = null;
			if (cell != null) {
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否为date
						Date date = cell.getDateCellValue();
						cellvalue = date.toLocaleString();
					} else {// 如果是纯数字s
						Integer num = new Integer(
								(int) cell.getNumericCellValue());
						cellvalue = String.valueOf(num);
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:
					cellvalue = cell.getStringCellValue().replace("'", "''");// 取得当前cell字符串
					break;
				default:
					cellvalue = "";
				}
			} else {
				cellvalue = "";
			}
			buffer.append(cellvalue).append(EXCEL_LINE_DELIMITER);
		}
		return buffer.toString();
	}

	public void close() {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				is = null;
			}
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				reader = null;
			}
		}
	}

	/**
	 * @param mapLi
	 *            放内容的空list
	 * @param keyMap
	 *            取值keys数组
	 * @param currSheet
	 *            页数
	 * @param beginRow
	 *            开始行
	 * @param beginCell
	 *            开始列
	 * @return
	 */
	public List<Map<String, Object>> readSheet(List<Map<String, Object>> mapLi,
			String[] keys, HSSFSheet sheet, int beginRow, int beginCell) {
		currPosition = beginRow;// 设置开始行
		if (currPosition > sheet.getLastRowNum()) {// 判断当前行是否到当前sheet结尾
			return mapLi;
		} else {
			mapLi.add(readLine(keys, sheet, currPosition, beginCell));
			currPosition++;
			readSheet(mapLi, keys, sheet, currPosition, beginCell);
			return mapLi;
		}
	}

	@SuppressWarnings("deprecation")
	public Map<String, Object> readLine(String[] keys, HSSFSheet sheet,
			int row, int beginCell) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("line", row);
		HSSFRow rowline = sheet.getRow(row);
		int filledColumns = rowline.getLastCellNum();// 获取当前行的列数
		HSSFCell cell = null;
		int keysNumber = 0;
		for (int i = beginCell; i < filledColumns; i++) {
			cell = rowline.getCell((short) i);
			String cellvalue = null;
			if (cell != null) {
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 判断是否为date
						Date date = cell.getDateCellValue();
						cellvalue = date.toLocaleString();
					} else {// 如果是纯数字s
						DecimalFormat df = new DecimalFormat("0");
						cellvalue = df.format(cell.getNumericCellValue());
						/*
						 * Integer num =new
						 * Integer((int)cell.getNumericCellValue());
						 */
						/* cellvalue=String.valueOf(num); */
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:
					cellvalue = cell.getStringCellValue().replace("'", "''");// 取得当前cell字符串
					break;
				default:
					cellvalue = "";
				}
			} else {
				cellvalue = "";
			}
			map.put(keys[keysNumber], cellvalue);
			keysNumber++;
		}
		return map;
	}

	/**
	 * 读取excel
	 * 
	 * @param path
	 *            文件路径
	 * @param keys
	 *            String[] keys ={"id","name","phone","eml","age","sex"};
	 * @param currSheet
	 *            页码
	 * @param beginRow
	 *            开始行
	 * @param beginCell
	 *            开始列
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static List<Map<String, Object>> readFile(String path,
			String[] keys, int currSheet, int beginRow, int beginCell)
			throws IOException, Exception {
		ExcelReader er = new ExcelReader(path);
		List<Map<String, Object>> mapLi = new ArrayList<Map<String, Object>>();
		HSSFSheet sheet = er.workbook.getSheetAt(currSheet);
		return er.readSheet(mapLi, keys, sheet, beginRow, beginCell);
	}

	/**
	 * 获取名称管理器值
	 * 
	 * @param path
	 * @param name
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static HSSFCell getHssFcell(String path, String name)
			throws IOException, Exception {
		ExcelReader er = new ExcelReader(path);
		HSSFName xssfName = er.workbook.getName(name);
		AreaReference areaReference = new AreaReference(
				xssfName.getRefersToFormula());
		CellReference cr = areaReference.getAllReferencedCells()[0];
		return er.workbook.getSheetAt(1).getRow(cr.getRow())
				.getCell(cr.getCol());
	}

	public void TestExcel(String path) {
		try {

			// 获取机构
			ReportBuilder bu = new ReportBuilder();
			bu.loadTemplate(path, 1);// 获取第1页 excel从0开始
			@SuppressWarnings("unused")
			String orgId = bu.getTemplateVal(1, 0);

			// 读取内容
			String[] keys = { "编码", "组织机构码", "单位名称", "全称", "财政机构类型" };
			@SuppressWarnings("unused")
			List<Map<String, Object>> li = this.readFile(path, keys, 0, 2, 0);

			// 改变格式
			bu.loadTemplate(path, 0);
			int line = 0;
			bu.setCellStyle(HSSFColor.BLUE.index, HSSFColor.RED.index, line, 0);// line
																				// 行数目

			// 设置值i
			bu.loadTemplate(path, 1);
			bu.writeInTemplate("121", 0, 3);

			// 设置下拉
			List<String> speLi = new ArrayList<>();
			String[] arr = (String[]) speLi.toArray(new String[speLi.size()]);
			bu.setLinePullDown(2, -1, 4, 4, arr);// 第二行开始 不限结尾 第四列 第四行 一格

			bu.SaveTemplate(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testExcelfile(String path) {
		try {
			String[] keys = { "编码", "组织机构码", "单位名称", "全称", "财政机构类型" };
			List<Map<String, Object>> li = ExcelReader.readFile(path, keys, 0,
					2, 0);
			System.out.println(li);
			ReportBuilder bu = new ReportBuilder();
			bu.loadTemplate("E:\\upload\\studentModel.xls", 1);
			System.out.println(bu.getTemplateVal(0, 3));
			/*
			 * ExcelReader er = new ExcelReader(path); HSSFName
			 * xssfName=er.workbook.getName("sex_女"); AreaReference
			 * areaReference = new AreaReference(xssfName.getRefersToFormula());
			 * CellReference cr = areaReference.getAllReferencedCells()[0];
			 * HSSFCell hssfCell =
			 * er.workbook.getSheetAt(1).getRow(cr.getRow()).
			 * getCell(cr.getCol()); hssfCell.getNumericCellValue(); double
			 * i=ExcelReader
			 * .getHssFcell("E:\\[文产院, 动漫学院][221122]学生信息系统导入列表.xls",
			 * "sex_女").getNumericCellValue(); System.out.println((int)i);
			 * ReportBuilder bu = new ReportBuilder();
			 * bu.loadTemplate("E:\\upload\\studentModel.xls",1);
			 * bu.writeInTemplate("sadsads",0,3);
			 */
			bu.SaveTemplate("E:\\upload\\studentModel.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception, IOException {

		/*
		 * String path = ""; //获取机构 ReportBuilder bu = new ReportBuilder();
		 * bu.loadTemplate(path,1);//获取第1页 excel从0开始
		 * 
		 * @SuppressWarnings("unused") String orgId = bu.getTemplateVal(0,3);
		 * 
		 * //读取内容 String[] keys ={"account","name","job","role","spe"};
		 * 
		 * @SuppressWarnings("unused") List<Map<String, Object>> li =
		 * ExcelReader.readFile(path, keys, 0, 2, 0);
		 * 
		 * //改变格式 bu.loadTemplate(path,0); int line =0;
		 * bu.setCellStyle(HSSFColor.BLUE.index,HSSFColor.RED.index, line,
		 * 0);//line 行数目
		 * 
		 * //设置值i bu.loadTemplate(path,1); bu.writeInTemplate("121",0,3);
		 * 
		 * //设置下拉 List<String> speLi = new ArrayList<>(); String[] arr =
		 * (String[])speLi.toArray(new String[speLi.size()]);
		 * bu.setLinePullDown(2,-1,4,4,arr);//第二行开始 不限结尾 第四列 第四行 一格
		 * 
		 * bu.SaveTemplate(path);
		 */

		/*
		 * try { ExcelReader er = new
		 * ExcelReader("E:\\[文产院, 动漫学院][221122]学生信息系统导入列表.xls"); String line
		 * =er.readLine(); while(line!=null){ System.out.println(line);
		 * line=er.readLine(); } er.close(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		// String[] keys ={"id","name","phone","eml","age","sex"};
		// List<Map<String, Object>> li =
		// ExcelReader.readFile("E:\\[文产院, 动漫学院][221122]学生信息系统导入列表.xls", keys,
		// 0, 2, 0);
		// System.out.println(li);
		// ReportBuilder bu = new ReportBuilder();
		// bu.loadTemplate("E:\\[文产院, 动漫学院][221122]学生信息系统导入列表.xls",1);
		// System.out.println(bu.getTemplateVal(0,3));
		// ExcelReader er = new
		// ExcelReader("E:\\[文产院, 动漫学院][221122]学生信息系统导入列表.xls");
		// HSSFName xssfName=er.workbook.getName("sex_女");
		// AreaReference areaReference = new
		// AreaReference(xssfName.getRefersToFormula());
		// CellReference cr = areaReference.getAllReferencedCells()[0];
		// HSSFCell hssfCell =
		// er.workbook.getSheetAt(1).getRow(cr.getRow()).getCell(cr.getCol());
		// hssfCell.getNumericCellValue();
		// double
		// i=ExcelReader.getHssFcell("E:\\[文产院, 动漫学院][221122]学生信息系统导入列表.xls",
		// "sex_女").getNumericCellValue();
		// System.out.println((int)i);
		/*
		 * ReportBuilder bu = new ReportBuilder();
		 * bu.loadTemplate("E:\\upload\\studentModel.xls",1);
		 * bu.writeInTemplate("sadsads",0,3);
		 * bu.SaveTemplate("E:\\upload\\studentModel.xls");
		 */
	}
}
