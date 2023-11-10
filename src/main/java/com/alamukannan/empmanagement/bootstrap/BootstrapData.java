package com.alamukannan.empmanagement.bootstrap;

import com.alamukannan.empmanagement.domain.Employee;
import com.alamukannan.empmanagement.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"default"})

public class BootstrapData implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final Logger log = LogManager.getLogger(BootstrapData.class);

    public BootstrapData(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        log.debug("Loading BootStrap Data -----");
        loadEmployees();
        log.info("Loading BootStrap data has been finished....");
    }

    private void loadEmployees(){

        Employee employee = new Employee();
        employee.setLastName("Somu");
        employee.setFirstName("ramu");
        employee.setEmail("ramu@gmail.com");

        Employee employee1 = new Employee();
        employee1.setEmail("alamu@gmail.com");
        employee1.setFirstName("alamu");
        employee1.setLastName("khannan");

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

    }

}
