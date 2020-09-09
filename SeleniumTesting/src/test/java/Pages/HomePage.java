package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class HomePage extends BasePageObject {

    private static final String homePageUrl = "https://www.booking.com/";

    @FindBy(css = "input#ss")
    private WebElement searchDropDown;

    @FindBy(xpath = "//label[@class='sb-destination-label-sr']/following-sibling::ul[1]/child::li")
    private List<WebElement> destinationSearchList;

    @FindBy(css = "span.xp__guests__count")
    private WebElement guestSpan;

    @FindBy(xpath = "//div[contains(@class, 'sb-group__field-adults')]/descendant::span[@class='bui-stepper__display']")
    private WebElement adultSpan;

    @FindBy(xpath = "//div[contains(@class, 'sb-group__field-adults')]/descendant::button[contains(@class, 'bui-stepper__add-button')]")
    private WebElement adultIncreaseButton;

    @FindBy(xpath = "//div[contains(@class, 'sb-group-children')]/descendant::span[@class='bui-stepper__display']")
    private WebElement childSpan;

    @FindBy(xpath = "//div[contains(@class, 'sb-group-children')]/descendant::button[contains(@class, 'bui-stepper__add-button')]")
    private WebElement childIncreaseButton;

    @FindBy(xpath = "//div[contains(@class, 'sb-group__field-rooms')]/descendant::button[contains(@class, 'bui-stepper__add-button')]")
    private WebElement roomIncreaseButton;

    @FindBy(xpath = "//div[contains(@class, 'sb-group__field-rooms')]/descendant::span[@class='bui-stepper__display']")
    private WebElement roomSpan;

    @FindBy(css = "div[class='xp__dates xp__group']")
    private WebElement dateDiv;

    @FindBy (xpath = "//div[@class='bui-calendar__wrapper']")
    private List<WebElement> currentCalendar;

    @FindBy(css = "button.sb-searchbox__button ")
    private WebElement searchButton;
    //span[@data-room-count]

    @FindBy(xpath = "//span[@data-room-count]")
    private WebElement roomCountSpan;

    @FindBy(xpath = "//span[@data-children-count]")
    private WebElement childCountSpan;

    @FindBy(xpath = "//span[@data-adults-count]")
    private WebElement adultCountSpan;

    public HomePage(){
        driver.get(homePageUrl);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        PageFactory.initElements(driver,this);
    }

    public static HomePage open(){
        HomePage homePage = new HomePage();
        return homePage;
    }

    public void enterLettersForDropDown(String keys){
        searchDropDown.sendKeys(keys);
    }

    public void clearSearchDropdown(){
        searchDropDown.clear();
    }

    public boolean checkVisibilityOfResults(){
        return waitForVisibility(destinationSearchList);
    }

    public List<WebElement> getAllDropDownResults(){
        return destinationSearchList;
    }

    public List<String> getAllSearchResults(){
        List<String> searchResultList = new ArrayList<>();
        for (WebElement ele : destinationSearchList){
            String searchResult = ele.findElement(By.xpath("span[2]")).getAttribute("innerHTML");
            searchResultList.add(searchResult);
        }
        return searchResultList;
    }

    public void clickOnGuestSpan(){
        guestSpan.click();
    }

    public int getAdultCount(){
        int adultCount = Integer.parseInt(adultSpan.getText()) ;
        return adultCount;
    }

    public void addAdult(){
        adultIncreaseButton.click();
    }

    public int getChildCount(){
        int childCount = Integer.parseInt(childSpan.getText()) ;
        return childCount;
    }

    public void addChild(){
        childIncreaseButton.click();
    }

    public int getRoomCount(){
        int roomCount = Integer.parseInt(roomSpan.getText()) ;
        return roomCount;
    }

    public void addRoom(){
        roomIncreaseButton.click();
    }

    public void clickDateDiv(){
        dateDiv.click();
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

    public boolean isDateSame(String day, String calendarDay){
        if(calendarDay!=null){
            return day.equals(calendarDay);
        }else {
            Assert.fail("Calendar day is null");
            return false;
        }
    }

    public void clickOnSearchButton(){
        searchButton.click();
    }

    public int getRoomCountSpan(){
        return Integer.parseInt(String.valueOf(roomCountSpan.getText().charAt(0)));
    }

    public int getChildCountSpan(){
        return Integer.parseInt(String.valueOf(childCountSpan.getText().charAt(0)));
    }

    public int getAdultCountSpan(){
        return Integer.parseInt(String.valueOf(adultCountSpan.getText().charAt(0)));
    }
}
