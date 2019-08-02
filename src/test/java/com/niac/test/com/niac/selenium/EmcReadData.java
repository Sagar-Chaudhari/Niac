package com.niac.test.com.niac.selenium;

import java.lang.reflect.Array;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class EmcReadData {

	private static WebDriver driver;
	
	public void AppLogin() {
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://testingpool.com/data-types-in-java/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		/*int rowCount = driver.findElements(By.xpath("")).size();
		int columnCount = driver.findElements(By.xpath("")).size();
		
		System.out.println("Number of rows in table :="+rowCount);
		System.out.println("Number of columns in table :="+columnCount);*/
		
	}
	public void TableData() {
		
	 //count rows
	 List<WebElement> Rows = driver.findElements(By.xpath("//div[@class='su-table']/table/tbody/tr"));
	 System.out.println("Size of the link: "+Rows.size());
	 /*int totalRows = Rows.size();
	 System.out.println(" Total rows : "+totalRows);*/
	 ArrayList<String> linkText = new ArrayList<String>();
	 for(WebElement ele : Rows) {
		 
		 try {
			 linkText.add(ele.getText());
		 }catch(Exception e) {
			 
		 }
		 System.out.println("********************************");
		 
		 System.out.println("Size of ArrayList*"+linkText.size());
		 int i=1;
		 for(String s : linkText) {
			 System.out.println("ArrayList Text: "+s+"|");
			 i++;
		 }
	   }
}
	 public static void main(String[] argv) {
		 
		 EmcReadData obj= new EmcReadData();
		 obj.AppLogin();
		 obj.TableData();
	 }
}
	 /*//count columns
	 List<WebElement> Columns = driver.findElements(By.xpath("//div[@class='su-table']/table/tbody/tr[1]/td"));
	 int totalColumns = Columns.size();
	 System.out.println(" Total Columns : "+totalColumns);
	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("http://testingpool.com/data-types-in-java/");
		
		//count rows
		List Rows = driver.findElements(By.xpath("//div[@class='su-table']/table/tbody/tr"));
		int totalRows = Rows.size();
		System.out.println(" Total rows : "+totalRows);

		//count columns
		List Columns = driver.findElements(By.xpath("//div[@class='su-table']/table/tbody/tr[1]/td"));
		int totalColumns = Columns.size();
		System.out.println(" Total Columns : "+totalColumns);

		HashMap map1 = new HashMap();

		//Extract data
		for(int i=1;i<=totalRows;i++){ 
		//Fetch all column's values to different variables and perform desired operations 
			WebElement strdatatype = driver.findElement(By.xpath("//div[@class='su-table']/table/tbody/tr["+i+"]/td[1]")); 
			WebElement strDesc = driver.findElement(By.xpath("//div[@class='su-table']/table/tbody/tr["+i+"]/td[2]")); 
			WebElement strExample = driver.findElement(By.xpath("//div[@class='su-table']/table/tbody/tr["+i+"]/td[3]")); 
			WebElement strDefaultVal = driver.findElement(By.xpath("//div[@class='su-table']/table/tbody/tr["+i+"]/td[4]")); 
			//First Column - Key 
			String myKey = strdatatype.getText(); 
			// Value - Combination of remaining 3 columns 
			String itsValue = strDesc.getText() +"," + strExample.getText() + "," + strDefaultVal.getText(); map1.put(myKey, itsValue);
			} 
		for (Map.Entry entry:map1..entrySet())
		{
		System.out.println("***The Key is - " + entry.getKey()+"***");
		System.out.println("The value is - " + entry.getValue());

	}

}}
*/