package com.telna.setup;

import com.telna.pages.HomePage;
import com.telna.pages.LoginPage;
import com.telna.utility.ConfigProvider;
import com.microsoft.playwright.*;
import com.telna.utility.LogMapper;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseTest {

    protected static Playwright playwright;
    protected static Browser browser;
    public Page page;
    protected BrowserContext browserContext;

    public Page getPage() {
        return page;
    }

    @BeforeClass
    public void storeStorageState() throws IOException {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        browserContext = browser.newContext();
        page = browserContext.newPage();
        LogMapper.info("Application URL : " + ConfigProvider.getAsString("application_url"));
        page.navigate(ConfigProvider.getAsString("application_url"));
        LoginPage loginPage = new LoginPage(page);
        loginPage.enterUsername(ConfigProvider.getAsString("username"));
        loginPage.enterPassword(ConfigProvider.getAsString("password"));
        loginPage.clickOnLoginButton();
        HomePage homePage = new HomePage(page);
        Assert.assertEquals("Dashboard", homePage.getActiveTabText());
        LogMapper.info("User logged in Successfully");
        Path path = Paths.get(System.getProperty("user.dir"), "data", "authState.json");
        Files.createDirectories(path.getParent());
        browserContext.storageState(new BrowserContext.StorageStateOptions().setPath(path));
        LogMapper.info("Dashboard : " + page.url());
        LogMapper.info("Storage State stored in file.");
    }

    @AfterClass
    public void cleanSetup(){
        browser.close();
        playwright.close();
    }

    @BeforeMethod
    public void setStorageState(){
        LogMapper.info("Using Storage State stored in file.");
        Path path = Paths.get(System.getProperty("user.dir"), "data", "authState.json");
        browserContext = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(path));
        page = browserContext.newPage();
    }

    @AfterMethod
    public void tearDown(){
        page.close();
        browserContext.close();
    }
}
