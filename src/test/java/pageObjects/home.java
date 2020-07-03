package pageObjects;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.GenericUtility;

import java.util.List;

public class home {
    //WebDriver driver;
    ExtentTest extentTest;
    GenericUtility genericUtility;



    @FindBy(css="section #logo")
    private WebElement evolutionGamgingLogo;


    @FindBy(xpath = "//div[@class='top-bar group']//ul[@class='menu']")
    private WebElement menuLinks;

    @FindBy(css = "h1>a[href*='live-roulette']")
    private WebElement liveRoulette;


    public home()  {
        PageFactory.initElements(driverFactory.getWebDriver(),this);
        genericUtility = new GenericUtility();
        //this.driver = driverFactory.getWebDriver();
        this.extentTest = ExtentReport.getExtentTest();
    }

    public void clickOnMenuLink(String itemName){
        try {
            String xPath = "//div[@class='top-bar group']//ul[@class='menu']//a[contains(text(),'"+itemName+"')]";
            Assert.assertTrue(evolutionGamgingLogo.isDisplayed(),"Not in home page cannot click on menu link");
            driverFactory.getWebDriver().findElement(By.xpath(xPath)).click();
            WebDriverWait wait = new WebDriverWait(driverFactory.getWebDriver(),60);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            extentTest.log(Status.INFO,"Clicked on "+itemName+" link from Menu links");

        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }
    }

    public void clickOnLiveRoulette(){
        try {
            genericUtility.waitForPageLoad(driverFactory.getWebDriver());
            ((JavascriptExecutor) driverFactory.getWebDriver()).executeScript("window.scrollBy(0,1300)");
            Thread.sleep(5000);
            Assert.assertTrue(liveRoulette.isDisplayed(),"Not in home page cannot click on Live Roulette");
            liveRoulette.click();
            WebDriverWait wait = new WebDriverWait(driverFactory.getWebDriver(),60);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            extentTest.log(Status.INFO,"Clicked on Live Roulette link from home page");

        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }
    }

    public void clickOnMenuItem(String menuName, String itemName){
        try {
            Thread.sleep(5000);
            String xPath = "//div[@class='top-bar group']//ul[@class='menu']//a[contains(text(),'"+menuName+"')]";
            Assert.assertTrue(evolutionGamgingLogo.isDisplayed(),"Not in home page cannot click on menu link");
            Actions builder = new Actions(driverFactory.getWebDriver());
            Actions axn = builder.moveToElement(driverFactory.getWebDriver().findElement(By.xpath(xPath)));
            axn.perform();
            WebDriverWait wait = new WebDriverWait(driverFactory.getWebDriver(),60);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            xPath = xPath + "/following-sibling::ul//a[contains(text(),'"+itemName+"')]";
            driverFactory.getWebDriver().findElement(By.xpath(xPath)).click();
            wait = new WebDriverWait(driverFactory.getWebDriver(),60);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            extentTest.log(Status.INFO,"Clicked on "+itemName+" link from Menu links");
        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }
    }

    public String[] captureMenuLinkList(String itemName){
        String[] listsText=null;
        List<WebElement> lists;
        try {
            
            String xPath = "//div[@class='top-bar group']//ul[@class='menu']//a[contains(text(),'"+itemName+"')]";
            Assert.assertTrue(evolutionGamgingLogo.isDisplayed(),"Not in home page cannot click on menu link");
            Actions builder = new Actions(driverFactory.getWebDriver());
            Actions axn = builder.moveToElement(driverFactory.getWebDriver().findElement(By.xpath(xPath)));
            axn.perform();
            WebDriverWait wait = new WebDriverWait(driverFactory.getWebDriver(),60);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            lists = driverFactory.getWebDriver().findElements(By.xpath(xPath + "/following-sibling::ul//a"));
            listsText = new String[lists.size()];
            for (int i = 0; i < lists.size(); i++) {
                listsText[i] = lists.get(i).getAttribute("innerText");
            }
        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }
        extentTest.log(Status.INFO,"Captured all the game names listed in the Menu, total games: "+ listsText.length);

        int count=0;
        String a[][]=new String[7][4];
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<4;j++)
            {
                //if(count==d.length) break;
                a[i][j]=listsText[count];
                count++;
            }
        }
        extentTest.log(Status.INFO,"Below is the table with Game names");
        extentTest.log(Status.INFO, MarkupHelper.createTable(a));

        return listsText;
    }

/*
    public void verify_menulinks()  {
        try{
            menuLink.click();
            String linkNames[] = new String[menuLinks.size()];
            for (int i = 0; i < menuLinks.size(); i++) {

                linkNames[i] = menuLinks.get(i).getText();
                if(linkNames[i].startsWith("Home")){
                    continue;
                }
                driver.findElement(By.linkText(linkNames[i])).click();
                extentTest.log(Status.INFO, "clicked on the link: " + linkNames[i]);
                driver.findElement(By.linkText(linkNames[i])).click();
                extentTest.log(Status.INFO, "Title of the page: " + driver.getTitle());
                Thread.sleep(1000);
                menuLink.click();
            }

        }catch (Exception e){
            extentTest.fail(e.getMessage());
        }
    }
*/
}
