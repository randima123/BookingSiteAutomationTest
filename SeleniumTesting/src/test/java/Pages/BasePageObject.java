package Pages;

import Common.DriverProvider.DriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.*;

import java.util.List;

public class BasePageObject {


    private static final int SHORT_IMPLICIT_TIMEOUT = 10;
    protected WebDriverWait wait;
    protected WebDriver driver = DriverProvider.getActiveDriver();
    final static Logger logger = Logger.getLogger(BasePageObject.class);

    public BasePageObject(){
        wait = new WebDriverWait(driver,SHORT_IMPLICIT_TIMEOUT);
    }

    protected boolean waitForVisibility(WebElement webElement){
        try {
            wait.until(ExpectedConditions.visibilityOf(webElement));
            return true;
        }catch (TimeoutException ex){
            logger.error("Search results are not visible");
            ex.printStackTrace();
            return false;
        }

    }
    protected boolean waitForVisibility(List<WebElement> webElementList){
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(webElementList));
            return true;
        }catch (TimeoutException ex){
            logger.error("Search results are not visible");
            ex.printStackTrace();
            return false;
        }

    }

}
