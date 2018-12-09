package StoryRunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import com.github.mkolisnyk.cucumber.runner.*;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "@target/rerun.txt", //Cucumber picks the failed scenarios from this file 
    glue = {"stepDefinition"},
	plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/re-runReport.html",
			"rerun:target/rerun.txt"
			},
	monochrome = true
  )
public class FailedScenarios {

}
