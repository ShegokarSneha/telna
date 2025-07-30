package com.telna.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.telna.setup.BaseTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import com.telna.utility.LogMapper;
import org.testng.*;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String reportName = "AutomationReport_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/AutomationReport/" + reportName);

        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("Test Suite Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setReportName("Execution-Status");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setCss("css-string");
        htmlReporter.config().setJs("js-string");
        htmlReporter.config().setProtocol(Protocol.HTTPS);
        htmlReporter.config().setTimelineEnabled(true);
        htmlReporter.config().setTimeStampFormat("MMM dd, YYYY HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Host Name", "MyHost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Your Name");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    // This method is called before a test method starts.
    @Override
    public void onTestStart(ITestResult result) {
        LogMapper.info(result.getMethod().getMethodName() + "Test Started");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest); // Set the test for the current thread
    }

    // This method is called when a test method succeeds.
    @Override
    public void onTestSuccess(ITestResult result) {
        LogMapper.info(result.getMethod().getMethodName() + "Test Passed");
        test.get().log(Status.PASS, MarkupHelper.createLabel(result.getMethod().getMethodName() + " Test Case PASSED", ExtentColor.GREEN));
    }

    // This method is called when a test method fails.
    @Override
    public void onTestFailure(ITestResult result) {
        LogMapper.info(result.getMethod().getMethodName() + "Test failed");
        test.get().log(Status.FAIL, MarkupHelper.createLabel(result.getMethod().getMethodName() + " Test Case FAILED", ExtentColor.RED));
        test.get().fail(result.getThrowable()); // Log the exception/error
        try {
            // Get the instance of the test class that failed
            Object testInstance = result.getInstance();
            if (testInstance instanceof BaseTest) {
                BaseTest baseTest = (BaseTest) testInstance;
                Page page = baseTest.getPage(); // Access the protected 'page' instance variable

                if (page != null) {
                    String screenshotDir = System.getProperty("user.dir") + "/AutomationReport/screenshots/";
                    File ssDir = new File(screenshotDir);
                    if (!ssDir.exists()) {
                        ssDir.mkdirs();
                    }

                    String screenshotFileName = result.getMethod().getMethodName() + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
                    String screenshotPath = screenshotDir + screenshotFileName;

                    page.screenshot(new Page.ScreenshotOptions()
                            .setPath(Paths.get(screenshotPath))
                            .setFullPage(true)
                            .setType(ScreenshotType.PNG));

                    test.get().fail("Screenshot: " + test.get().addScreenCaptureFromPath(screenshotPath));
                } else {
                    test.get().warning("Playwright Page instance not available for screenshot for test: " + result.getMethod().getMethodName());
                }
            } else {
                test.get().warning("Test instance is not an instance of BaseTest, cannot access Playwright Page for screenshot.");
            }
        } catch (Exception e) {
            test.get().fail("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // This method is called when a test method is skipped.
    @Override
    public void onTestSkipped(ITestResult result) {
        Class<? extends IRetryAnalyzer> retryClass =
                result.getMethod().getRetryAnalyzerClass();
        if (retryClass != null && !retryClass.equals(org.testng.internal.annotations.DisabledRetryAnalyzer.class)) {
            test.get().log(Status.WARNING, MarkupHelper.createLabel(result.getMethod().getMethodName() + " Test Case RETRIED", ExtentColor.BLUE));
            LogMapper.info(result.getMethod().getMethodName() + "Test Retried");
        } else {
            test.get().log(Status.SKIP, MarkupHelper.createLabel(result.getMethod().getMethodName() + " Test Case SKIPPED", ExtentColor.ORANGE));
            LogMapper.info(result.getMethod().getMethodName() + "Test Skipped");
        }
    }

    // These methods are not commonly used for basic reporting, but are part of ITestListener
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented for this example
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result); // Treat timeout as a failure
    }

    public static ExtentTest getExtentTest() {
        return test.get();
    }
}
