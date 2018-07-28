package com.qa.Demo.ExtentAndLog4j;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class YopMailIDCreation {
	public WebDriver driver;

	ExtentHtmlReporter e1;
	ExtentReports e2;
	ExtentTest e3;

	String Path = System.getProperty("user.dir") + "/Log4jreportgenrator/Log4j.properties";
	public static Logger logger = Logger.getLogger(YopMailIDCreation.class.getName());

	@BeforeTest
	public void Log4jCapturing() {
		PropertyConfigurator.configure(Path);
	}

	@BeforeSuite
	public void ExtentReportYopMail() {
		e1 = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/YopMailExtentReport.html");
		e2 = new ExtentReports();
		e2.attachReporter(e1);

		e2.setSystemInfo("Enviroment", "QA	");
		e2.setSystemInfo("Host Name", "Computer Host name : 127.0.0.1");
		e2.setSystemInfo("User Name", "Dheeraj Pratap Singh");
		e2.setSystemInfo("OS", "Window 10");

		e1.config().setDocumentTitle("Automated Script for YoPmail ID creation");
		e1.config().setReportName("Email ID creation in Yopmail site.");
		e1.config().setTestViewChartLocation(ChartLocation.TOP);
		e1.config().setTheme(Theme.DARK);
	}

	@BeforeMethod
	public void StartedCreatingReport(Method method) {
		e3 = e2.createTest(this.getClass().getSimpleName() + " : : " + method.getName() + " has been started");
	}

	@AfterSuite
	public void Flush() {
		e2.flush();
	}

	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {

			e3.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + "Test case has been failed for this module", ExtentColor.RED));
			e3.log(Status.FAIL, result.getThrowable());
			
			logger.info("Captured the screenshot for the failed test case");

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			e3.log(Status.PASS, MarkupHelper.createLabel(result.getName() + "Test case has been passed for this module", ExtentColor.GREEN));
			e3.log(Status.PASS, "Test case has been passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			e3.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + "Test case has been skipped for while now", ExtentColor.ORANGE));
			e3.log(Status.SKIP, "Test case has been skipped for now");
		}

	}

	@BeforeClass
	public void OpenBroswer() {
		logger.info("The broswer property has been set it up");
		System.setProperty("webdriver.chrome.driver", "E:\\Selnium\\BrowserPath\\chromedriver_win32\\chromedriver.exe");

		logger.info("Initiated the broswer driver");
		driver = new ChromeDriver();

		logger.info("Maximized the broswer");
		driver.manage().window().maximize();

		logger.info("Sync the broswer");
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(2000, TimeUnit.SECONDS);

		logger.info("Site has been opned after hitting the URL");
		driver.get("http://www.yopmail.com/en/");
	}

	@Test(priority = 0)
	public void EnterEmailID_In_TextBox_Module() {
		e3.log(Status.INFO, "This module has been started.");
		logger.info("Printed the Current URL in the Console");
		System.out.println(driver.getCurrentUrl());

		logger.info("Printed the Current Title in the Console");
		System.out.println(driver.getTitle());

		logger.info("Entered the email in the text field");
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("ExtentReportDEmo");
	}

	@Test(priority = 1)
	public void Verifications_Module() {

		e3.log(Status.INFO, "This module has been started.");
		SoftAssert s1_URL = new SoftAssert();

		String CurrentURL = driver.getCurrentUrl();
		String ExptextedURL = "http://www.yopmail.com/en/";

		logger.info("Printed current URL after entering the email ID in the text field");
		System.out.println("Current URL of the site is : " + CurrentURL);

		logger.info("Verification has been performed on the URL");
		System.out.println("URL verification has been passed....");
		System.out.println("===================================================");

		s1_URL.assertEquals(CurrentURL, ExptextedURL);
		s1_URL.assertAll();

		SoftAssert s2_Title = new SoftAssert();
		String CurrentTitle = driver.getTitle();
		String ExptextedTitle = "YOPmail - Disposable Email Address";

		logger.info("Printed the Current Title after entering the email ID in the text field");
		System.out.println("Current URL of the site is : " + CurrentTitle);

		logger.info("Verification has been performed on the Title");
		System.out.println("Title verification has been passed....");
		System.out.println("===================================================");

		s2_Title.assertEquals(CurrentTitle, ExptextedTitle);
		s1_URL.assertAll();

	}

	@Test(priority = 2)
	public void Click_On_CheckInbox_Module() {
		e3.log(Status.INFO, "This module has been started.");
		logger.info("Clicked on the button --> 'Checked inbox'");
		driver.findElement(By.xpath("//input[@title='Check inbox @yopmail.com']")).click();
	}

	@Test(priority = 3)
	public void get_TextOf_CreatedEmail_Module() {
		e3.log(Status.INFO, "This module has been started.");
		logger.info("Got the complete email ID after the clicking on the button");
		String CurrentText_Created = driver.findElement(By.xpath("//div[contains(@class,'bname b al_l')]")).getText();
		System.out.println("==============================================");

		logger.info("Printed the Email Address which is just now created");
		System.out.println(CurrentText_Created);
		System.out.println("==============================================");
	}

	@Test(priority = 4)
	public void ResetPassword_Module() {
		e3.log(Status.INFO, "This module has been started.");
		logger.info("Deliberately failing this ResetPasswordLink flow");
		Assert.assertEquals(false, true);
	}

	@Test(priority = 5)
	public void UpdatedPassword_Module() {
		e3.log(Status.INFO, "This module has been started.");
		logger.info("Deliberately failing this UpdatedPasswordLink flow");
		Assert.assertEquals(false, true);
	}

	@Test(priority = 6)
	public void Login_Module() {
		e3.log(Status.INFO, "This module has been started.");
		logger.info("Deliberately passing this Login flow");
		Assert.assertEquals(true, true);
	}

	@Test(priority = 7)
	public void Register_Module() {
		e3.log(Status.INFO, "This module has been started.");
		logger.info("Deliberately failing this Register flow");
		Assert.assertEquals(false, true);
	}

	@Test(priority = 8)
	public void Check_for_new_mails_Module() throws InterruptedException {

		e3.log(Status.INFO, "This module has been started.");
		logger.info("Stored all the iframe in the list");
		List<WebElement> element = driver.findElements(By.xpath("//iframe[@class='whc']"));

		System.out.println("Total number of frame are present in the site are as below");

		logger.info("Got the total number of iframe present in the current window site");
		int getSize = element.size();

		logger.info("Printed total number of iframes");
		System.out.println(getSize);

		for (int i = 0; i < getSize; i++) {
			String getTageName = element.get(i).getTagName();

			logger.info("Printed the TagName of each iframe");
			System.out.println(getTageName);
		}

		logger.info("Switiching to the iframe");
		driver.switchTo().frame("ifinbox");
		System.out.println("I am in ifinbox frame");

		logger.info("Window got sleeped for 3 seconds");
		Thread.sleep(3000);

		logger.info("Clicked on the email ID which you have just created");
		driver.findElement(By.xpath("//div[@class='pdet nb']//a")).click();
	}

	@Test(priority = 9, enabled = false)
	public void Skipped_Module() {

		e3.log(Status.INFO, "This module has been skipped now...");
		System.out.println("Skipping this method...");
	}

	@AfterTest
	public void CloseBroswer() {
		logger.info("Broswer got closed...");
		driver.close();
	}

}
