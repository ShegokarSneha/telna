package com.telna.pages;

import com.microsoft.playwright.Page;

public class HomePage {
    Page page;
    public HomePage(Page page) {
        this.page = page;
    }

    private String activeTabText = "a[class='oxd-main-menu-item active'] > span";
    private String adminTab = "a[href*=admin]";
    private String leaveTab = "a[href*=Leave]";
    private String recruitmentTab = "a[href*=Recruitment]";

    public String getActiveTabText(){
        page.waitForSelector(activeTabText, (new Page.WaitForSelectorOptions().setTimeout(60000))).isVisible();
        return page.locator(activeTabText).innerText();
    }

    public void clickOnAdminTab(){
        page.click(adminTab);
    }

    public void clickOnLeaveTab(){
        page.click(leaveTab);
    }

    public void clickOnRecruitmentTab(){
        page.click(recruitmentTab);
    }
}
