package com.alamukannan.empmanagement.repository;

import com.alamukannan.AbstractContainerBaseTest;
import com.alamukannan.empmanagement.domain.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("dev")     // This required as we are testing the production DB.
class EmployeeRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
     void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        // given
        Employee employee = new Employee();
        employee.setFirstName("Ramu");
        employee.setLastName("somu");
        employee.setEmail("abc@gmail.com");

        // when
        Employee employee1 = employeeRepository.save(employee);

        // then
        Assertions.assertNotNull(employee1);
        Assertions.assertNotNull(employee1.getId());

    }
    @Test
   void givenEmployee_whenFindByFirstName_thenReturnSavedEmployee(){

        // given
        Employee employee = new Employee();
        employee.setFirstName("Ramu");
        employee.setLastName("somu");
        employee.setEmail("abc@gmail.com");
         employeeRepository.save(employee);

        // when
        Employee employee1 = employeeRepository.findByFirstName(employee.getFirstName());

        // then
        Assertions.assertNotNull(employee1);
        assertEquals(employee.getFirstName(),employee1.getFirstName());
        assertEquals(employee.getLastName(),employee1.getLastName());
        assertEquals(employee.getEmail(),employee1.getEmail());
    }


}