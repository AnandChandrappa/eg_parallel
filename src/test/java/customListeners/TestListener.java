package customListeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import utilities.ExtentReport;
import utilities.GenericUtility;
import utilities.driverFactory;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener, IExecutionListener, ITest {
    static LocalDateTime startTimeofTCExecution;
    String startTimeStamp, testName ;
    ExtentTest extentTestObj;
    String formattedTime;
    ZonedDateTime currentTime;

    @Override
    public void onExecutionStart() {
        ExtentReport.initiateReportLog();
    }

    @Override
    public void onExecutionFinish() {
        driverFactory.tearDownDrivers();
//        ExtentReport.tearDownReport();
        if(ExtentReport.extentReport!=null)
            ExtentReport.extentReport=null;
    }

    public String returnTestName(){return testName;}

    @Override
    public void onTestStart(ITestResult iTestResult) {
        ExtentReport.extentReport.setTestRunnerOutput(Thread.currentThread().getId()+" Test Execution Listener:before invocation for test - "+iTestResult.getMethod().getMethodName());
        String browserName = GenericUtility.readConfigs("browser");
        String url = GenericUtility.readConfigs("applicationURL");
        ExtentReport.extentReport.setTestRunnerOutput(Thread.currentThread().getId()+" Test Execution Listener:before invocation for test - "+iTestResult.getMethod().getMethodName()+" browser name:"+browserName+" environment:"+"Test");

        WebDriver driver = driverFactory.initDriver(browserName ,url,"Test");
        testName = iTestResult.getTestName();
        if (driver!=null) {
            driverFactory.setWebDriver(driver);
        }else {
            throw new RuntimeException("Unable to produce the driver");
        }

        extentTestObj = ExtentReport.extentReport.createTest(iTestResult.getMethod().getMethodName());
        if(ExtentReport.getExtentTest() == null){
            ExtentReport.setExtentTest(extentTestObj);
        }

        ExtentReport.getExtentTest().assignCategory(iTestResult.getInstanceName());
        String groups[] = iTestResult.getMethod().getGroups();
        for (int i=0;i<groups.length;i++){
            ExtentReport.getExtentTest().assignCategory(groups[i]);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        startTimeStamp = dtf.format(now);
        startTimeofTCExecution = LocalDateTime.parse(startTimeStamp, dtf);

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        try {
            ExtentReport.getExtentTest().pass(MarkupHelper.createLabel(iTestResult.getMethod().getMethodName() + " - **PASSED***", ExtentColor.GREEN));
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            ExtentReport.flushReport();
            ExtentReport.tearDownReport();
            driverFactory.tearDownDrivers();
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try {
            currentTime = ZonedDateTime.now();
            formattedTime = currentTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyy_hh_mm_ss"));
            String fileName = GenericUtility.takeScreenShot(iTestResult.getMethod().getMethodName(), formattedTime);
            ExtentReport.getExtentTest().fail(MarkupHelper.createLabel(iTestResult.getMethod().getMethodName() + " - **FAILED***", ExtentColor.RED));
            ExtentReport.getExtentTest().addScreenCaptureFromPath(fileName);
        }catch (Exception e){
            ExtentReport.getExtentTest().error(e.fillInStackTrace());
        }finally {
            ExtentReport.flushReport();
            ExtentReport.tearDownReport();
            driverFactory.tearDownDrivers();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        try {
            ExtentReport.getExtentTest().skip(MarkupHelper.createLabel(iTestResult.getMethod().getMethodName() + " - **SKIPPED***", ExtentColor.AMBER));
        }catch (Exception e){
            ExtentReport.getExtentTest().error(e.fillInStackTrace());
        }finally {
            ExtentReport.flushReport();
            ExtentReport.tearDownReport();
            driverFactory.tearDownDrivers();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    @Override
    public String getTestName() {
        return testName;
    }
}
