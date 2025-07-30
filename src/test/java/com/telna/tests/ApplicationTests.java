package com.telna.tests;

import com.telna.pages.HomePage;
import com.telna.reporting.ReportingDetails;
import com.telna.setup.BaseTest;
import com.telna.reporting.ExtentReportListener;
import com.telna.utility.ConfigProvider;
import com.telna.utility.LogMapper;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

@Listeners(ExtentReportListener.class)
public class ApplicationTests extends BaseTest {

    @Test
    public void goToAdmin(){
        page.navigate(ConfigProvider.getAsString("application_url"));
        HomePage homePage = new HomePage(page);
        homePage.clickOnAdminTab();
        LogMapper.info("Clicked on Admin Tab");
        assertEquals("Admin", homePage.getActiveTabText());
        assertTrue(page.url().contains("/admin/viewSystemUsers"), "Matching Admin url");
        assertThat(page).hasURL(Pattern.compile(".*/admin/viewSystemUsers.*"));
        LogMapper.info("Admin : " + page.url());
        LogMapper.info("Admin Tab opened Successfully");
        ReportingDetails.addStepInReport(true, "Admin URL : " +  page.url());
        ReportingDetails.captureScreenshot(page, "Admin Tab opened Successfully");
    }

    @Test
    public void goToLeave(){
        page.navigate(ConfigProvider.getAsString("application_url"));
        HomePage homePage = new HomePage(page);
        homePage.clickOnLeaveTab();
        assertEquals("Leave", homePage.getActiveTabText());
        LogMapper.info("Clicked on Leave Tab");;
        assertTrue(page.url().contains("/leave/viewLeaveList"), "Matching Leave url");
        assertThat(page).hasURL(Pattern.compile(".*/leave/viewLeaveList.*"));
        System.out.println("Leave : " + page.url());
        ReportingDetails.captureScreenshot(page, "Leave Tab opened Successfully");
        ReportingDetails.addStepInReport(true, "Leave URL : " +  page.url());
        LogMapper.info("Leave Tab opened Successfully");
    }

    @Test
    public void goToRecruitment(){
        page.navigate(ConfigProvider.getAsString("application_url"));
        HomePage homePage = new HomePage(page);
        homePage.clickOnRecruitmentTab();
        assertEquals("Recruitment", homePage.getActiveTabText());
        LogMapper.info("Clicked on Recruitment Tab");
        assertTrue(page.url().contains("/recruitment/viewCandidates"), "Matching Recruitment url");
        assertThat(page).hasURL(Pattern.compile(".*/recruitment/viewCandidates.*"));
        LogMapper.info("Recruitment : " + page.url());
        ReportingDetails.captureScreenshot(page,"Recruitment Tab opened Successfully");
        ReportingDetails.addStepInReport(true, "Recruitment URL : " +  page.url());
        LogMapper.info("Recruitment Tab opened Successfully");
    }

}
