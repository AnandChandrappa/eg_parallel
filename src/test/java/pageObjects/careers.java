package pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.ExtentReport;
import utilities.GenericUtility;
import utilities.driverFactory;

import java.util.List;

public class careers {
    ExtentTest extentTest;
    GenericUtility genericUtility;
    WebDriver driver;

    careers(){
        PageFactory.initElements(driverFactory.getWebDriver(),this);
        genericUtility = new GenericUtility();
        this.driver = driverFactory.getWebDriver();
        this.extentTest = ExtentReport.getExtentTest();
    }

    @FindBy(xpath="//form//div//span[text()='Choose job category']/parent::a")
    private WebElement chooseJobCategory;

    @FindBy(xpath = "//div[@class='top-bar group']//ul[@class='menu']//a[contains(text(),'Careers')]")
    private WebElement menuLinkCareer;


    @FindBy(xpath="//form//div//span[text()='Choose location']/parent::a")
    private WebElement chooseJobLocation;

    @FindBy(css="input[title='GO']")
    private WebElement go;

    @FindBy(xpath = "//div[text()='Posted by']/following-sibling::div[@class='postedByName']")
    private List<WebElement> postedBy;

    public void chooseJobCategory(String jobCategory){
        try {
            String xPath = "//ul//li//div[text()='"+jobCategory+"']";
            Assert.assertTrue(chooseJobCategory.isDisplayed(), "Navigate to careers home page before selecting the job category");
            chooseJobCategory.click();
            driver.findElement(By.xpath(xPath)).click();
            genericUtility.waitForPageLoad(driver);
        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }

    }

    public void chooseJobLocation(String jobLocation){
        try {
            String xPath = "//ul//li//div[text()='" + jobLocation + "']";
            Assert.assertTrue(chooseJobLocation.isDisplayed(), "Navigate to careers home page before selecting the job location");
            chooseJobLocation.click();
            driver.findElement(By.xpath(xPath)).click();
            genericUtility.waitForPageLoad(driver);
        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }
    }

    public void searchJob(String category, String location){
        try {
            Thread.sleep(3000);
            Assert.assertTrue(menuLinkCareer.isDisplayed(),"Unable to click on Career link from Menu");
            menuLinkCareer.click();
            genericUtility.waitForPageLoad(driver);
            genericUtility.switchToNewWindow("careers",driver);
            chooseJobCategory(category);
            chooseJobLocation(location);
            go.click();
            genericUtility.waitForPageLoad(driver);
            extentTest.log(Status.INFO,"Searched for the job from Career page");
        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }
    }

    public void selectJobFromResult(String jobName){
        try {
            String xPath = "//div[@id='listOfJobs']//div[contains(text(),'" + jobName + "')]";
            WebElement element = driver.findElement(By.xpath(xPath));
            Assert.assertTrue(element.isDisplayed(), "Searched job is not listed in the result");

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            genericUtility.waitForPageLoad(driver);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            genericUtility.waitForPageLoad(driver);
            xPath = "//h1[contains(text(),'"+jobName+"')]";
            Assert.assertTrue(driver.findElement(By.xpath(xPath)).isDisplayed(), "Failed to navigate to searched job page");
            extentTest.log(Status.INFO,"Successfully searched the job and navigated to job details page");

        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }
    }

    public void getPosteByNames(){
        String postedByNames[] = new String[postedBy.size()];
        for (int i = 0; i < postedBy.size(); i++) {
            postedByNames[i] = postedBy.get(i).getText();
        }

        extentTest.log(Status.INFO, "This job is posted by: "+ postedByNames[0]);
        extentTest.log(Status.INFO, "Hiring Manager for this job is: "+ postedByNames[1]);
        genericUtility.closeAnWindow(driver,"careers");
    }
}
