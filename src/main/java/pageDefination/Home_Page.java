package pageDefination;

import init.util.BrowserAction;
import objectRepo.HomeObject;

import org.openqa.selenium.WebDriver;

public class Home_Page extends BrowserAction implements HomeObject{
	
	public  boolean verifyImgHeader(WebDriver driver) throws Exception
	{
		return verifyElementDisplayed(driver,imgHeader_xpath);
	}
	
}
