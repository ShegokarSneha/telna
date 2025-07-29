package com.telna.pages;

import com.microsoft.playwright.Page;

public class HomePage {
    Page page;
    public HomePage(Page page) {
        this.page = page;
    }

    private String activeText = "a[class='oxd-main-menu-item active'] > span";

    public String getTabText(){
        return page.locator(activeText).innerText();
    }
}
