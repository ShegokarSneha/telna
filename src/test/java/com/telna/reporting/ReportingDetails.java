package com.telna.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportingDetails {

    public static void captureScreenshot(Page page, String message){
        String screenshotDir = "AutomationReport/screenshots/";
        File ssDir = new File(screenshotDir);
        if (!ssDir.exists()) {
            ssDir.mkdirs();
        }
        String screenshotFileName = "Screenshot_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
        String screenshotPath = screenshotDir + screenshotFileName;

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(screenshotPath)));
        String destinationPath = screenshotPath.substring(screenshotPath.indexOf("/") + 1);
       ExtentReportListener.getExtentTest().pass(message, MediaEntityBuilder.createScreenCaptureFromPath(destinationPath).build());
    }

    public static void addStepInReport(boolean condition, String message){
        ExtentTest extentTest = ExtentReportListener.getExtentTest();
        if(extentTest != null){
            if(condition) {
                extentTest.pass(message);
            }else {
                extentTest.fail(message);
            }
        }
    }

    public static void addStepInReport(String message){
        ExtentTest extentTest = ExtentReportListener.getExtentTest();
        if(extentTest != null){
                extentTest.pass(message);
        }
    }
}
