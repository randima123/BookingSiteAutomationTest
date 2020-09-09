package Common.DataProvider;

import com.sun.media.sound.InvalidFormatException;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class CommonDataProvider {

    @DataProvider(name="excelData")
    public Object[][] readExcel() throws InvalidFormatException, IOException {
        return ExcelReader.readExcel(System.getProperty("user.dir") + "//src//test//resources//excelSheets//test.xlsx");
    }

}
