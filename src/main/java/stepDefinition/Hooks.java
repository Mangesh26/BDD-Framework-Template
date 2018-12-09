package stepDefinition;

import com.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import init.util.DriverAction;

public class Hooks {

	@Before
	 public void beforeScenario(Scenario scenario) {
	     Reporter.assignAuthor("Mangesh Kulkarni");
	     System.out.println(scenario.getName());
	 }
	
	/*@After
	public static void afterScenario(){
		DriverAction.releaseDriver(DriverAction.driver);
	}*/
}
