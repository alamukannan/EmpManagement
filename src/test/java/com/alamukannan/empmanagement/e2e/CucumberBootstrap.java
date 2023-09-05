package com.alamukannan.empmanagement.e2e;

import com.alamukannan.AbstractContainerBaseTest;
import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;





@Cucumber
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"dev"})
public class CucumberBootstrap extends AbstractContainerBaseTest {

}
