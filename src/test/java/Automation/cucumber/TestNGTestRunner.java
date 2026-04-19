package Automation.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/Automation/cucumber",
    glue = "Automation.stepDefinitions",
    monochrome = true,
    tags = "@WalmartTests",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json"
    }
)
public class TestNGTestRunner extends AbstractTestNGCucumberTests {
}