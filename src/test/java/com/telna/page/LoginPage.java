package com.telna.page;

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
        page.locator(userName).fill(usernameValue);
    }

    public void enterPassword(String passwordValue){
        page.locator(password).fill(passwordValue);
    }

    public void clickOnLoginButton(){
        page.locator(loginBtn).click();
    }
}
