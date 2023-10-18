package com.alamukannan.empmanagement.services;

import com.alamukannan.empmanagement.bootstrap.BootstrapData;
import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.exceptions.EmployeeNotFoundException;
import com.alamukannan.empmanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class EmployeeServiceImplIT {

    @Autowired
    EmployeeRepository employeeRepository;

    EmployeeService employeeService;
    static final Long EMPLOYEE_IDENTIFIER = 1L;

    @BeforeEach
    void init() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
        BootstrapData data = new BootstrapData(employeeRepository);
        data.run();
    }


    @Test
    @DisplayName("test Create new employee in service layer")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void createNewEmployee() {
        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("alamu1");
        employeeDTO.setLastName("khanna1");
        employeeDTO.setEmail("abc@gmail.com");

        // When
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(employeeDTO);
        // Then
        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getId());
        assertEquals("khanna1", savedEmployee.getLastName());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void getAllEmployees() {
        // When
        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();

        //Then
        assertEquals(2, employeeDTOS.size());
        assertNotNull(employeeDTOS.get(0).getLastName());
        assertEquals(EMPLOYEE_IDENTIFIER, employeeDTOS.get(0).getId());
        assertEquals("ramu", employeeDTOS.get(0).getFirstName());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void UpdateEmployee() {
        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(2L);
        employeeDTO.setLastName("modifiedLast");

        // When

        EmployeeDTO returnedEmp = employeeService.updateEmployee(2L, employeeDTO);

        // Then
        assertNotNull(returnedEmp);
        assertEquals("modifiedLast", returnedEmp.getLastName());
        assertNotNull(returnedEmp.getId());
        assertEquals(2, returnedEmp.getId());
        assertNull(returnedEmp.getEmail());
        assertNull(returnedEmp.getFirstName());


    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void updateEmployeeWithException() {
        //Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        //Then
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(3L, employeeDTO));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void deleteEmployee() {
//        When
        employeeService.deleteEmployee(EMPLOYEE_IDENTIFIER);
        EmployeeDTO employeeDTO = new EmployeeDTO();

//        then
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.updateEmployee(EMPLOYEE_IDENTIFIER, employeeDTO));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void deleteEmployeeWithException() {
        // Then
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(3L));

    }

}
