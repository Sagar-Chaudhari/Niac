package com.niac.test.com.niac.selenium;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.annotations.Test;

@Test
public class EmcDataBase {
	
	protected static Connection conn= null;
	protected static Statement stmt= null;
	protected static ResultSetMetaData rsmd= null;
	protected static ResultSet rs1= null;
	protected static ResultSet rs2= null;
	protected static String query_Total_Row="SELECT COUNT(*) AS TOTAL_ROW FROM TblDocSPBroker";
	protected static String query_ALL_RECORDS="SELECT * FROM TblDocSPBroker";
	protected static int columnNumber;
	protected static int rowNumber;
	protected static SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
	//
public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
	try {
		System.out.println("Connecting to database.."+"\n"+df.format(new Date()));
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		conn=DriverManager.getConnection("jdbc:sqlserver://192.168.0.54:1433","NIAC2000","ams!MM14");
		System.out.println("Connected to Oracal Database");
		}
		catch(SQLException ex)
		{
		System.err.println("Failed to conncect to DB/Check Propertyes file");
		ex.printStackTrace();
		}

		List<String> data =new ArrayList<String>();
		stmt = conn.createStatement();
		rs1= stmt.executeQuery(query_ALL_RECORDS);
		rsmd=rs1.getMetaData();
		columnNumber =rsmd.getColumnCount();
		while(rs1.next())
		{
			for(int i=1; i<= columnNumber; i++)
			{
			data.add(rs1.getString(i));
			}
		}
		System.out.println("\t   "+rsmd.getColumnName(1)+"  "+rsmd.getColumnName(2)+"\t\t   "+rsmd.getColumnName(3));
		System.out.println("\t  ..........................................");
		rs2 = stmt.executeQuery(query_Total_Row);
		System.out.println("Total count"+rs2);
		while(rs2.next())
		{
			rowNumber = rs2.getInt("TOTAL_ROW");
		}
		
		for(int m=0; m<=rowNumber*columnNumber;m++)
		{
			for(int i=1; i<= rowNumber; i++)
			{
			System.out.println(i+"no row : "+data.get(m)+"\t\t| "+data.get(m+1)+"\t\t|  "+data.get(m+2)+"\t|");
			++m;
			++m;
			++m;
			}
			System.out.println("\t  ...............................");
		}
		
		System.out.println("Total Column: "+columnNumber);
		System.out.println("Total Row: "+rowNumber);
        
		if(rowNumber==271742)	
		{
			System.out.println("thanks");
		}
		
		if(conn!=null)
		{
		conn.commit();
		conn.close();
		System.out.println("\n--------------------------------------------------"+ "\n"+df.format(new Date()));
		System.out.println("Disconnected from Oracleig Database");
		}
		/*//Loading the required JDBC Driver class
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
		
		//Creating a connection to the database
		Connection conn1 = DriverManager.getConnection("jdbc:sqlserver://192.168.0.54:1433","NIAC2000","ams!MM14");
		//Connection conn2 = DriverManager.getConnection("jdbc:sqlserver://192.168.0.54:1433","NIAC2000","ams!MM14");
		
		//Executing SQL query and fetching the result
		Statement st = conn1.createStatement();
		String sqlStr = "select * from TblDocSPBroker"; //
		ResultSet rs = st.executeQuery(sqlStr);
		while (rs.next()) {
		System.out.println(rs.getString("doctypeid"));*/
		
		
		}	
}

