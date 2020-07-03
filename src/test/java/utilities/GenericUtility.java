package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class GenericUtility {

	Actions action = null;	  

	public GenericUtility() {
		this.action = new Actions(driverFactory.getWebDriver());
	}

	public static String takeScreenShot(String methodName, String timeStamp) throws Exception {
		// captures the screen shot
		String reportLocation = ".//target/extent_reports/Screenshots/";
		String fileName = reportLocation +methodName+"_"+timeStamp+".png";
		try {
			EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driverFactory.getWebDriver());
			File scrFile = eventFiringWebDriver.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(fileName));
			System.out.println("***Placed screen shot in "+reportLocation+" ***");
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return fileName;
	}

	public static String readConfigs(String property){
		String propValue=null;
		try {
			Properties props = new Properties();
			File fs = new File(new File("src"), "/test/java/config.properties");
			FileInputStream fis = new FileInputStream(fs.getAbsoluteFile());
			props.load(fis);
			propValue = props.getProperty(property);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propValue;
	}

	public void waitForPageLoad(WebDriver driver){
		try{
			Thread.sleep(3000);
			WebDriverWait wait = new WebDriverWait(driver,60);
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public boolean switchToNewWindow(String windowTitle, WebDriver driver){
		boolean switchWin = false;
		try {
			String winHandle;
			Set<String> handles = driver.getWindowHandles();
			int a = handles.size();
			Iterator it = handles.iterator();
			while (it.hasNext()) {
				winHandle = (String) it.next();
				driver.switchTo().window(winHandle);
				if (driver.getTitle().toLowerCase().contains(windowTitle.toLowerCase())) {
					switchWin = true;
					break;
				}
				waitForPageLoad(driver);
			}
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		return switchWin;
	}

	public void closeAllWindows(WebDriver driver){
		try {
			String winHandle;
			Set<String> handles = driver.getWindowHandles();
			int a = handles.size();
			Iterator it = handles.iterator();
			while (it.hasNext()) {
				winHandle = (String) it.next();
				driver.switchTo().window(winHandle);
				driver.close();
				Thread.sleep(2000);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void closeAnWindow(WebDriver driver, String windowName){
		try {
			String winHandle;
			Set<String> handles = driver.getWindowHandles();
			int a = handles.size();
			Iterator it = handles.iterator();
			while (it.hasNext()) {
				winHandle = (String) it.next();
				driver.switchTo().window(winHandle);
				if (driver.getTitle().contains(windowName)) {
					driver.close();
					Thread.sleep(2000);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
