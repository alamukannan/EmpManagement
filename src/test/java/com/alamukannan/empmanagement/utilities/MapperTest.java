package com.alamukannan.empmanagement.utilities;

import com.alamukannan.empmanagement.domain.Employee;
import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapperTest {
    static final Long EMPLOYEE_ID1 = 1L;
    static final Long EMPLOYEE_ID2 = 2L;


    @Test
    @DisplayName("Get employee when given EmployeeDTO")
    void getEmployee() {
        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_ID1);
        employeeDTO.setFirstName("alamu");
        employeeDTO.setLastName("khanna");
        employeeDTO.setEmail("abc@gmail.com");

        //When
        Employee employee = Mapper.getEmployee(employeeDTO);
        //Then
        assertEquals(EMPLOYEE_ID1, employee.getId());
        assertEquals("alamu", employee.getFirstName());
        assertEquals("khanna", employee.getLastName());
        assertNotNull(employee.getEmail());
        assertEquals("abc@gmail.com", employee.getEmail());

    }

    @Test
    @DisplayName("Get EmployeeDTO when given Employee")
    void getEmployeeDTO() {

        // Given
        Employee employee = new Employee();
        employee.setId(EMPLOYEE_ID2);
        employee.setFirstName("ramu");
        employee.setLastName("somu");
        employee.setEmail("ramusomu@gmail.com");


        // When
        EmployeeDTO employeeDTO = Mapper.getEmployeeDTO(employee);

        // Then
        assertEquals(EMPLOYEE_ID2, employeeDTO.getId());
        assertEquals("ramu", employeeDTO.getFirstName());
        assertEquals("somu", employeeDTO.getLastName());
        assertNotNull(employeeDTO.getEmail());
        assertEquals("ramusomu@gmail.com", employeeDTO.getEmail());

    }
}