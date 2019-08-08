package com.niac.test.com.niac.selenium;

public class ReadDataTestCases extends ReadExcel {
	Object[][] dataTC;
	int countTC;
	ReadExcel excel1 = new ReadExcel();
	String FilePath = "C:\\Users\\vitthal.chalkapure\\git\\Niac\\Configurations\\TestCases.xlsx";
	public Object[][] getTestCases() throws Exception {
		dataTC = excel1.getData(FilePath, "TestCases");
		
		return dataTC;
		
		
	}
	public int getTestCasesCount() throws Exception {
		countTC = excel1.getCount(FilePath, "TestCases");
		return countTC;
	}

}
