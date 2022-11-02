package com.ti.testcases;

import com.ti.data.MobileEmulators;
import com.ti.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

public class ResponsiveTesting {
    WebDriver driver;
    LoginPage loginPage;

    Map<String,String> deviceMobEmu;
    String baseURL = "https://demosite.titaniuminstitute.com.mx/wp-admin/admin.php?page=sch-dashboard";
    String userName = "admin";
    String password = "G3-ySzY%";

    @BeforeTest
    void setUp() {
        WebDriverManager.chromedriver().setup();
        deviceMobEmu = new HashMap<>();
    }

    @AfterMethod
    void closeBrowser() {
        driver.quit();
    }

    @AfterTest
    void cleanMap(){
        deviceMobEmu.clear();
    }

    @Test(enabled = false, dataProviderClass = MobileEmulators.class, dataProvider = "mobileEmulations")
    public void responsiveTest(String emulation){
        deviceMobEmu.put("deviceName", emulation);
        ChromeOptions chromeOpt = new ChromeOptions();
        chromeOpt.setExperimentalOption("mobileEmulation", deviceMobEmu);

        driver = new ChromeDriver(chromeOpt);
        driver.get(baseURL);
        loginPage = new LoginPage(driver);
        loginPage.loginAs(userName).withPassword(password).login();
    }

    @Test(dataProviderClass = MobileEmulators.class, dataProvider = "mobileEmulationsDimensions")
    public void responsiveTest(String emulation, int w, int h){
        deviceMobEmu.put("deviceName", emulation);
        ChromeOptions chromeOpt = new ChromeOptions();
        chromeOpt.setExperimentalOption("mobileEmulation", deviceMobEmu);

        driver = new ChromeDriver(chromeOpt);
        Dimension dimension = new Dimension(w,h);
        driver.manage().window().setSize(dimension);

        driver.get(baseURL);
        loginPage = new LoginPage(driver);
        loginPage.loginAs(userName).withPassword(password).login();
    }
}
