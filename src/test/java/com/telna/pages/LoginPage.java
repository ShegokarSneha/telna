package com.telna.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    Page page;

    public LoginPage(Page page){
        this.page = page;
    }

    private String userName = "input[name='username']";
    private String password =  "input[name='password']";
    private String loginBtn = "button[type='submit']";

    public void enterUsername(String usernameValue){
        page.waitForSelector(userName, (new Page.WaitForSelectorOptions().setTimeout(30000))).isVisible();
        page.locator(userName).fill(usernameValue);
    }

    public void enterPassword(String passwordValue){
        page.waitForSelector(password, (new Page.WaitForSelectorOptions().setTimeout(30000))).isVisible();
        page.locator(password).fill(passwordValue);
    }

    public void clickOnLoginButton(){
        page.waitForSelector(loginBtn, (new Page.WaitForSelectorOptions().setTimeout(30000))).isVisible();
        page.locator(loginBtn).click();
    }
}
