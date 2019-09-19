package com.niac.test.com.niac.selenium;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.hssf.record.CFRuleBase.ComparisonOperator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFColorScaleFormatting;
import org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule;
import org.apache.poi.xssf.usermodel.XSSFFontFormatting;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheetConditionalFormatting;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadDataEMC extends ReadExcel{
	
	ReadExcel excel = new ReadExcel();
	Object[][] data;
	Object[][] ids;
	int Count;
	String SQLQueryOld = "";
	String SQLQueryNew = "";
	String Sid ="";
	String id ="";
	int k=0;
	int zero=0;
	String FolderName="";
	String FilePath = "C:\\Users\\vitthal.chalkapure\\git\\Niac\\TestData\\TestData.xlsx";
	public String resultFile=null;
	public static Connection conn= null;
	public static Statement stmt= null;
	public static ResultSet rsForHeadings= null;
	public static ResultSet rs1= null;
	public static ResultSet rs2= null;
	
	//common function
	public void Niac2k(int Q1 ,int Q2,String sheetWithIDs, String maxFolder) throws Exception {
		data = excel.getData(FilePath, "SQLQuery");
		String SQLToReplace1="";
		String SQLToReplace2="";
		String quote ="";
		SQLQueryOld = data[Q1][0].toString();
	    SQLQueryNew = data[Q2][0].toString();
	    int max=Integer.parseInt(maxFolder.substring(0, maxFolder.length()-2));
	    SQLToReplace1=data[Q1][1].toString();
	    SQLToReplace2=data[Q2][1].toString();
	    
	    //System.out.println("Query is:"+SQLQuery);
	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    //conn=DriverManager.getConnection( "jdbc:sqlserver://192.168.0.54:1433","NIAC2000","ams!MM14");
	    conn=DriverManager.getConnection("jdbc:sqlserver://192.168.0.54:1433;user=NIAC2000;password=ams!MM14;database=NIACTest");
	    stmt = conn.createStatement();
	    
	    int total=0;
	    k=0;	
	    Count = excel.getCount(FilePath, sheetWithIDs);
	    //System.out.println("Row Count is:"+Count);
	      XSSFWorkbook workbook = new XSSFWorkbook(); 
	      XSSFSheet spreadsheet = workbook.createSheet("ECMPhase2_DB_"+sheetWithIDs);
	      XSSFRow row = spreadsheet.createRow(k);
	    
	      XSSFCell cell;
	      cell = row.createCell(0);
	      cell.setCellValue(sheetWithIDs);
	      cell = row.createCell(1);
	      cell.setCellValue("DB Name");
	 
	      ArrayList<String> dataList = new ArrayList<String>();
	         
	   try {     
	    for (int j=1;j<=4000;j++)
	    {
	    	System.out.println(j);
	    	int l=2;
	    	total=0;
	    	ids = excel.getData(FilePath, sheetWithIDs);
	    	
	    	id = ids[j][0].toString();
	    	
	    	//ID = Integer.parseInt(Sid.substring(0, Sid.length()-2));
	    	//System.out.println("BrokerID is:"+brokerID.toString());
	    	String SQLQueryOldTest = SQLQueryOld.replaceAll(SQLToReplace1+"=\\D?\\d+(-)\\d+(\\.\\d+)?\\D", SQLToReplace1+"="+id);
	    	String SQLQueryNewTest = SQLQueryNew.replaceAll(SQLToReplace2+"=\\D?\\d+(-)\\d+(\\.\\d+)?\\D", SQLToReplace2+"="+id);
	    	 
	    	 if(k==0)
	    	 {
	    		 rsForHeadings= stmt.executeQuery(SQLQueryOldTest);
	    		 while (rsForHeadings.next()) {
					 
					  //String FolderCount = rs1.getString(1);
					  FolderName = rsForHeadings.getString(2);
					  row = spreadsheet.getRow(k);
					  cell = row.createCell(l++);
				      cell.setCellValue(FolderName);  
				      dataList.add(FolderName);
				    
	    		 }
	    		 cell = row.createCell(max+2);
			     cell.setCellValue("Total Count"); 
			     cell = row.createCell(l);
			     /*cell.setCellValue("Status"); 
			     dataList.add("Status");*/
	    		 k++;
	    	 }
	    	 rs1= stmt.executeQuery(SQLQueryOldTest);
	    	
	    	 row = spreadsheet.createRow(k);
			  cell = row.createCell(0);
		      cell.setCellValue(id);
		      cell = row.createCell(1);
		      cell.setCellValue("OldDB");
	    	 for(int rc=0;rc<dataList.size();rc++) {
	    		 cell = row.createCell(rc+2);	
			      cell.setCellValue(zero);	 
	    	 } 
	    		 while (rs1.next()) {
	    	 				 
				  String FolderCount = rs1.getString(1);
				  String FolderName = rs1.getString(2);
				 
			         
			         if (dataList.contains(FolderName))
			         {
			         cell = row.createCell(dataList.indexOf(FolderName)+2);	
			         cell.setCellValue(Integer.parseInt(FolderCount));
			         total=total+Integer.parseInt(FolderCount);
			         
			         }
			         else {
			        	 
			        	 cell=spreadsheet.getRow(0).createCell(dataList.size()+2);
			        	 cell.setCellValue(FolderName);
			        	 dataList.add(FolderName);
			        	 //System.out.println(row.getRowNum());
			        	 row = spreadsheet.getRow(k);
			        	 cell = row.createCell(dataList.indexOf(FolderName)+2);	
				         cell.setCellValue(Integer.parseInt(FolderCount));
			        	 total=total+Integer.parseInt(FolderCount);
			         }
			        
	    		 }		        	 
			        
	    	 
			       cell = row.createCell(max+2);
			       
			       cell.setCellValue(total);
			
				total=0;
			  k++;
			  rs2= stmt.executeQuery(SQLQueryNewTest);
			  row = spreadsheet.createRow(k);
			  cell = row.createCell(0);
		      cell.setCellValue(id);
		      cell = row.createCell(1);
		      cell.setCellValue("NewDB");
		      for(int rc=0;rc<dataList.size();rc++) {	
		    	  cell = row.createCell(rc+2);	
			      cell.setCellValue(zero);
		      }
			      while (rs2.next()) {
			      
				  String FolderCount = rs2.getString(1);
				  String FolderName = rs2.getString(2);
				   
				  
				  if (dataList.contains(FolderName))
			         {
					 cell= row.getCell(dataList.indexOf(FolderName)+2);
			         cell.setCellValue(Integer.parseInt(FolderCount));
			         total=total+Integer.parseInt(FolderCount);
			          //System.out.println("New "+Count +" "+FolderName+ "\n");
			         }
				 
		    	  }
		    	  
			        
			  cell = row.createCell(max+2);
		      cell.setCellValue(total);
		      //cell.setCellFormula("(D2:D10)");
		      
		      
		       
			   k++;
	    }
	    cell=spreadsheet.getRow(0).createCell(max+3);
   	    cell.setCellValue("Status");
	    for(int m=1;m<k;m++) {    	 
	    	spreadsheet.addMergedRegion(new CellRangeAddress(m, m+1, max+1, max+1));
	    	m++;
	    }
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
	   finally {
		   
	   
	    Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());      
	    resultFile="ECMPhase2_DB_"+sheetWithIDs+"_"+currentTimestamp.getDate()+"_"+currentTimestamp.getHours()+"_"+currentTimestamp.getMinutes()+".xlsx";
	    FileOutputStream out = new FileOutputStream(new File("C:\\Users\\vitthal.chalkapure\\git\\Niac\\TestData\\DBSheet\\"+resultFile));
	      workbook.write(out);
	      out.close();
	      workbook.close();
	      System.out.println("File written successfully");
	   }
      
      
   }

	
}
