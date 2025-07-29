package com.telna.tests;

import com.telna.page.HomePage;
import com.telna.reporting.ReportingDetails;
import com.telna.setup.BaseTest;
import com.telna.reporting.ExtentReportListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(ExtentReportListener.class)
public class ApplicationTest extends BaseTest {

    @Test
    public void goToAdmin(){
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewSystemUsers");
        HomePage homePage = new HomePage(page);
        Assert.assertEquals("Admin", homePage.getTabText());
        Assert.assertTrue(page.url().contains("/admin/viewSystemUsers"), "Matching Admin url");
        assertThat(page).hasURL(Pattern.compile(".*/admin/viewSystemUsers.*"));
        System.out.println("Admin : " + page.url());
        ReportingDetails.captureScreenshot(page, "Admin Tab opened Successfully");
        ReportingDetails.addStepInReport(true, "Admin URL : " +  page.url());
    }

    @Test
    public void goToLeave(){
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/leave/viewLeaveList");
        HomePage homePage = new HomePage(page);
        Assert.assertEquals("Leave", homePage.getTabText());
        assertThat(page).hasURL(Pattern.compile(".*/leave/viewLeaveList.*"));
        System.out.println("Leave : " + page.url());
        ReportingDetails.captureScreenshot(page, "Leave Tab opened Successfully");
        ReportingDetails.addStepInReport(true, "Leave URL : " +  page.url());
    }

    @Test
    public void goToRecruitment(){
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/viewCandidates");
        HomePage homePage = new HomePage(page);
        Assert.assertEquals("Recruitment", homePage.getTabText());
        assertThat(page).hasURL(Pattern.compile(".*/recruitment/viewCandidates.*"));
        System.out.println("Recruitment : " + page.url());
        ReportingDetails.captureScreenshot(page,"Recruitment Tab opened Successfully");
        ReportingDetails.addStepInReport(true, "Recruitment URL : " +  page.url());
    }

}
