package com.alamukannan.empmanagement.controllers;

import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    static final Long EMPLOYEE_IDENTIFIER1 = 1L;

    static final String FIRST_NAME = "alamu";
    static final String LAST_NAME = "khanna";
    static final String EMAIL = "abc@gmail.com";
    EmployeeController employeeController;
    @Mock
    EmployeeService employeeService;
    @Mock
    BindingResult bindingResult;
    MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();


    // Runs before every test execution
    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController(employeeService);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void getIndexPage() throws Exception {
        // When
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    @DisplayName("test creating new employee")
    void saveEmployeeTest() throws NoSuchMethodException {

        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setFirstName(FIRST_NAME);
        employeeDTO.setLastName(LAST_NAME);
        employeeDTO.setEmail(EMAIL);

        given(employeeService.createNewEmployee(any(EmployeeDTO.class))).willReturn(employeeDTO);

        // When
        ResponseEntity<Object> response = employeeController.createNewEmployee(employeeDTO, bindingResult);

        // Then
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeDTO.getClass().getDeclaredMethod("getFirstName"), Objects.requireNonNull(response.getBody().getClass().getDeclaredMethod("getFirstName")));
        then(employeeService).should().createNewEmployee(any(EmployeeDTO.class));
        then(employeeService).shouldHaveNoMoreInteractions();

    }

    @Test
    @DisplayName("test creating new employee will return ok")
    void createNewProjectStatusIsOK() throws Exception {
        //        given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setFirstName("alamuKhannan");
        employeeDTO.setEmail(EMAIL);

        given(employeeService.createNewEmployee(any(EmployeeDTO.class))).willReturn(employeeDTO);

        //        When
        mockMvc.perform(post("/new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDTO))).andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()).andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists()).andExpect(MockMvcResultMatchers.jsonPath("$.email").exists()).andExpect(jsonPath("$.id", equalTo(1))).andExpect(jsonPath("$.firstName", equalTo("alamuKhannan"))).andExpect(jsonPath("$.lastName", equalTo(null))).andExpect(jsonPath("$.email", equalTo(EMAIL)));
    }

    @Test()
    @DisplayName("Creating new employee should return 400")
    void createNewProjectStatusIs400() throws Exception {
        //        given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("abc");

        //        When
        mockMvc.perform(post("/new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDTO))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.firstName", Is.is("name should be greater than 4 letters"))).andExpect(jsonPath("$.email", Is.is("Email of the employee shouldn't be empty")));
    }

    @Test
    void createNewProjectStatusIs400withEmptyEmployee() throws Exception {
//        given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);


//        When
        mockMvc.perform(post("/new").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDTO))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.firstName", Is.is("Name of the employee shouldn't be empty"))).andExpect(jsonPath("$.email", Is.is("Email of the employee shouldn't be empty")));

    }


    @Test
    @DisplayName("test getAllEmployees")
    void getAllEmployees() {
        // Given
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setFirstName(FIRST_NAME);
        employeeDTO.setEmail(EMAIL);
        employeeDTOS.add(employeeDTO);

        given(employeeService.getAllEmployees()).willReturn(employeeDTOS);

        // When
        ResponseEntity<List<EmployeeDTO>> returnedEmp = employeeController.getAllEmployees();

        //Then
        assertNotNull(returnedEmp);
        assertEquals(HttpStatus.OK, returnedEmp.getStatusCode());
        assertEquals(1, Objects.requireNonNull(returnedEmp.getBody()).size());
        then(employeeService).should().getAllEmployees();
        then(employeeService).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("test getAllEmployees with 200")
    void getAllEmployeesWithOK() throws Exception {
        // Given
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setFirstName(FIRST_NAME);
        employeeDTO.setEmail(EMAIL);
        employeeDTOS.add(employeeDTO);

        given(employeeService.getAllEmployees()).willReturn(employeeDTOS);
        // Then
        MockHttpServletResponse employees = mockMvc.perform(get("/all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse();

        // Then
        assertNotNull(employees);
        assertNotNull(employees.getContentAsString());

    }

    @Test
    void updateEmployee() {
        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setLastName("modifiedLast");

        EmployeeDTO returnedEmp = new EmployeeDTO();
        returnedEmp.setId(employeeDTO.getId());
        returnedEmp.setLastName(employeeDTO.getLastName());

        given(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class))).willReturn(returnedEmp);

        // When
        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(1L, employeeDTO);

        //Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        assertEquals("modifiedLast", response.getBody().getLastName());
        assertNotNull(returnedEmp.getId());
        assertNotNull(response.getBody().getId());
        assertNull(response.getBody().getFirstName());
        assertNull(response.getBody().getEmail());
    }

    @Test
    void updateEmployeeWithOk() throws Exception {
        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setLastName("modifiedLast");
        employeeDTO.setFirstName(FIRST_NAME);
        employeeDTO.setEmail(EMAIL);

        EmployeeDTO returnedEmp = new EmployeeDTO();
        returnedEmp.setId(employeeDTO.getId());
        returnedEmp.setLastName(employeeDTO.getLastName());
        returnedEmp.setFirstName(employeeDTO.getFirstName());
        returnedEmp.setEmail(employeeDTO.getEmail());

        given(employeeService.updateEmployee(anyLong(), any(EmployeeDTO.class))).willReturn(returnedEmp);

        //When
        mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDTO))).andExpect(status().isOk()).andExpect(jsonPath("$.lastName", equalTo("modifiedLast"))).andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME))).andExpect(jsonPath("$.email", equalTo(EMAIL))).andExpect(jsonPath("$.id", equalTo(1)));

        //Then
        then(employeeService).should().updateEmployee(anyLong(), any(EmployeeDTO.class));
        then(employeeService).shouldHaveNoMoreInteractions();

    }

    @Test
    void updateEmployeeWith400() throws Exception {
        //Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setLastName("modifiedLast");

        //When
        mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDTO))).andExpect(status().isBadRequest());
    }

    @Test
    void updateEmployeeWith400AndNotFoundException() throws Exception {
        //Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setLastName("modifiedLast");

        //When
        mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDTO))).andExpect(status().isBadRequest());

    }

    @Test
    void deleteEmployee() {
        // When
        employeeController.deleteEmployee(EMPLOYEE_IDENTIFIER1);

        //Then
        then(employeeService).should().deleteEmployee(any(Long.class));
        then(employeeService).shouldHaveNoMoreInteractions();


    }

    @Test
    void deleteEmployeeStatusOK() throws Exception {
//        Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setLastName("modifiedLast");
        employeeDTO.setFirstName(FIRST_NAME);
        employeeDTO.setEmail(EMAIL);
//        when
        mockMvc.perform(delete("/employees/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeDTO))).andExpect(status().isOk()).andExpect(jsonPath("$", equalTo("Employee with ID: 1 has been deleted successfully")));

    }

}
