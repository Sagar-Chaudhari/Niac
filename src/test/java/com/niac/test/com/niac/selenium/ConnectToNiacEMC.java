package com.niac.test.com.niac.selenium;

import static org.testng.Assert.assertEquals;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ConnectToNiacEMC {
	
	// Object of Connection from the Database
	 Connection conn = null;
	 
	 // Object of Statement. It is used to create a Statement to execute the query
	 Statement stmt = null;
	 
	 //Object of ResultSet => 'It maintains a cursor that points to the current row in the result set'
	 ResultSet resultSet = null;
	 WebDriver driver;
	 
	 @BeforeTest
	 public void SetUpConnection() throws SQLException, ClassNotFoundException {
	 
	 // Register JDBC driver (JDBC driver name and Database URL)
	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	 
	 // Open a connection
	 conn = DriverManager.getConnection("jdbc:sqlserver://192.168.0.54:1433","NIAC2000","ams!MM14");
	 
	 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver.exe");
	 ChromeOptions options = new ChromeOptions();
	 
	 // Code to disable the popup of saved password
	 Map<String, Object> prefs = new HashMap<String, Object>();
	 prefs.put("credentials_enable_service", false);
	 prefs.put("password_manager_enabled", false);
	 options.setExperimentalOption("prefs", prefs);
	 driver = new ChromeDriver(options);
	 driver.get("http://testingpool.com/data-types-in-java/");
	 }
	 
	 @Test
	 public void OperationalMethod() {
	 try {
	 // Execute a query
	 stmt = conn.createStatement();
	 resultSet = stmt.executeQuery("SELECT * FROM OT_Docs");
	 
	 // Get the all row of UI Table
	 List<WebElement> lstTr = driver.findElements(By.xpath("//div[@class='su-table']/table/tbody/tr"));
	 
	 // Index for Row
	 int rowCount = 0;
	 
	 // Count of Matched Column
	 int matchColumnCount = 0;
	 
	 // Count of Matched Row
	 int matchRowCount = 0;
	 
	 System.out.println("Row Count => " + lstTr.size());
	 
	 // Extract the data from Table
	 while (resultSet.next()) {
	 
	 
	 //(rowCount + 1) because first row is a header row , Get all the columns from a particular row
	 List<WebElement> lstTd = lstTr.get(rowCount + 1).findElements(By.tagName("td"));
	 System.out.println("Cloumn Count => " + lstTd.size());
	 
	 for (int j = 0; j < lstTd.size(); j++) {
	 String uiCell = lstTd.get(j).getText();
	 System.out.println("UI Cell Data => " + uiCell);
	 
	 /*
	 * (j + 1) in the resultSet => because index is start from 1
	 * and here loop is starting from 0
	 */
	 String dbCell = resultSet.getString(j + 1);
	 System.out.println("DB Cell Data => " + dbCell);
	 
	 // Comparison between both string
	 if (uiCell.trim().equalsIgnoreCase(dbCell.trim())) {
	 matchColumnCount++;
	 }
	 }
	 
	 if (matchColumnCount == lstTd.size()) {
	 matchRowCount++;
	 System.out.println("========ROW MATCHED==========");
	 }
	 
	 // Set 'matchColumnCount' to 0 for next row	matchColumnCount = 0;
	 rowCount++;
	 }
	 assertEquals(matchRowCount, rowCount, "UI Table is the exact copy of Database Table");
	 } catch (Exception e) {
	 System.out.println(e);
	 }
	 }
	 
	 @AfterTest
	 public void CloseTheConnection() throws SQLException {
	 
	 // Code to close each and all Object related to Database connection
	 if (resultSet != null) {
	 try {
	 resultSet.close();
	 } catch (Exception e) {
	 }
	 }
	 
	 if (stmt != null) {
	 try {
	 stmt.close();
	 } catch (Exception e) {
	 }
	 }
	 
	 if (conn != null) {
	 try {
	 conn.close();
	 } catch (Exception e) {
	 }
	 }
	 
	 driver.quit();
	 }

}
