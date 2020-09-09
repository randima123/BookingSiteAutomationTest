package Pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SearchResultPage extends BasePageObject {

    final static Logger logger = Logger.getLogger(SearchResultPage.class);

    @FindBy(xpath = "//div[@role='heading']/h1")
    private WebElement heading;

    @FindBy(css = "input#ss")
    private WebElement searchDestination;

    @FindBy(xpath = "//div[@data-calendar2-title='Check-in']/descendant::div[@class='sb-date-field__display']")
    private WebElement checkInDateDiv;

    @FindBy(xpath = "//div[@data-calendar2-title='Check-out']/descendant::div[@class='sb-date-field__display']")
    private WebElement checkOutDateDiv;

    @FindBy(xpath = "//select[@id='group_adults']/descendant::option[@selected='selected']")
    private WebElement adultCountOption;

    @FindBy(xpath = "//select[@id='group_children']/descendant::option[@selected='selected']")
    private WebElement childrenCountOption;

    @FindBy(xpath = "//div[contains(@class,'--checkin-field xp__date-time')]")
    private WebElement checkInDateIconDiv;

    @FindBy(xpath = "//div[contains(@class,'--checkout-field xp__date-time')]")
    private WebElement checkOutDateIconDiv;

    @FindBy (xpath = "//div[@class='bui-calendar__wrapper']")
    private List<WebElement> currentCalendar;

    @FindBy(xpath = "//div[@class='bui-calendar__main b-a11y-calendar-contrasts']")
    private WebElement calendarBuiDiv;

    @FindBy(xpath = "//ul[@role='listbox']/li[1]")
    private WebElement firstSearchOption;

    @FindBy(xpath = "//div[contains(@class,'sb-dates__los')]")
    private WebElement NumNightStayText;

    @FindBy(xpath = "//button[contains(@class,'searchbox__button')]")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@id='hotellist_inner']")
    private WebElement searchResultList;

    public SearchResultPage(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(driver,this);
    }

    public void navigateToSearchPage(){
        driver.get("https://www.booking.com/searchresults.html");
    }

    public static SearchResultPage open(){
        return new SearchResultPage();
    }

    public boolean verifySearchPage(String destinationKey){
        try {
            String headingTitle = heading.getText();
            if(headingTitle!=null && headingTitle.contains(destinationKey) && headingTitle.contains("properties found")){
                return true;
            }else {
                Assert.fail("Heading not correct. Navigation to search page failed.");
                return false;
            }

        }catch (NoSuchElementException ex){
            ex.printStackTrace();
            Assert.fail("Heading not found. Navigation to search page failed.");
            return false;
        }

    }

    public String verifyLocation(){
        String searchLocation = searchDestination.getAttribute("value");
        return searchLocation;
    }

    public boolean verifyCheckInDate(String year, String month, String day){
        String checkInDate = checkInDateDiv.getText();
        if(checkInDate!=null && checkInDate.contains(year) && checkInDate.contains(month) && checkInDate.contains(day)){
            return true;
        }else{
            return false;
        }
    }

    public boolean verifyCheckOutDate(String year, String month, String day){
        String checkInDate = checkOutDateDiv.getText();
        if(checkInDate!=null && checkInDate.contains(year) && checkInDate.contains(month) && checkInDate.contains(day)){
            return true;
        }else{
            return false;
        }
    }

    public int getDisplayedAdultCount(){
        int displayedAdultCount = Integer.parseInt(adultCountOption.getAttribute("value"));
        return displayedAdultCount;
    }

    public int getDisplayedChildCount(){
        int displayedChildCount = Integer.parseInt(childrenCountOption.getAttribute("value"));
        return displayedChildCount;
    }

    public void enterSearchLocation(String location){
        searchDestination.sendKeys(location);
    }

    public void clickOnCheckInDateDiv(){
        checkInDateIconDiv.click();
    }
    public void clickOnCheckOutDateDiv(){
        checkOutDateIconDiv.click();
    }

    public void selectDate(String year, String month, String day){

        WebElement checkInElement=null;
        for(WebElement element: currentCalendar){
            String cal =element.findElement(By.xpath("div")).getText();
            if(cal.contains(year)&&cal.contains(month)){
                checkInElement=element;
                break;
            }
        }
        if(checkInElement!=null){
            try {
                WebElement dayElement = checkInElement.findElement(By.xpath("table/tbody/descendant::td/span/span[text()='"+day+"']"));
                if(dayElement != null){
                    dayElement.click();
                }
            }catch (NoSuchElementException ex){
                Assert.fail("Check in day not found");
                ex.printStackTrace();
            }
        }else {
            Assert.fail("Check in year and month not found");
        }

    }

    public void checkVisibilityForDateExpansion(){
        waitForVisibility(calendarBuiDiv);
    }

    public void  selectFirstSearchOption(){
        firstSearchOption.click();
    }

    public String GetNumNightsStayText(){
        return NumNightStayText.getText().trim();
    }

    public long getNumOfDaysFromText(String NumStayText){
        return Long.parseLong(NumStayText.split("-")[0]);
    }

    public boolean verifyNumOfNights(){
        String checkInDate = checkInDateDiv.getText();
        Date formattedCheckInDate = getDateFromString(checkInDate);
        String checkOutDate = checkOutDateDiv.getText();
        Date formattedCheckOutDate = getDateFromString(checkOutDate);
        long diff = formattedCheckOutDate.getTime() - formattedCheckInDate.getTime();
        long numOfNights = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        String NumStayText = GetNumNightsStayText();
        long numStayDaysFromText = getNumOfDaysFromText(NumStayText);
        if(numOfNights == numStayDaysFromText){
            return true;
        }else {
            return false;
        }

    }

    public Date getDateFromString(String stringDate){
        if(stringDate != null){
            String[] stringList = stringDate.split(" ");
            String formattedDate = stringList[1]+" "+stringList[2]+" "+stringList[3];
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = null;
            try {
                date = format.parse(formattedDate);
            } catch (ParseException e) {
                logger.error("Parse exception while parsing date.");
                e.printStackTrace();
            }
            return date;
        }else{
            logger.error("CheckInDateText is null.");
            return null;
        }
    }

    public void clickSearchButton(){
        searchButton.click();
    }

    public boolean verifyNumDaysInSearchResults(long numStayDaysFromText){
        List<WebElement> resultList = searchResultList
                .findElements(By.xpath("descendant::div[contains(@class,'bui-price-display ')]/div[1]/div"));
        for(WebElement element : resultList){
            long numOfNights = Long.parseLong(element.getText().split(" ")[0]);
            if(numOfNights != numStayDaysFromText){
                logger.info("Found wrong number of stay date: "+numOfNights);
                return false;
            }
        }
        return true;
    }

}
