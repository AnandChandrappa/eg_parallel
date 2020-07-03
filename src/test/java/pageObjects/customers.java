package pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITest;
import utilities.ExtentReport;
import utilities.GenericUtility;
import utilities.driverFactory;

import java.util.List;

public class customers  {
    ExtentTest extentTest;
    GenericUtility genericUtility;
    //WebDriver driver;

    customers(){
        PageFactory.initElements(driverFactory.getWebDriver(),this);
        genericUtility = new GenericUtility();
        //this.driver = driverFactory.getWebDriver();
        this.extentTest = ExtentReport.getExtentTest();
    }

    @FindBy(css="ul.embed-customers-list li a")
    private List<WebElement> customerLogosLink;

    @FindBy(xpath="//h2[text()='Navigate']/following-sibling::div//a[contains(text(),'Customers')]")
    private WebElement LHNcustomerLink;

    public void captureCustomerLinks(){
        String customerNames[] = new String[customerLogosLink.size()];
        try {
            WebDriverWait wait = new WebDriverWait(driverFactory.getWebDriver(),60);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            Assert.assertTrue(LHNcustomerLink.isDisplayed(), "Not in Customer page cannot fetch customers details");
            for (int i = 0; i < customerLogosLink.size(); i++) {
                customerNames[i] = customerLogosLink.get(i).getAttribute("href");
            }
            extentTest.log(Status.INFO,"Captured all the Customer url's : "+ customerNames.length);
            int count=0;
            String a[][]=new String[10][4];
            for(int i=0;i<10;i++)
            {
                for(int j=0;j<4;j++)
                {
                    //if(count==d.length) break;
                    a[i][j]=customerNames[count];
                    count++;
                }
            }
            extentTest.log(Status.INFO,"Below is the table with customer url");
            extentTest.log(Status.INFO, MarkupHelper.createTable(a));
        }catch (Exception e){
            extentTest.fail(e.getMessage());
            Assert.fail();
        }


    }

}
