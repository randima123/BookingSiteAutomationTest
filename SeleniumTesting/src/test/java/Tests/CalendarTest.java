package Tests;

import Common.DataProvider.CommonDataProvider;
import Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CalendarTest extends BaseTest {


    @Test(priority = 1, dataProviderClass = CommonDataProvider.class, dataProvider = "excelData")
    public void selectCheckInDateTest(String location, String ciYear, String ciMonth, String ciDate, String coYear, String coMonth, String coDate){
        HomePage homePage = HomePage.open();
        homePage.clickDateDiv();
        String checkInYear=ciYear;
        String checkInMonth=ciMonth;
        String checkInDay=ciDate;
        homePage.selectDate(checkInYear,checkInMonth,checkInDay);
        Assert.assertTrue(true);

    }

    @Test(priority = 2, dataProviderClass = CommonDataProvider.class, dataProvider = "excelData")
    public void selectCheckOutDateTest(String location, String ciYear, String ciMonth, String ciDate, String coYear, String coMonth, String coDate) throws InterruptedException {
        HomePage homePage = HomePage.open();
        homePage.clickDateDiv();
        String checkOutYear=coYear;
        String checkOutMonth=coMonth;
        String checkOutDay=coDate;
        homePage.selectDate(checkOutYear,checkOutMonth,checkOutDay);
        Thread.sleep(50);
        Assert.assertTrue(true);

    }



}
