package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import config.ConfigReader;
import driver.DriverManager;
import pages.LoginPage;
import pages.ProductSearchPage;
import utils.ExcelUtil;
import utils.LoggerUtil;

public class ProductSearchTest extends BaseTest{
	
	
	@Test(priority = 1)
	public void Search_001_VerifySearchProduct() {
		try {
		LoginPage loginPage = new LoginPage(DriverManager.getDriver());        
		 Boolean flagVal = loginPage.loginToApplication();
		 if(flagVal) {
			 LoggerUtil.info("Starting test case execution: " + ExcelUtil.getCellData("ProductSearchData", "Search_001", "TestcaseID"));
		        String productTextExcl = ExcelUtil.getCellData("ProductSearchData", "Search_001", "ProductText");        
		        String expctedResltExcl = ExcelUtil.getCellData("ProductSearchData", "Search_001", "ExpectedResult");       
		        ProductSearchPage ProSearchPage = new ProductSearchPage(DriverManager.getDriver());
		        
		        ProSearchPage.enterTxtSearchBar(productTextExcl);
		        ProSearchPage.clickIconSubmitSearch();
		        ProSearchPage.verifySearchResultsDisplayed(expctedResltExcl);
		        LoggerUtil.pass("Test Case " + ExcelUtil.getCellData("ProductSearchData", "Search_001", "TestcaseID") + " Search Products displayed successfully." +expctedResltExcl);
			
		 }else {
		     
              LoggerUtil.error("Error in loginToApplication", null);  
		}
	
		} catch (AssertionError e) {    	 
	         LoggerUtil.error(e.getMessage(), e);
	         throw e;
	   }
    }
	
	
	
}
