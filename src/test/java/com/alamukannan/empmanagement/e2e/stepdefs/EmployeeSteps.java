package com.alamukannan.empmanagement.e2e.stepdefs;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.alamukannan.empmanagement.domain.Employee;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import java.util.List;

/**
 * Step Definition Class for Employee.
 *
 * <p>Uses Java Lambda style step definitions so that we don't need to worry
 * about method naming for each step definition</p>
 */
public class EmployeeSteps extends AbstractSteps implements En {

    public EmployeeSteps() {

        Given("user wants to create/update a employee with the following attributes", (DataTable employeeDt) -> {
            List<Employee> employees = employeeDt.asList(Employee.class);
            Employee employee = employees.get(0);

            testContext().setPayload(employee);
        });

        When("user saves the new employee {string}", (String testContext) -> {
            String createEmployeeUrl = "/new";

            // AbstractSteps class makes the POST call and stores response in TestContext
            executePost(createEmployeeUrl);
        });
        /**
         * This can be moved to a Class named 'CommonSteps.java so that it can be reused.
         */
        Then("the save {string}", (String expectedResult) -> {
            Response response = testContext().getResponse();

            switch (expectedResult) {
                case "IS SUCCESSFUL":
                    assertThat(response.statusCode()).isIn(200, 201);
                    break;
                case "FAILS":
                    assertThat(response.statusCode()).isBetween(400, 504);
                    break;
                default:
                    fail("Unexpected error");
            }
        });

    }

}

