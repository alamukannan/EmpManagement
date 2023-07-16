package com.alamukannan.empmanagement.e2e;

import com.alamukannan.empmanagement.EmpManagementApplication;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Class to use spring application context while running cucumber
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = EmpManagementApplication.class, loader = SpringBootContextLoader.class)
public class CucumberSpringContextConfiguration {
    @Before
    public void setUp() {
        System.out.println("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}
