package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class driverFactory {
    private static driverFactory instance = null;
    static ThreadLocal<WebDriver> webdriver = new ThreadLocal<WebDriver>();
    private driverFactory(){}

    public enum browserType {chrome,firefox,ie}

    public static WebDriver initDriver(String browser,String url, String environment) {
        WebDriver driver = null;


        switch (browser.toString().toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
        }
        driver.manage().deleteAllCookies();
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        try {			Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();		}

        return driver;

    }

    public static WebDriver getWebDriver() {
        return webdriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        if(driverFactory.getWebDriver()== null) {
            webdriver.set(driver);
        }
    }

    public static void tearDownDrivers(){
        // remove driver instances from ThreadLocal
        ExtentReport.extentReport.setTestRunnerOutput(Thread.currentThread().getId()+" Test Manager:tear down drivers- "+" entry");
        try {
            if(driverFactory.getWebDriver()!=null) {
                driverFactory.getWebDriver().quit();
                driverFactory.webdriver.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
