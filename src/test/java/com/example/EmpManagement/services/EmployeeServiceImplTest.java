package com.example.EmpManagement.services;

import com.example.EmpManagement.domain.Employee;
import com.example.EmpManagement.dtos.EmployeeDTO;
import com.example.EmpManagement.repository.EmployeeRepository;
import com.example.EmpManagement.utilities.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
       // EmployeeDTO employeeDTO1 = Mapper.getEmployeeDTO(employee);

        // When
        EmployeeDTO savedEmployee=  employeeService.createNewEmployee(employeeDTO);
        // Then
        assertNotNull(savedEmployee);
        assertEquals(EMPLOYEE_IDENTIFIER1,savedEmployee.getId());
        assertEquals("khanna",savedEmployee.getLastName());
        then(employeeRepository).should().save(any(Employee.class));
        then(employeeRepository).shouldHaveNoMoreInteractions();
    }
}