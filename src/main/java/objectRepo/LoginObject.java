package objectRepo;

import org.openqa.selenium.By;

public interface LoginObject {

	By txtLogin = By.id("email");
	By txtPassword_id = By.id("pass");
	By invaliMsg_id = By.id("lblInvalid");
	By loginButton_id = By.id("loginbutton1");
	
}
