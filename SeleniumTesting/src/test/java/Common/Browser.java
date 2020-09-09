package Common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum Browser {

    CHROME(ChromeDriver.class,"CHROME"),
    FF(FirefoxDriver.class, "FF")
    ;

    private Class<? extends RemoteWebDriver> driverClass;
    private String driverName;

    Browser(Class<? extends RemoteWebDriver> driverClass, String driverName) {
        this.driverClass = driverClass;
        this.driverName = driverName;
    }

    public static Browser lookupBrowser(String name){
        for (Browser browser: Browser.values()){
            if(browser.driverName.equals(name)){
                return browser;
            }
        }
        return null;
    }

    public WebDriver getInstance(){
        try {
            return this.getDriverClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Class<? extends RemoteWebDriver> getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(Class<? extends RemoteWebDriver> driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
