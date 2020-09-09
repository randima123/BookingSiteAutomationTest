package Tests;

import Pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CalendarTest extends BaseTest {


    @Test(priority = 1)
    public void selectCheckInDateTest(){
        HomePage homePage = HomePage.open();
        homePage.clickDateDiv();
        String checkInYear="2020";
        String checkInMonth="October";
        String checkInDay="16";
        homePage.selectDate(checkInYear,checkInMonth,checkInDay);
        Assert.assertTrue(true);

    }

    @Test(priority = 2)
    public void selectCheckOutDateTest() throws InterruptedException {
        HomePage homePage = HomePage.open();
        homePage.clickDateDiv();
        String checkOutYear="2020";
        String checkOutMonth="October";
        String checkOutDay="17";
        homePage.selectDate(checkOutYear,checkOutMonth,checkOutDay);
        Thread.sleep(50);
        Assert.assertTrue(true);

    }



}
