package tests;

import config.ConfigReader;
import driver.DriverManager;
import utils.TestListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        DriverManager.getDriver().manage().deleteAllCookies(); 
        DriverManager.getDriver().navigate().refresh();
        DriverManager.getDriver().get(ConfigReader.getProperty("url"));     
        
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}