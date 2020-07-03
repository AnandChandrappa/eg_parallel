package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReport {
    public static ExtentReports extentReport = null;
    private static ExtentHtmlReporter htmlReport;
    private static String reportLocation;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static synchronized void initiateReportLog() {
        // creates the report instance
        ZonedDateTime currentTime = ZonedDateTime.now();
        String formattedTime = currentTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyy_hh_mm_ss"));
        reportLocation = ".//target/extent_reports/EvolutionGaming.html";
        if (extentReport == null) {
            htmlReport = new ExtentHtmlReporter(reportLocation);
            htmlReport.config().setTheme(Theme.DARK);
            htmlReport.config().setDocumentTitle("Java Project");
            htmlReport.config().setReportName("Test Report");
            htmlReport.config().setAutoCreateRelativePathMedia(true);
            htmlReport.config().setTimeStampFormat("HH:mm:ss");
            //  htmlReport.start();

            extentReport = new ExtentReports();
            extentReport.attachReporter(htmlReport);
            extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
            extentReport.setSystemInfo("User Name", System.getProperty("user.name"));
            extentReport.setSystemInfo("Application", "Evolution");
            extentReport.setSystemInfo("Environment", "Test");
            extentReport.setSystemInfo("Browser", GenericUtility.readConfigs("browser").toUpperCase());

        }
        return;
    }

    public static void flushReport(){
        // writes the test case result to HTML file
        extentReport.flush();
    }

    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }

    public static void setExtentTest(ExtentTest extentTestInst) {
        extentTest.set(extentTestInst);
    }

    public static void tearDownReport(){
        if(ExtentReport.getExtentTest() != null)
            ExtentReport.setExtentTest(null);
    }
}
