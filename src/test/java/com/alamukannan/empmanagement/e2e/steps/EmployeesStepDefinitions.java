package com.alamukannan.empmanagement.e2e.steps;


import com.alamukannan.empmanagement.domain.Employee;
import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.e2e.CucumberBootstrap;
import com.alamukannan.empmanagement.repository.EmployeeRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class EmployeesStepDefinitions extends CucumberBootstrap {


    private final String SERVER_URL = "http://localhost";
    private final String THINGS_ENDPOINT = "/new";

    private final EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;

    private String EmsEndpoint() {
        return SERVER_URL + ":" + port + THINGS_ENDPOINT;
    }

    public int put(final String something) {
        return restTemplate.postForEntity(EmsEndpoint(), something, Void.class).getStatusCodeValue();
    }

    public EmployeesStepDefinitions(EmployeeRepository employeeRepository, TestRestTemplate restTemplate) {
        this.employeeRepository = employeeRepository;
        this.restTemplate = restTemplate;
    }

    @When("^user sends employee data to be created with firstname (.*), lastname (.*) and email (.*)$")
    public void when_user_request_the_create_endpoint(final String firstname, final String lastname, final String email) {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setEmail(email);
        employee.setLastName(lastname);
        employee.setFirstName(firstname);
        restTemplate.postForEntity(EmsEndpoint(), employee, Employee.class);

    }

    @Then("^user should receive the created employee$")
    public void then_user_should_receive_created_employee() {
        int size = employeeRepository.findAll().size();
        assertEquals(1, size);
    }
}
