package Pages;

import Common.DriverProvider.DriverProvider;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

public class BasePageObject {


    private static final int SHORT_IMPLICIT_TIMEOUT = 10;
    protected WebDriverWait wait;
    protected WebDriver driver = DriverProvider.getActiveDriver();
    final static Logger logger = Logger.getLogger(BasePageObject.class);

    public BasePageObject(){
        wait = new WebDriverWait(driver,SHORT_IMPLICIT_TIMEOUT);
    }

    protected boolean waitForVisibility(WebElement webElement){

            wait.until(visibilityOf(webElement));
            return true;

    }
    protected boolean waitForVisibility(List<WebElement> webElementList){
            wait.until(visibilityOfAllElements(webElementList));
            return true;

    }

}
