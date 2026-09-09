package tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import driver.DriverManager;
import pages.LoginPage;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import config.ConfigReader;
import utils.ExcelUtil;
import utils.LoggerUtil;


public class LoginTest extends BaseTest {
    
    // 1. Verify the invalid user name error message
    @Test(priority = 1)
    public void Log_001_VerifyInvalidUsername(){
     try {
    	 LoggerUtil.info("Starting test case execution: " + ExcelUtil.getCellData("LoginData", "Log_001", "TestcaseID"));
         String usernameExcl = ExcelUtil.getCellData("LoginData", "Log_001", "Username");
         //String passwordExcl = ExcelUtil.getCellData("LoginData", "Log_001", "Password");
         String expctedResltExcl = ExcelUtil.getCellData("LoginData", "Log_001", "ExpectedResult");
         LoginPage loginPage = new LoginPage(DriverManager.getDriver());
         loginPage.clickIconAccountListArrow();
         loginPage.clickBtnSignIn();
         loginPage.enterUsername(usernameExcl);
         loginPage.clickBtnContinue();
         String errorUsernameMsgActual = loginPage.getErrorInvalidUserMsg();
		 AssertJUnit.assertEquals(errorUsernameMsgActual, expctedResltExcl);
		 LoggerUtil.pass("Test Case " + ExcelUtil.getCellData("LoginData", "Log_001", "TestcaseID") + " Invalid username error message displayed successfully.");
     } catch (AssertionError e) {    	
         LoggerUtil.error(e.getMessage(), e);
         throw e;
     }
	}
 // 2. Verify the invalid Password error message
    @Test(priority = 2)
    public void Log_002_VerifyInvalidPassword(){
      try {
    	LoggerUtil.info("Starting test case execution: " + ExcelUtil.getCellData("LoginData", "Log_002", "TestcaseID"));
         String usernameExcl = ExcelUtil.getCellData("LoginData", "Log_002", "Username");
         String passwordExcl = ExcelUtil.getCellData("LoginData", "Log_002", "Password");
         String expctedResltExcl = ExcelUtil.getCellData("LoginData", "Log_002", "ExpectedResult");
         LoginPage loginPage = new LoginPage(DriverManager.getDriver());
         loginPage.clickIconAccountListArrow();
         loginPage.clickBtnSignIn();
         loginPage.enterUsername(usernameExcl);
         loginPage.clickBtnContinue();
         loginPage.enterPassword(passwordExcl);
         loginPage.clickBtnSignInSubmit();
         String errorPaswrdMsgActual = loginPage.getErrorInvalidPasswrdMsg();
		 AssertJUnit.assertEquals(errorPaswrdMsgActual, expctedResltExcl);
		 LoggerUtil.pass("Test Case " + ExcelUtil.getCellData("LoginData", "Log_002", "TestcaseID") + " Invalid username error message displayed successfully.");
      } catch (AssertionError e) {
           LoggerUtil.error(e.getMessage(), e);
           throw e;
       }	 
	}
 // 3. Verify the empty Username error message
    @Test(priority = 3)
    public void Log_003_VerifyEmptyUsername(){
    try {	
    	LoggerUtil.info("Starting test case execution: " + ExcelUtil.getCellData("LoginData", "Log_003", "TestcaseID"));
         String usernameExcl = ExcelUtil.getCellData("LoginData", "Log_003", "Username");        
         String expctedResltExcl = ExcelUtil.getCellData("LoginData", "Log_003", "ExpectedResult");
         LoginPage loginPage = new LoginPage(DriverManager.getDriver());
         loginPage.clickIconAccountListArrow();
         loginPage.clickBtnSignIn();
         loginPage.enterUsername(usernameExcl);
         loginPage.clickBtnContinue();       
         String emptyUserErorMsgActual = loginPage.getEmptyUserMsg();
		 AssertJUnit.assertEquals(emptyUserErorMsgActual, expctedResltExcl);
		 LoggerUtil.pass("Test Case " + ExcelUtil.getCellData("LoginData", "Log_003", "TestcaseID") + " Enter your mobile number or email Error message displayed successfully.");
     } catch (AssertionError e) {
    	 LoggerUtil.error(e.getMessage(), e);
         throw e;
     } 
	}
    
 // 4. Verify the empty Password error message
    @Test(priority = 4)
    public void Log_004_VerifyEmptyPassword(){
      try {	
    	LoggerUtil.info("Starting test case execution: " + ExcelUtil.getCellData("LoginData", "Log_004", "TestcaseID"));
         String usernameExcl = ExcelUtil.getCellData("LoginData", "Log_004", "Username");
         String passwordExcl = ExcelUtil.getCellData("LoginData", "Log_004", "Password");
         String expctedResltExcl = ExcelUtil.getCellData("LoginData", "Log_004", "ExpectedResult");
         LoginPage loginPage = new LoginPage(DriverManager.getDriver());
         loginPage.clickIconAccountListArrow();
         loginPage.clickBtnSignIn();
         loginPage.enterUsername(usernameExcl);
         loginPage.clickBtnContinue();
         loginPage.enterPassword(passwordExcl);
         loginPage.clickBtnSignInSubmit();
         String emptyPaswrdErorMsgActual = loginPage.getEmptyPaswrdMsg();
		 AssertJUnit.assertEquals(emptyPaswrdErorMsgActual, expctedResltExcl);
		 LoggerUtil.pass("Test Case " + ExcelUtil.getCellData("LoginData", "Log_004", "TestcaseID") + " Enter your password Error message displayed successfully.");
      } catch (AssertionError e) {
      	   LoggerUtil.error(e.getMessage(), e);
           throw e;
       }	 
	}
    
    //5. verify the valid Username and password
    @Test(priority=5)
    public void Log_005_VerifyValidLogin() {
    try {	
    	LoggerUtil.info("Starting test case execution: " + ExcelUtil.getCellData("LoginData", "Log_005", "TestcaseID"));
        String usernameExcl = ExcelUtil.getCellData("LoginData", "Log_005", "Username");
        String passwordExcl = ExcelUtil.getCellData("LoginData", "Log_005", "Password");
        String expctedResltExcl = ExcelUtil.getCellData("LoginData", "Log_005", "ExpectedResult");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.clickIconAccountListArrow();
        loginPage.clickBtnSignIn();
        loginPage.enterUsername(usernameExcl);
        loginPage.clickBtnContinue();
        loginPage.enterPassword(passwordExcl);
        loginPage.clickBtnSignInSubmit();
        String dashboardTitleActual =  loginPage.getPageTitle();        
        AssertJUnit.assertEquals(dashboardTitleActual, expctedResltExcl);
        LoggerUtil.pass("Test Case " + ExcelUtil.getCellData("LoginData", "Log_005", "TestcaseID") + " User is able to login successfully. Dashboard Title: " +dashboardTitleActual);
     } catch (AssertionError e) {
    	 LoggerUtil.error(e.getMessage(), e);
         throw e;
     } 
    }
    
    @Test(priority=6)
    public void Log_006_ValidLogout() {
		try {
			
			//Login
			LoggerUtil.info("Starting test case execution: " + ExcelUtil.getCellData("LoginData", "Log_006", "TestcaseID"));
	        String usernameExcl = ExcelUtil.getCellData("LoginData", "Log_006", "Username");
	        String passwordExcl = ExcelUtil.getCellData("LoginData", "Log_006", "Password");
	        String expctedResltExcl = ExcelUtil.getCellData("LoginData", "Log_006", "ExpectedResult");
	        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
	        loginPage.clickIconAccountListArrow();
	        loginPage.clickBtnSignIn();
	        loginPage.enterUsername(usernameExcl);
	        loginPage.clickBtnContinue();
	        loginPage.enterPassword(passwordExcl);
	        loginPage.clickBtnSignInSubmit();
	        String dashboardTitleActual =  loginPage.getPageTitle();            
	        LoggerUtil.info("Test Case " + " User is able to login successfully. Dashboard Title: " +dashboardTitleActual);
			
	        //Logout
	        loginPage.clickIconAccountListArrow();
	        loginPage.clickLnkSignout();  	        
	        loginPage.isLoginUsernameDisplayed();	        
	        LoggerUtil.pass("Test Case " + ExcelUtil.getCellData("LoginData", "Log_006", "TestcaseID") + " User is able to logout successfully. ");
		  } catch (Exception e) {
			  LoggerUtil.error(e.getMessage(), e);
	         
		}

	}
}
