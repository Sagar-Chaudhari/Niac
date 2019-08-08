package com.niac.test.com.niac.selenium;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadDataEMC extends ReadExcel{
	
	ReadExcel excel = new ReadExcel();
	Object[][] data;
	Object[][] ids;
	int Count;
	String SQLQueryOld = "";
	String SQLQueryNew = "";
	String id ="";
	int k=0;
	String FolderName="";
	String FilePath = "C:\\Users\\vitthal.chalkapure\\git\\Niac\\TestData\\TestData.xlsx";
	public String resultFile=null;
	public static Connection conn= null;
	public static Statement stmt= null;
	public static ResultSet rsForHeadings= null;
	public static ResultSet rs1= null;
	public static ResultSet rs2= null;
	
	//common function
	public void Niac2k(int Q1 ,int Q2,String sheetWithIDs) throws Exception {
		int i=0;
	    data = excel.getData(FilePath, "SQLQuery");
	    
	    SQLQueryOld = data[Q1][0].toString();
	    SQLQueryNew = data[Q2][0].toString();
	    //System.out.println("Query is:"+SQLQuery);
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    conn=DriverManager.getConnection( "jdbc:sqlserver://192.168.0.54:1433","NIAC2000","ams!MM14");
	    stmt = conn.createStatement();
	    int total=0;
	    k=0;	
	    Count = excel.getCount(FilePath, sheetWithIDs);
	    //System.out.println("Row Count is:"+Count);
	      XSSFWorkbook workbook = new XSSFWorkbook(); 
	      XSSFSheet spreadsheet = workbook.createSheet("ECM_Old_New_"+sheetWithIDs);
	      XSSFRow row = spreadsheet.createRow(k);
	      XSSFCell cell;
	      cell = row.createCell(0);
	      cell.setCellValue(sheetWithIDs);
	      cell = row.createCell(1);
	      cell.setCellValue("DB Name");
	 
	      ArrayList<String> dataList = new ArrayList<String>();
	         
	     			 	
	      
	    for (int j=1 ;j<=10;j++)
	    {
	    	i=2;
	    	int l=2;
	    	total=0;
	    	ids = excel.getData(FilePath, sheetWithIDs);
	    	id = ids[j][0].toString();
	    	//System.out.println("BrokerID is:"+brokerID.toString());
	    	SQLQueryOld = SQLQueryOld.replaceAll(sheetWithIDs+"=\\d+(\\.\\d+)?", sheetWithIDs+"="+id);
	    	SQLQueryNew = SQLQueryNew.replaceAll(sheetWithIDs+"=\\d+(\\.\\d+)?", sheetWithIDs+"="+id);
	    	 
	    	 if(k==0)
	    	 {
	    		 rsForHeadings= stmt.executeQuery(SQLQueryOld);
	    		 while (rsForHeadings.next()) {
					 
					  //String FolderCount = rs1.getString(1);
					  FolderName = rsForHeadings.getString(2);
					  row = spreadsheet.getRow(k);
					  cell = row.createCell(l++);
				      cell.setCellValue(FolderName);  
				      dataList.add(FolderName);
				    
	    		 }
	    		 cell = row.createCell(l++);
			     cell.setCellValue("Total Count"); 
			     dataList.add("Total Count");
			     cell = row.createCell(l);
			     cell.setCellValue("Status"); 
	    		 k++;
	    	 }
	    	 rs1= stmt.executeQuery(SQLQueryOld);
	    	
	    	 row = spreadsheet.createRow(k);
			  cell = row.createCell(0);
		      cell.setCellValue(id);
		      cell = row.createCell(1);
		      cell.setCellValue("OldDB");
	    	 for(int rc=0;rc<dataList.size();rc++) {
	    		 cell = row.createCell(rc+2);	
			      cell.setCellValue("0");
	    		 if (rs1.next()) {
	    	 				 
				  String FolderCount = rs1.getString(1);
				  String FolderName = rs1.getString(2);
				 
			         
			         if (dataList.contains(FolderName))
			         {
			         cell = row.createCell(dataList.indexOf(FolderName)+2);	
			         cell.setCellValue(FolderCount);
			         i++;
			         total=total+Integer.parseInt(FolderCount);
			         
			         }
			        
	    		 }		        	 
			        
	    	 }
			       cell = row.createCell(dataList.indexOf("Total Count")+2);
			       
			       cell.setCellValue(total);
			
				total=0;
			  i=2;
			  k++;
			  rs2= stmt.executeQuery(SQLQueryNew);
			  row = spreadsheet.createRow(k);
			  cell = row.createCell(0);
		      cell.setCellValue(id);
		      cell = row.createCell(1);
		      cell.setCellValue("NewDB");
		      for(int rc=0;rc<dataList.size();rc++) {	
		    	  cell = row.createCell(rc+2);	
			      cell.setCellValue("0");
		      }
			      while (rs2.next()) {
			      
				  String FolderCount = rs2.getString(1);
				  String FolderName = rs2.getString(2);
				   
				  
				  if (dataList.contains(FolderName))
			         {
					 cell= row.getCell(dataList.indexOf(FolderName)+2);
			         cell.setCellValue(FolderCount);
			         total=total+Integer.parseInt(FolderCount);
			          //System.out.println("New "+Count +" "+FolderName+ "\n");
			         }
				 
		    	  }
		    	  
			        
			  cell = row.createCell(dataList.indexOf("Total Count")+2);
		      cell.setCellValue(total);
		      //cell.setCellFormula("(D2:D10)");
		      spreadsheet.addMergedRegion(new CellRangeAddress(k-1, k, dataList.size()+2, dataList.size()+2));
		      
		       
			   k++;
	    }
	    Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
	    resultFile="ECMPhase2_DB_"+sheetWithIDs+"_"+currentTimestamp.getDate()+currentTimestamp.getHours()+""+currentTimestamp.getMinutes()+".xlsx";
	    FileOutputStream out = new FileOutputStream(new File("C:\\Users\\vitthal.chalkapure\\git\\Niac\\TestData\\DBSheet\\"+resultFile));
	      workbook.write(out);
	      out.close();
	      workbook.close();
	      System.out.println("File written successfully");
      
      
   }
	 
}
