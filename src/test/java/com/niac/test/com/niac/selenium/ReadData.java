package com.niac.test.com.niac.selenium;

public class ReadData  extends ReadDataEMC{

public static void main(String[] args)  throws Exception {
		
	ReadDataEMC rd = new ReadDataEMC();
	Object[][] testC;
	int testCount;
	ReadDataTestCases rdTC = new ReadDataTestCases();
	testC=rdTC.getTestCases();
	testCount= rdTC.getTestCasesCount();
	for(int i=1;i<=testCount;i++)
	{
		if(testC[i][1].toString().equalsIgnoreCase("Y")) {
		rd.Niac2k((i*2)-1,(i*2),testC[i][2].toString());
		}
	}
	//rd.Niac2k(1, 2, "BrokerID");
	//rd.Niac2k(3, 4, "ProspectID");
	}
}