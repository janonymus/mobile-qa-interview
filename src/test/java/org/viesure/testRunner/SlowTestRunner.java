package org.viesure.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
        features = {"src/main/resources/pages"},
        glue = "steps",
        tags = "@slow"
)
public class SlowTestRunner extends AbstractTestNGCucumberTests {

}
