package Tests;

import Common.DriverProvider.DriverProvider;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setup(){
        driver = DriverProvider.getActiveDriver();
        driver.manage().deleteAllCookies();
    }

    @BeforeMethod
    public void beforeMethod(){
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public void close(){
        DriverProvider.close();
    }


}
