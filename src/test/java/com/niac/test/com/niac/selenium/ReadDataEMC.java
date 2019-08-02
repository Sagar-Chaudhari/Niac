package com.niac.test.com.niac.selenium;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadDataEMC extends ReadExcel{
	
	ReadExcel excel = new ReadExcel();
	Object[][] data;
	Object[][] id;
	int Count;
	String SQLQuery1 = "";
	String SQLQuery2 = "";
	String brokerID ="";
	int k=0;
	String FilePath = "C:\\Users\\sagar.chaudhari\\eclipse-workspace\\com.niac.selenium\\TestData\\TestData.xlsx";
	public static Connection conn= null;
	public static Statement stmt= null;
	public static ResultSet rs1= null;
	public static ResultSet rs2= null;
	
	public void Niac2k() throws Exception {
			int i=0;
		    data = excel.getData(FilePath, "Sheet1");
		    SQLQuery1 = data[1][0].toString();
		    SQLQuery2 = data[2][0].toString();
		    //System.out.println("Query is:"+SQLQuery);
		    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		    conn=DriverManager.getConnection( "jdbc:sqlserver://192.168.0.54:1433","NIAC2000","ams!MM14");
		    stmt = conn.createStatement();
		    int total=0;
		    
		    Count = excel.getCount(FilePath, "Sheet2");
		    //System.out.println("Row Count is:"+Count);
		      XSSFWorkbook workbook = new XSSFWorkbook(); 
		      XSSFSheet spreadsheet = workbook.createSheet("ECM_Old_New_Broker_db");
		      XSSFRow row = spreadsheet.createRow(0);
		      XSSFCell cell;
		      cell = row.createCell(0);
		      cell.setCellValue("Broker ID ");
		      cell = row.createCell(1);
		      cell.setCellValue("DB Name");
		      cell = row.createCell(2);
		      cell.setCellValue("Appointment");
		      cell = row.createCell(3);
		      cell.setCellValue("Commission Sent");
		      cell = row.createCell(4);
		      cell.setCellValue("Commn Invoices");
		      cell = row.createCell(5);
		      cell.setCellValue("Correspondence");
		      cell = row.createCell(6);
		      cell.setCellValue("Invoices");
		      cell = row.createCell(7);
		      cell.setCellValue("Payments Sent");
		      cell = row.createCell(8);
		      cell.setCellValue("Renewal Apps");
		      cell = row.createCell(9);
		      cell.setCellValue("Reports");
		      cell = row.createCell(10);
		      cell.setCellValue("Root Folder");
		      cell = row.createCell(11);
		      cell.setCellValue("Statements");
		      cell = row.createCell(12);
		      cell.setCellValue("W-9 Forms");
		      cell = row.createCell(13);
		      cell.setCellValue("Total Count");
		      cell = row.createCell(14);
		      cell.setCellValue("Status");
		      ArrayList<String> data = new ArrayList<String>();
		      for (int l=0;l<12;l++)
		      {
		    	  data.add(spreadsheet.getRow(0).getCell(l+2).getStringCellValue());
		    	 // System.out.print(data);
		      }
		         
		      k=1;				 	
		      
		    for (int j=1 ;j<=Count;j++)
		    {
		    	i=2;
		    	total=0;
		    	id = excel.getData(FilePath, "Sheet2");
		    	brokerID = id[j][0].toString();
		    	//System.out.println("BrokerID is:"+brokerID.toString());
		    	SQLQuery1 = SQLQuery1.replaceAll("BrokerID=\\d+(\\.\\d+)?", "BrokerID="+brokerID);
		    	SQLQuery2 = SQLQuery2.replaceAll("BrokerID=\\d+(\\.\\d+)?", "BrokerID="+brokerID);
		    	  rs1= stmt.executeQuery(SQLQuery1);
		    	 
		    	  row = spreadsheet.createRow(k);
				  cell = row.createCell(0);
			      cell.setCellValue(brokerID);
			      cell = row.createCell(1);
			      cell.setCellValue("OldDB");
				  while (rs1.next()) {
					 
					  String FolderCount = rs1.getString(1);
					  String FolderName = rs1.getString(2);
					 
				         
				         if (data.contains(FolderName))
				         {
				         cell = row.createCell(data.indexOf(FolderName)+2);	
				         cell.setCellValue(FolderCount);
				         i++;
				         total=total+Integer.parseInt(FolderCount);
				         //System.out.println("Old "+Count +" "+FolderName+ "\n");
				         }
				      			        	 
				         }
				       cell = row.createCell(data.indexOf("Total")+2);
				       
				       cell.setCellValue(total);
				
					total=0;
				  i=2;
				  k++;
				  rs2= stmt.executeQuery(SQLQuery2);
				  row = spreadsheet.createRow(k);
				  cell = row.createCell(0);
			      cell.setCellValue(brokerID);
			      cell = row.createCell(1);
			      cell.setCellValue("NewDB");
				  while (rs2.next()) {
					 
					  String FolderCount = rs2.getString(1);
					  String FolderName = rs2.getString(2);
					   
				         
					  if (data.contains(FolderName))
				         {
				         cell = row.createCell(data.indexOf(FolderName)+2);	
				         cell.setCellValue(FolderCount);
				         total=total+Integer.parseInt(FolderCount);
				          //System.out.println("New "+Count +" "+FolderName+ "\n");
				         }
				       				        	
				        }
				  cell = row.createCell(data.indexOf("Total")+2);
			      cell.setCellValue(total);
			      //cell.setCellFormula("(D2:D10)");
			      spreadsheet.addMergedRegion(new CellRangeAddress(k-1, k, 14, 14));
			      
			       
				   k++;
		    }
		   		    
		    FileOutputStream out = new FileOutputStream(new File("C:\\Users\\sagar.chaudhari\\eclipse-workspace\\com.niac.selenium\\TestData\\DBSheet\\ECMPhase2_BrokerDB.xlsx"));
		      workbook.write(out);
		      out.close();
		      workbook.close();
		      System.out.println("ECMPhase2_BrokerDB.xlsx written successfully");
	      
	      /*XSSFWorkbook workbook = new XSSFWorkbook(); 
	      XSSFSheet spreadsheet = workbook.createSheet("employee_db");
	      XSSFRow row = spreadsheet.createRow(1);
	      XSSFCell cell;
	      cell = row.createCell(1);
	      cell.setCellValue("EMP ID");
	      cell = row.createCell(2);
	      cell.setCellValue("EMP NAME");
	      cell = row.createCell(3);
	      cell.setCellValue("DEG");
	      cell = row.createCell(4);
	      cell.setCellValue("SALARY");
	      cell = row.createCell(5);
	      cell.setCellValue("DEPT");
	      int i = 2;

	      while(resultSet.next()) {
	         row = spreadsheet.createRow(i);
	         cell = row.createCell(1);
	         cell.setCellValue(resultSet.getInt("eid"));
	         cell = row.createCell(2);
	         cell.setCellValue(resultSet.getString("ename"));
	         cell = row.createCell(3);
	         cell.setCellValue(resultSet.getString("deg"));
	         cell = row.createCell(4);
	         cell.setCellValue(resultSet.getString("salary"));
	         cell = row.createCell(5);
	         cell.setCellValue(resultSet.getString("dept"));
	         i++;
	      }

	      FileOutputStream out = new FileOutputStream(new File("exceldatabase.xlsx"));
	      workbook.write(out);
	      out.close();
	      workbook.close();
	      System.out.println("exceldatabase.xlsx written successfully");*/
	      
	   }
	 
}
