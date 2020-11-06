package Tests;

import Common.DriverProvider.DriverProvider;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver;
    private static final String fileSeparator = File.separator;
    private static final String rootDirectory = System.getProperty("user.dir");
    private static ExtentReports extent;

    @BeforeTest
    public void extentReportSetup() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(rootDirectory + fileSeparator + "reports" + fileSeparator +
                "html-report" + fileSeparator + "execution-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        htmlReporter.config().setDocumentTitle("Test Execution Report - Bookings.com");
        htmlReporter.config().setReportName("Test Execution Report - Bookings.com");
        htmlReporter.config().setTheme(Theme.DARK);

        extent.setSystemInfo("Application Name", "Bookings.com");
        extent.setSystemInfo("Test Developer", "Randima Senanayake");
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Operating System", "Ubuntu 18.04.5 LTS - 64 Bit");
    }

    @BeforeMethod
    public void setup(){
        driver = DriverProvider.getActiveDriver();
        driver.manage().deleteAllCookies();
    }

    @AfterMethod
    public void generateReportDataAndCloseAllDrivers(ITestResult result) {
        ExtentTest test = extent.createTest(result.getName());
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                test.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
                test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable().getMessage(), ExtentColor.RED));
                try {
                    test.fail("Screenshot at the failed moment is below " +
                            test.addScreenCaptureFromPath(takeScreenshot(driver, result.getName())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ITestResult.SKIP:
                test.log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.GREY));
                break;
            case ITestResult.SUCCESS:
                test.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
                break;
        }
        DriverProvider.close();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }

    private static String takeScreenshot(WebDriver driver, String screenshotName) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = rootDirectory + fileSeparator + "screenshots" + fileSeparator +
                screenshotName + " - " + timestamp + ".png";
        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }

    @AfterClass
    public void close(){
        DriverProvider.close();
    }


}
