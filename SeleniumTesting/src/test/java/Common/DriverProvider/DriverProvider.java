package Common.DriverProvider;

import Common.Browser;
import Common.Configuration;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class DriverProvider {

    private static final List<WebDriver> drivers = new ArrayList<>();
    private static int ACTIVE_DRIVER_INDEX= 0;

    private static WebDriver getWebDriver(int index){
        if(drivers.size()<=ACTIVE_DRIVER_INDEX){
            getNewDriverInstance();
        }
        return drivers.get(ACTIVE_DRIVER_INDEX);
    }

    public static WebDriver getActiveDriver(){
        return getWebDriver(ACTIVE_DRIVER_INDEX);
    }

    private static WebDriver getNewDriverInstance(){
        drivers.add(Browser.lookupBrowser(Configuration.getBrowser()).getInstance());
        if(drivers != null){
            return drivers.get(ACTIVE_DRIVER_INDEX);
        }else {
            //ERROR
            return null;
        }
    }

    public static void close(){
        for(WebDriver driver: drivers){
            if(driver!=null){
                driver.quit();
            }
        }
        drivers.clear();
        ACTIVE_DRIVER_INDEX=0;
    }

}
