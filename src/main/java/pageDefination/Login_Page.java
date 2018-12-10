package pageDefination;

import init.util.BrowserAction;
import objectRepo.LoginObject;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Login_Page extends BrowserAction implements LoginObject{
	
	public void loginName(WebDriver driver,String username) throws Exception{
		enterText(driver,txtLogin , username);
	}
	
	public  void loginPassword(WebDriver driver,String password) throws Exception{
		enterText(driver, txtPassword_id, password);
	}
	
	public  void loginClickButton(WebDriver driver) throws Exception{
		clickButton(driver, loginButton_id);
	}
	
	public boolean verifyErrMessage(WebDriver driver) throws Exception{
		return verifyElementDisplayed(driver, invaliMsg_id);
	}
	
	public boolean verifyLoginButton(WebDriver driver) throws Exception{
		return verifyElementDisplayed(driver, loginButton_id);
	}
}
