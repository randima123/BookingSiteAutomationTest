package Tests;

import Common.DataProvider.CommonDataProvider;
import Pages.HomePage;
import Pages.SearchResultPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Test
public class SearchTest extends BaseTest {

    @Test(dataProviderClass = CommonDataProvider.class, dataProvider = "excelData")
    public void searchDropDownLocationTest(String location){
        HomePage homePage = HomePage.open();
        homePage.clearSearchDropdown();
        homePage.enterLettersForDropDown(location);
        boolean isVisible =homePage.checkVisibilityOfResults();
        Assert.assertTrue(isVisible,"Search elements not visible");
        List<String> searchResultList = homePage.getAllSearchResults();
        for(String element : searchResultList){
            if(element.contains(location)){
                Assert.assertTrue(true);
            }else{
                Assert.assertTrue(false, "incorrect search result for location: "+location);
            }
        }
    }

    @Test
    public void validateAdultNumIncrease(){
        HomePage homePage = HomePage.open();
        homePage.clickOnGuestSpan();
        int currentAdultCount = homePage.getAdultCount();
        Assert.assertEquals(2, currentAdultCount);
        homePage.addAdult();
        int countAfterAdd = homePage.getAdultCount();
        Assert.assertEquals(2+1, countAfterAdd);
    }

    @Test
    public void validateChildNumIncrease(){
        HomePage homePage = HomePage.open();
        homePage.clickOnGuestSpan();
        int currentChildCount = homePage.getChildCount();
        Assert.assertEquals(0, currentChildCount);
        homePage.addChild();
        int countAfterAdd = homePage.getChildCount();
        Assert.assertEquals(0+1, countAfterAdd);
    }

    @Test
    public void validateRoomNumIncrease(){
        HomePage homePage = HomePage.open();
        homePage.clickOnGuestSpan();
        int currentRoomCount = homePage.getRoomCount();
        Assert.assertEquals(1, currentRoomCount);
        homePage.addRoom();
        int countAfterAdd = homePage.getRoomCount();
        Assert.assertEquals(1+1, countAfterAdd);
    }

    @Test(dataProviderClass = CommonDataProvider.class, dataProvider = "excelData")
    public void checkSelectedSearchDataWithForm(String location, String ciYear, String ciMonth, String ciDate, String coYear, String coMonth, String coDate) throws InterruptedException {
        HomePage homePage = HomePage.open();
        homePage.clearSearchDropdown();
        homePage.enterLettersForDropDown(location);
        homePage.clickDateDiv();
        homePage.selectDate(ciYear,ciMonth,ciDate);
        homePage.selectDate(coYear,coMonth,coDate);
        int currentChildCount = homePage.getChildCountSpan();
        int currentAdultCount = homePage.getAdultCountSpan();
        homePage.clickOnSearchButton();
        SearchResultPage searchResultPage = SearchResultPage.open();
        boolean isNavigated = searchResultPage.verifySearchPage(location);
        Assert.assertTrue(isNavigated);
        String searchLocation = searchResultPage.verifyLocation();
        Assert.assertEquals(searchLocation, location);
        boolean isCorrectCheckInDate = searchResultPage.verifyCheckInDate(ciYear,ciMonth,ciDate);
        Assert.assertTrue(isCorrectCheckInDate);
        boolean isCorrectCheckOutDate = searchResultPage.verifyCheckOutDate(coYear,coMonth,coDate);
        Assert.assertTrue(isCorrectCheckOutDate);
        int displayedAdultCount = searchResultPage.getDisplayedAdultCount();
        Assert.assertEquals(displayedAdultCount, currentAdultCount);
        int displayedChildCount = searchResultPage.getDisplayedChildCount();
        Assert.assertEquals(displayedChildCount, currentChildCount);
    }

    @Test(dataProviderClass = CommonDataProvider.class, dataProvider = "excelData")
    public void nightStayDisplayInSearch(String location, String ciYear, String ciMonth, String ciDate, String coYear, String coMonth, String coDate) throws InterruptedException {
        SearchResultPage searchResultPage = SearchResultPage.open();
        searchResultPage.navigateToSearchPage();
        searchResultPage.enterSearchLocation(location);
        searchResultPage.selectFirstSearchOption();
        searchResultPage.clickOnCheckInDateDiv();
        searchResultPage.checkVisibilityForDateExpansion();
        searchResultPage.selectDate(ciYear,ciMonth,ciDate);
        searchResultPage.clickOnCheckOutDateDiv();
        searchResultPage.checkVisibilityForDateExpansion();
        searchResultPage.selectDate(coYear,coMonth,coDate);
        boolean result =searchResultPage.verifyNumOfNights();
        Assert.assertTrue(result, "Number of nights stay value is calculated Incorrectly");

    }


    @Test(dataProviderClass = CommonDataProvider.class, dataProvider = "excelData")
    public void nightStayDisplayInSearchResult(String location, String ciYear, String ciMonth, String ciDate, String coYear, String coMonth, String coDate) throws InterruptedException {
        SearchResultPage searchResultPage = SearchResultPage.open();
        searchResultPage.navigateToSearchPage();
        searchResultPage.enterSearchLocation(location);
        searchResultPage.selectFirstSearchOption();
        searchResultPage.clickOnCheckInDateDiv();
        searchResultPage.checkVisibilityForDateExpansion();
        searchResultPage.selectDate(ciYear,ciMonth,ciDate);
        searchResultPage.clickOnCheckOutDateDiv();
        searchResultPage.checkVisibilityForDateExpansion();
        searchResultPage.selectDate(coYear,coMonth,coDate);

        String NumStayText = searchResultPage.GetNumNightsStayText();
        long numStayDaysFromText = searchResultPage.getNumOfDaysFromText(NumStayText);
        searchResultPage.clickSearchButton();
        boolean result = searchResultPage.verifyNumDaysInSearchResults(numStayDaysFromText);
        Assert.assertTrue(result, "Search results show incorrect number of nights stay details");
    }

}
