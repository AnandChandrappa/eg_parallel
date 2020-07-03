package pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.ExtentReport;
import utilities.GenericUtility;
import utilities.driverFactory;

import java.util.List;

public class ourGames {
    ExtentTest extentTest;
    GenericUtility genericUtility;
    WebDriver driver;

    ourGames(){
        PageFactory.initElements(driverFactory.getWebDriver(),this);
        genericUtility = new GenericUtility();
        this.driver = driverFactory.getWebDriver();
        this.extentTest = ExtentReport.getExtentTest();
        //extentTest.log(Status.DEBUG,"Current Thread ID: "+Thread.currentThread().getId());
        //extentTest.log(Status.DEBUG,"Current Driver ID: "+((ChromeDriver)driverFactory.getWebDriver()).getSessionId().toString());
    }

    @FindBy(xpath="//picture[@title='live roulette']/parent::a")
    private WebElement linkLiveRoulette;

    @FindBy(xpath="//h2[text()='Navigate']/parent::div//ul//following-sibling::a")
    private List<WebElement> navigateLinks;

    @FindBy(xpath="//h2[text()='Navigate']")
    private WebElement navigateSection;

    public void clickOnLiveRoulette(){
        try {
            genericUtility.waitForPageLoad(this.driver);
            Assert.assertTrue(linkLiveRoulette.isDisplayed(),"Not in our-games page cannot click on Live Roulette");
            linkLiveRoulette.click();
            genericUtility.waitForPageLoad(this.driver);
            Assert.assertTrue(navigateSection.isDisplayed(),"Unable to navigate to live-roulette page");
            extentTest.log(Status.INFO,"Navigated to live-roulette page");
        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }

    }

    public String[] captureNavigateLinkNames(){
        String listsNames[] = new String[navigateLinks.size()];
        try {
            Assert.assertTrue(navigateSection.isDisplayed(),"Not in live-roulette page cannot capture navigation link names");
            for (int i = 0; i < navigateLinks.size(); i++) {
                listsNames[i] = navigateLinks.get(i).getText();
                listsNames[i] = navigateLinks.get(i).getAttribute("innerText");
            }
        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }
        extentTest.log(Status.INFO,"Captured all the game names from LHN in the live-roulette page, total games: "+ listsNames.length);
        return listsNames;
    }
}
