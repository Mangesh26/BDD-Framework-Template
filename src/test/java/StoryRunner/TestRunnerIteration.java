package StoryRunner;

import org.junit.runner.RunWith;

import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;

import cucumber.api.CucumberOptions;

@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(
		jsonReport = "target/cucumber.json",
        retryCount = 2,
        detailedReport = true,
        detailedAggregatedReport = false,
        overviewReport = true,
        //coverageReport = true,
        jsonUsageReport = "target/cucumber-usage.json",
        usageReport = true,
        toPDF = true,
        outputFolder = "target"
        )
@CucumberOptions(
		plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        features = { "src/main/resources/Feature/Login.feature" },
        glue = { "stepDefinition" }
)
public class TestRunnerIteration {
}