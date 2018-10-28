package framework;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Helper{

	Connection connection;
	LinkedList<String> alLogs;	
	protected LinkedHashMap<String, String> allProcessData;
	protected WebDriver driver;
	protected ExtentTest report;	
	protected String testName;
	protected String processName;
	
	File testDataFileLocation_Destination = new File("C:\\Users\\HARI\\eclipse-workspace\\vivriti.automationframework\\src\\utilfiles\\CapitalMarket.xlsx");
	/*File testDataFileLocation_Destination=new File(System.getProperty("java.io.tmpdir")+"\\"+System.getProperty("user.name")+"_PAMData.xlsx");
	//FileUtils.copyFile(testDataFileLocation_Source, PAM_online_testdataFile_Destination);
	 */
	public Helper(){}

	public Helper(WebDriver _driver, ExtentTest _report){
		driver=_driver;
		report=_report;	
	}

	public String data(String key) throws FrameworkException {		

		String Val=null;

		if(key.contains("Config_")) {
			Val=allProcessData.get(key);
		}else if(key.contains("onlineData_")){
			Val=allProcessData.get(key);
		}else {Val=allProcessData.get(processName+"."+key.toString());}
		if(Val==null)
		{
			report.log(LogStatus.FAIL,"Data issue- Column["+key+"] not found in the data sheet["+processName+"]");
			throw new FrameworkException("Data issue- Column["+key+"] not found in the data sheet["+processName+"]");
		}else {
			return Val;
		}
	}

	public void assertAreEqual(String msg,ExtentTest report,Object oActual,Object oExpected) throws Exception{
		if(!oExpected.toString().isEmpty()) {
			if(oExpected.equals(oActual)) {
				report.log(LogStatus.PASS,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				report.log(LogStatus.PASS,report.addScreenCapture(getscreenshot(driver,data("Config_Report_Path"))));
				System.out.println(LogStatus.PASS+": "+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
			}else {	
				report.log(LogStatus.FAIL,": "+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				System.out.println(LogStatus.FAIL+": "+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				throw new FrameworkException(msg+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");				
			}
		}
	}

	public void assertAreNotEqual(String msg,ExtentTest report,Object oActual,Object oExpected) throws Exception   {			
		if(!oExpected.toString().isEmpty()) {
			if(oExpected.equals(oActual)) {	
				report.log(LogStatus.FAIL,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				System.out.println(LogStatus.FAIL+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				throw new FrameworkException(msg+"Expected["+oExpected.toString()+"] "+"Actual["+oExpected.toString()+"]");
			}else {
				report.log(LogStatus.PASS,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				report.log(LogStatus.PASS,report.addScreenCapture(getscreenshot(driver,data("Config_Report_Path"))));
				System.out.println(LogStatus.PASS+":"+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
			}				
		}
	}

	public void verifyAreEqual(String msg,ExtentTest report,Object oActual,Object oExpected) throws Exception {		
		if(!oExpected.toString().isEmpty()) {
			if(oExpected.equals(oActual)) {
				report.log(LogStatus.PASS,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				report.log(LogStatus.PASS,report.addScreenCapture(getscreenshot(driver,data("Config_Report_Path"))));
				System.out.println(LogStatus.PASS+":"+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
			}else {
				report.log(LogStatus.FAIL,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				report.log(LogStatus.FAIL,report.addScreenCapture(getscreenshot(driver,data("Config_Report_Path"))));
				System.out.println(LogStatus.FAIL+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
			}	
		}
	}

	public void verifyAreNotEqual(String msg,ExtentTest report,Object oActual,Object oExpected) throws Exception {			
		if(!oExpected.toString().isEmpty()) {
			if(oExpected.equals(oActual)) {
				report.log(LogStatus.FAIL,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				report.log(LogStatus.FAIL,report.addScreenCapture(getscreenshot(driver,data("Config_Report_Path"))));
				System.out.println(LogStatus.FAIL+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
			}else {
				report.log(LogStatus.PASS,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				report.log(LogStatus.PASS,report.addScreenCapture(getscreenshot(driver,data("Config_Report_Path"))));
				System.out.println(LogStatus.PASS+": "+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
			}	
		}
	}

	public void assertAreContain(String msg,ExtentTest report,Object oActual,Object oExpected) throws Exception{
		if(!oExpected.toString().isEmpty()) {
			if(oActual.toString().contains(oExpected.toString())) {
				report.log(LogStatus.PASS,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				report.log(LogStatus.PASS,report.addScreenCapture(getscreenshot(driver,data("Config_Report_Path"))));
				System.out.println(LogStatus.PASS+": "+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
			}else {	
				report.log(LogStatus.FAIL,msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				System.out.println(LogStatus.FAIL+msg+": "+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");
				throw new FrameworkException(msg+"Expected["+oExpected.toString()+"] "+"Actual["+oActual.toString()+"]");				
			}
		}
	}


	public void addlogs(String log) {
		//alLogs.add(log);
		//System.out.println(log);
	}

	public LinkedList<String> getlogs() {
		return alLogs;
	}

	// =================================================
	public WebDriver GetDriver(String browser) throws Exception{
		WebDriver driver = null;

		try {  
			switch(browser) {

			case "Firefox":		

				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions fireFoxOptions = new FirefoxOptions().setProfile(new FirefoxProfile());
				//fireFoxOptions.setBinary(configData.get("FirefoxBinary"));
				driver = new FirefoxDriver(fireFoxOptions);

				break;

			case "Chrome":

				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				//chromeOptions.setCapability("chrome.binary", configData.get("ChromeBinary").toString());
				chromeOptions.addArguments("disable-infobars");
				chromeOptions.addArguments("disable-extensions");
				chromeOptions.setExperimentalOption("useAutomationExtension", false);

				driver = new ChromeDriver(chromeOptions);

				break;

			case "IE":		

				WebDriverManager.iedriver().setup();

				//System.setProperty("webdriver.ie.driver", System.getProperty("java.io.tmpdir")+"\\"+browser+Driver.IE_Driver_Version+".exe");
				InternetExplorerOptions IEoptions = new InternetExplorerOptions();
				IEoptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				//IEoptions.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
				//IEoptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS,true);
				//IEoptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
				//IEoptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);

				driver = new InternetExplorerDriver(IEoptions);		

				break;

			case "EDGE":

				WebDriverManager.edgedriver().setup();

				EdgeOptions Edgeoptions = new EdgeOptions();

				driver = new EdgeDriver(Edgeoptions);

				break;

			case "MOBILE":

				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability("openDeviceTimeout", 1);

				break;

			default:
				break;

			}
			addlogs(LogStatus.INFO+": "+ browser+" driver instnace successfully created");
		}catch(Exception e) {			
			addlogs("Error: "+ browser+" driver instnace was not created successfully ");
			throw e;
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;

	}

	// =================================================

	Recordset getRecordSet(String excelFilePath,String sqlQuerry) throws Exception {

		connection = new Fillo().getConnection(excelFilePath);
		Recordset recordset;
		try {
			recordset = connection.executeQuery(sqlQuerry);
		} catch (FilloException e) {
			throw new FrameworkException("Record not found for the query["+sqlQuerry+"]");
		}
		return recordset;
	}



	public LinkedHashMap<String,String> getTestDataDetails(String sheetName, String testData) throws Exception {
		LinkedHashMap<String,String> data=new LinkedHashMap<String,String>();
		Recordset recordset=getRecordSet(testDataFileLocation_Destination.getAbsolutePath(),"Select * from "+sheetName+" where testDataName='"+testData+"'");
		recordset.next();
		for(String filed:recordset.getFieldNames()) {
			data.put(filed, recordset.getField(filed));
		}
		recordset.close();		
		connection.close();
		return data;
	}

	
	public LinkedHashMap<String, String> configData(String configName) throws Exception{
		LinkedHashMap<String, String> configData=new LinkedHashMap<String, String>();
		Recordset recordset=getRecordSet(testDataFileLocation_Destination.getAbsolutePath(),"Select * from Config where ENV_NAME='"+configName+"'");
		recordset.next();
		for(String filed:recordset.getFieldNames()) {
			configData.put("Config_"+filed, recordset.getField(filed));
		}
		recordset.close();		
		connection.close();
		return configData;
	}

	
	public String getscreenshot(WebDriver driver,String reportPath) throws IOException, HeadlessException, AWTException  { 
		String sharePath=null;

		sharePath=System.getProperty("java.io.tmpdir");			
		
		String filepath=reportPath.replace("\\\\Sharepath" , sharePath)+"\\"+getDateAndTimeNow()+".png";

		try 
		{
			driver.getTitle();    
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(filepath));
		} 
		catch (Exception e)  
		{ 
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(filepath));
		}
		return filepath;
	}



	public String getDateAndTimeNow() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now).replace("/","_").replace(" ","_").replace(":","_");
	}

	public String getTempFolderPath() throws IOException {
		File temp = File.createTempFile("temp-file-name", ".tmp"); 
		String absolutePath = temp.getAbsolutePath();
		String tempFilePath = absolutePath.
				substring(0,absolutePath.lastIndexOf(File.separator));
		return tempFilePath;
	}
	class FrameworkException extends Exception{
		private static final long serialVersionUID = 1L;
		String str1;
		FrameworkException(String str2) {
			str1=str2;
		}
		public String toString(){ 
			return (str1) ;
		}
	}
}
