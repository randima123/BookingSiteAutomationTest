package Tests;

import Common.DriverProvider.DriverProvider;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

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
