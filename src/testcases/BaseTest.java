package testcases;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import framework.Helper;

public class BaseTest {
	
	ExtentReports extent = null;
	ExtentTest testReport = null;
	WebDriver driver = null;
	Helper helper = new Helper();	
	
	@BeforeSuite
	public void _beforeSuite() throws Exception
	{
		
		System.out.println(helper.configData("QA"));
		System.out.println(helper.getTestDataDetails("NCD_TestData","testData2"));System.out.println(System.getProperty("user.dir"));
		extent = new ExtentReports (System.getProperty("user.dir")+"\\extentReports\\ExtentReport.html", true);
		extent.addSystemInfo("Environment","Chrome");
		extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
		
	}
	
	@BeforeMethod
	public void beforeMethoed() throws Exception
	{
		testReport = extent.startTest("TESt");		
		driver = helper.GetDriver("Chrome");
		driver.get("http://mp-qa.vivriti.in");
		testReport.log(LogStatus.INFO, "Test");
	}
	
	@AfterTest
	public void afterMethoed()
	{
		driver.quit();
	}

	
	@AfterSuite
	public void _afterSuite()
	{
		extent.endTest(testReport);	
		extent.flush();
		extent.close();	
		
	}
}
