package Common.Listener;

import Common.DriverProvider.DriverProvider;
import Common.ExtentReporter;
import Common.UtilClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listener implements ITestListener {

    ExtentReports extentReports = ExtentReporter.getExtentReports();
    ExtentTest test;
    ThreadLocal<ExtentTest>  extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        test = extentReports.createTest(methodName);
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail(result.getThrowable());
        String methodName = result.getMethod().getMethodName();
        WebDriver driver = DriverProvider.getActiveDriver();
        String screenShotPath = UtilClass.getScreenShot(methodName, driver);
        try {
            extentTest.get().addScreenCaptureFromPath("./screenshots/"+screenShotPath, methodName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
