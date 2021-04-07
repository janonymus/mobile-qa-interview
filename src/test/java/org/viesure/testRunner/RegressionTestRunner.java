package org.viesure.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/main/resources/pages"},
        glue = "steps",
        tags = "@regression"
)
public class RegressionTestRunner extends AbstractTestNGCucumberTests {

}
