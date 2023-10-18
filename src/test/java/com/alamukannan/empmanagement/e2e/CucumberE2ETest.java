package com.alamukannan.empmanagement.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources", glue = "com.alamukannan.empmanagement.e2e")
public class CucumberE2ETest {

}
