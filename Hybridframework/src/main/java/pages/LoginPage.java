package pages;

import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import config.ConfigReader;
import driver.DriverManager;
import utils.ExcelUtil;
import utils.LoggerUtil;

public class LoginPage extends BasePage {

    // Locators   
	private final By iconAccountListArrow = By.xpath("//div[@id=\"nav-link-accountList\"]//button[@class=\"nav-flyout-button nav-icon nav-arrow\"]");
	private final By btnSignIn = By.xpath("//div[@class=\"nav-template nav-flyout-content\"]//div[@id=\"nav-flyout-ya-signin\"]");
	
	private final By txtUsername = By.xpath("//div[@id=\"claim-input-container\"]//input[@id=\"ap_email_login\"]");
	
	private final By btnContinue = By.xpath("//span[@id=\"continue\"]");
	
	private final By txtPassword = By.xpath("//div[@class=\"a-section a-spacing-large\"]//input[@id=\"ap_password\"]");
    
	private final By btnSignInSubmit = By.xpath("//div[@class=\"a-section\"]//span[@id=\"auth-signin-button\"]");
    
	private final By errorInvalidUserMsg = By.xpath("//div[@id=\"invalid-email-alert\"]//div[@class=\"a-alert-content\"]");
	private final By errorInvalidPaswrdMsg = By.xpath("//div[@id=\"authportal-center-section\"]//div[@class=\"a-alert-content\"][1]");
	
	private final By errorEmptyUserMsg = By.xpath("//div[@id=\"empty-claim-alert\"]//div[@class=\"a-alert-content\"]");
	private final By errorEmptyPaswrdMsg = By.xpath("//div[@id=\"auth-password-missing-alert\"]//div[@class=\"a-alert-content\"]");
	//private final By lnkSignout = By.xpath("//div[@id=\"nav-flyout-anchor\"]//a[@id=\"nav-item-signout\"]");
	private final By lnkSignout = By.linkText("Sign Out");
	private final By lnkHelloSignin = By.xpath("//div[@id=\"nav-link-accountList\"]//span[text()='Hello, sign in']");
	
	
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void clickIconAccountListArrow() {
    	click(iconAccountListArrow);
    	LoggerUtil.pass("AccountListArrow Icon Clicked.");
    }
    public void clickBtnSignIn() {
        click(btnSignIn);
        LoggerUtil.pass("SignIn Button Clicked.");
    }
    public void enterUsername(String username) {
        sendKeys(txtUsername, username);
        LoggerUtil.pass("Username entered: " +username);
    }
    public void clickBtnContinue() {
        click(btnContinue);
    }
    public void enterPassword(String password) {
        sendKeys(txtPassword, password);
        LoggerUtil.pass("The Password entered.");
    }
    public void clickBtnSignInSubmit() {
        click(btnSignInSubmit);
        LoggerUtil.pass("SignInSubmit Button Clicked.");
    }
    public String getErrorInvalidUserMsg() {    	
        return getText(errorInvalidUserMsg);
        
    }
    public String getErrorInvalidPasswrdMsg() {
        return getText(errorInvalidPaswrdMsg);
    }
    public String getEmptyUserMsg() {
		return getText(errorEmptyUserMsg);
	}
    public String getEmptyPaswrdMsg() {
		return getText(errorEmptyPaswrdMsg);
	}
    
    public void clickLnkSignout() {
        click(lnkSignout);
        LoggerUtil.pass("Signout link Clicked.");
    }
    
    public void isLoginUsernameDisplayed() {
    	isDisplayed(txtUsername);
    	LoggerUtil.pass("Login Username field is displayed.");
    }
    public boolean loginToApplication() {
   
    	clickIconAccountListArrow();
    	clickBtnSignIn();
    	enterUsername(ConfigReader.getProperty("username"));
    	clickBtnContinue();
    	enterPassword(ConfigReader.getProperty("password"));
    	clickBtnSignInSubmit();
    	String dashboardTitleActual =  getPageTitle();      
        if(dashboardTitleActual.contentEquals(ConfigReader.getProperty("dashboardTitle"))) {
        	System.out.println("Login Successfull.");  
        	return true;
        }else {
        	System.out.println("Unable to login."); 
        	return false;
        }
    }
    
 
  
}
