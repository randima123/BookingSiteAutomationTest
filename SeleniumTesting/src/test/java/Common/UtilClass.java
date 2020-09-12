package Common;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilClass {

    public static String getScreenShot(String methodName, WebDriver driver){

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String screenshotName = methodName+"-"+timeStamp+".png";
        String screenShotPath = "./reports/screenshots/"+screenshotName;
        try {
            FileUtils.copyFile(scrFile, new File(screenShotPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotName;
    }



}
