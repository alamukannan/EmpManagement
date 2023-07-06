package com.alamukannan.empmanagement.services;

import com.alamukannan.empmanagement.domain.Employee;
import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.repository.EmployeeRepository;
import com.alamukannan.empmanagement.utilities.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    static final Long EMPLOYEE_IDENTIFIER1 =1L;
    @Mock
    EmployeeRepository employeeRepository;

    EmployeeService employeeService;


    @BeforeEach
    void init(){
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    @DisplayName("test Create new employee in service layer")
    void createNewEmployee() {
        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setFirstName("alamu");
        employeeDTO.setLastName("khanna");
        employeeDTO.setEmail("abc@gmail.com");
        Employee employee= Mapper.getEmployee(employeeDTO);
        given(employeeRepository.save(any(Employee.class))).willReturn(employee);

        // When
        EmployeeDTO savedEmployee=  employeeService.createNewEmployee(employeeDTO);
        // Then
        assertNotNull(savedEmployee);
        assertEquals(EMPLOYEE_IDENTIFIER1,savedEmployee.getId());
        assertEquals("khanna",savedEmployee.getLastName());
        then(employeeRepository).should().save(any(Employee.class));
        then(employeeRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("test return zero employee")
    void getAllEmployeesWithEmptyList() {
        // Given
        List<Employee> employees = new ArrayList<>();
        given(employeeRepository.findAll()).willReturn(employees);

        // When
        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();

      // Then
        assertEquals(0,employeeDTOS.size());
    }

    @Test
    void getAllEmployees(){
        //Given
         Employee employee = new Employee();
         employee.setEmail("abc@gmail.com");
         employee.setId(1L);
         employee.setFirstName("alamu");
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        given(employeeRepository.findAll()).willReturn(employees);

        // When
        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();

        //Then
        assertEquals(1,employeeDTOS.size());
        assertNull(employeeDTOS.get(0).getLastName());
        assertEquals(1L,employeeDTOS.get(0).getId());
        assertEquals(13,employeeDTOS.get(0).getEmail().length());
        assertEquals("alamu",employeeDTOS.get(0).getFirstName());
    }

}