package com.alamukannan.empmanagement.e2e;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features")
public class TestRunnerE2E {
}
