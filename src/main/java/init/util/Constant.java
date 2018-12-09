package init.util;

import java.io.IOException;
import org.openqa.selenium.WebDriver;

public class Constant {

	public static final String URL_app = "https://www.facebook.com/";
	 
    public static final String Username = "";

    public static final String Password = "";
    
    public static WebDriver driver=null;
    
    
    public static WebDriver getDriver()
    {
		return driver;
    }
    
}
