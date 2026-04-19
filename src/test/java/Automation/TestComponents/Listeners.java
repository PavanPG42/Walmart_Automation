package Automation.TestComponents;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Listeners extends BaseTest implements ITestListener {

    private static final Logger log = LogManager.getLogger(Listeners.class);
    ExtentReports extent;
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String path = System.getProperty("user.dir") + "/reports/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Walmart Automation Results");
        reporter.config().setDocumentTitle("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "QA Team");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        log.info("Test started: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test passed");
        log.info("Test PASSED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        log.error("Test FAILED: {}", result.getMethod().getMethodName());

        try {
            driver = (WebDriver) result.getTestClass()
                .getRealClass().getField("driver").get(result.getInstance());
            String filePath = getScreenshot(result.getMethod().getMethodName(), driver);
            extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
        } catch (Exception e) {
            log.error("Could not capture screenshot: {}", e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        log.info("Extent report flushed");
    }
}