package com.niac.test.com.niac.selenium;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;

/**
 * 
 * @param xlsFilePath
 * @param sheetName
 * @param cell
 * @param columnTitle
 * @return Object
 */
public class ReadExcel {

	private Logger logger = Logger.getLogger(ReadExcel.class);
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFCell cell = null;
	//static String path = "C:\\Users\\sagar.chaudhari\\eclipse-workspace\\com.niac.selenium\\TestData\\TestData.xlsx";
	//static String path = Constants.inputDataFile;

	public Object[][] getData(String xlsFilePath, String sheetName) throws Exception {
		logger.info("Reading xlsx file: " + xlsFilePath + " for factory method.");
		Object[][] tabArray = null;
		int ci = 0, cj = 0, noOfRow = 0, noOfCols = 0;
		cell = null;
		FileInputStream file = new FileInputStream(new File(xlsFilePath));
		workbook = new XSSFWorkbook(file);
		sheet = workbook.getSheet(sheetName);
		noOfRow = sheet.getLastRowNum();
		noOfCols = workbook.getSheet(sheetName).getRow(0).getLastCellNum();
		tabArray = new Object[noOfRow+1][noOfCols];
		int celcounter = 0;
		cj = 0;
		int totCol = sheet.getRow(0).getLastCellNum();
		for (ci = 0; ci < noOfRow+1; ci++) {
			cj = celcounter;
			for (int c = 0; c < totCol; c++) {
				cell = sheet.getRow(ci).getCell(c, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				tabArray[ci][cj] = cell;
				cj++;
			}
		}
		file.close();
		return (tabArray);
	}
//----------------------------------------
	public int getCount(String xlsFilePath, String sheetName) throws Exception
	{
		logger.info("Reading xlsx file: " + xlsFilePath + " for factory method.");
		Object[][] tabArray = null;
		int ci = 0, cj = 0, noOfRow = 0, noOfCols = 0;
		cell = null;
		FileInputStream file = new FileInputStream(new File(xlsFilePath));
		workbook = new XSSFWorkbook(file);
		sheet = workbook.getSheet(sheetName);
		noOfRow = sheet.getLastRowNum();
		noOfCols = workbook.getSheet(sheetName).getRow(0).getLastCellNum();
		return noOfRow;
	}

}