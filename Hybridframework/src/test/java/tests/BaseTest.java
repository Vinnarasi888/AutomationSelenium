package tests;

import config.ConfigReader;
import driver.DriverManager;
import utils.ExcelUtil;
import utils.TestListener;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {
	
	@BeforeMethod
    public void setUp() {
        DriverManager.initDriver();        
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(ConfigReader.getProperty("url"));  
        DriverManager.getDriver().manage().deleteAllCookies();	    
	  	DriverManager.getDriver().navigate().refresh();

    }

   
    @AfterMethod(alwaysRun = true)
    public void tearDown() {    	
        DriverManager.quitDriver();
    }
	
	/*@BeforeTest
	public void setUp() {
		DriverManager.initDriver();  
		DriverManager.getDriver().manage().window().maximize();
	    DriverManager.getDriver().get(ConfigReader.getProperty("url"));   
	    DriverManager.getDriver().manage().deleteAllCookies();	    
	  	DriverManager.getDriver().navigate().refresh();
	}

	@AfterTest
	public void tearDown() {    	
	    DriverManager.quitDriver();
	}
   */
}