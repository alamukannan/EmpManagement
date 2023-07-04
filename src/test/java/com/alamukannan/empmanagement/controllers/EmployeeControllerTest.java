package com.alamukannan.empmanagement.controllers;


import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.services.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
  class EmployeeControllerTest {


     static final Long EMPLOYEE_IDENTIFIER1 =1L;
     static final Long EMPLOYEE_IDENTIFIER2 = 2L;


    EmployeeController employeeController;
    @Mock
    EmployeeService employeeService;


    MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();


    // Runs before every test execution
    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController(employeeService);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    @DisplayName("test creating new employee")
    void saveEmployeeTest(){

        // Given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setFirstName("alamu");
        employeeDTO.setLastName("khanna");

        given(employeeService.createNewEmployee(any(EmployeeDTO.class))).willReturn(employeeDTO);

        // When
       ResponseEntity<EmployeeDTO> response = employeeController.createNewEmployee(employeeDTO);

       // Then
      assertNotNull(response.getBody());

      assertEquals(HttpStatus.CREATED,response.getStatusCode());
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
        employeeDTO.setEmail("almu.hana@gmail.com");

        given(employeeService.createNewEmployee(any(EmployeeDTO.class))).willReturn(employeeDTO);

       //        When
       mockMvc.perform(post("/api/v1/new")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(mapper.writeValueAsString(employeeDTO)))
               .andExpect(status().isCreated())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
               .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
               .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
               .andExpect(jsonPath("$.id",equalTo(1)))
               .andExpect(jsonPath("$.firstName",equalTo("alamuKhannan")))
               .andExpect(jsonPath("$.lastName",equalTo(null)))
               .andExpect(jsonPath("$.email",equalTo("almu.hana@gmail.com")))
       ;
    }

    @Test()
    @DisplayName("Creating new employee should return 400")
    void createNewProjectStatusIs400() throws Exception {
        //        given
        EmployeeDTO employeeDTO = new EmployeeDTO();

        //        When
       mockMvc.perform(post("/api/v1/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isBadRequest());
    }





}
