package com.qa.Demo.ExtentAndLog4j;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ManchesterUnitedKingdom_LoginDemo {

	WebDriver driver;
	String Log4j_path = System.getProperty("user.dir") + "/Log4jFolder/Log4j.properties";
	public static Logger logger = Logger.getLogger(ManchesterUnitedKingdom_LoginDemo.class);

	ExtentHtmlReporter e1;
	ExtentReports e2;
	ExtentTest e3;

	String ExtentReport_path = System.getProperty("user.dir") + "/test-output/Manchester_Login.html";

	@BeforeClass
	public void OpenBroswer() {

		logger.info("The broswer property has been set it up");
		System.setProperty("webdriver.gecko.driver", "D:\\SeleniumJars\\geckodriver-v0.20.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().window().maximize();

		logger.info("Sync the broswer");
		driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(2000, TimeUnit.SECONDS);

		logger.info("Broswer got opened");
		driver.get("https://www.manutd.com/");
	}

	@BeforeTest
	public void SettingLog4j() {
		PropertyConfigurator.configure(Log4j_path);
	}

	@BeforeSuite
	public void SettingUpOfExtentReport() {
		e1 = new ExtentHtmlReporter(ExtentReport_path);
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

	@AfterSuite
	public void FlusingReport() {

		e2.flush();
	}

	@BeforeMethod
	public void MethodName(Method name) {
		e3 = e2.createTest(this.getClass().getSimpleName() + " : : " + name.getName(), " has been started...");
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			e3.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
			e3.log(Status.FAIL, result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			e3.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
			e3.log(Status.PASS, result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			e3.log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.ORANGE));
			e3.log(Status.SKIP, result.getThrowable());
		}
	}

	@Test(priority = 0)
	public void Login() throws InterruptedException {
		e3.log(Status.INFO, "This module is started");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//li[@id='gigyalogin']//a[@data-track-text='Log In']")).click();
		driver.findElement(By.xpath("//input[@name='username' and @placeholder='Email address *']"))
				.sendKeys("testloginmanchester@yopmail.com");
		driver.findElement(By.xpath("//input[@type='password' and @placeholder='Password *']")).sendKeys("Dheeraj@12");
		driver.findElement(By.xpath("//input[@value='LOG IN']")).click();
	}

	@Test(priority = 0)
	public void SendMoney() {
		e3.log(Status.INFO, "This module is started");
		SoftAssert s1 = new SoftAssert();
		Assert.assertEquals(false, true);
		s1.assertAll();
	}

	@Test(priority = 1)
	public void EditProfile() {
		e3.log(Status.INFO, "This module is started");
		Assert.assertEquals(false, true);
	}

	@Test(priority = 2)
	public void Help() {
		e3.log(Status.INFO, "This module is started");
		Assert.assertEquals(false, true);

	}

	@AfterTest
	public void CloseBroswer() throws InterruptedException {
		Thread.sleep(4000);
		driver.close();
	}
}
