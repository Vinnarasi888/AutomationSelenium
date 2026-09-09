package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.reporters.jq.BasePanel;

import config.ConfigReader;
import driver.DriverManager;
import utils.LoggerUtil;

public class ProductSearchPage extends BasePage{

	public ProductSearchPage(WebDriver driver) {
		super(driver);
		
	}

	private final By txtSearchBar = By.xpath("//form[@id=\"nav-search-bar-form\"]//div[@class=\"nav-search-field \"]//input[@id=\"twotabsearchtextbox\"]");
	private final By iconSubmitSearch = By.xpath("//*[@id=\"nav-search-submit-button\"]");
	private final By lstSearchResults1 = By.xpath("//div[@role='listitem']//h2[@class='a-size-mini s-line-clamp-1'][1]");
	  
	public void enterTxtSearchBar(String searchProduct) {
		click(txtSearchBar);
        sendKeys(txtSearchBar, searchProduct);
        LoggerUtil.pass("Search Product entered: " +searchProduct);
    }
	public void clickIconSubmitSearch() {
		click(iconSubmitSearch);
	}
	public void verifySearchResultsDisplayed(String expectedProduct) {
		isDisplayed(lstSearchResults1);
		WebElement productTitles = driver.findElement(lstSearchResults1); 
		String actualTitle = productTitles.getText();
		//boolean isProductFound = false;
		if(actualTitle.contentEquals(expectedProduct) ) {			
			System.out.println("Matched Product Found: " + actualTitle);
			LoggerUtil.pass("The Search Product is displayed: "+expectedProduct);            
		}
		
	}
}
