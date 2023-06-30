package com.alamukannan.empmanagement.controllers;


import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
  class EmployeeControllerTest {


     static final Long EMPLOYEE_IDENTIFIER1 =1L;
     static final Long EMPLOYEE_IDENTIFIER2 = 2L;


    EmployeeController employeeController;
    @Mock
    EmployeeService employeeService;

    @Mock
    BindingResult bindingResult;

    MockMvc mockMvc;




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
       ResponseEntity<EmployeeDTO> response = employeeController.createNewEmployee(employeeDTO,bindingResult);

       // Then
      assertNotNull(response.getBody());

      assertEquals(HttpStatus.CREATED,response.getStatusCode());
      then(employeeService).should().createNewEmployee(any(EmployeeDTO.class));
      then(employeeService).shouldHaveNoMoreInteractions();

    }

    @Test
    @DisplayName("test creating new employee will return ok")
    @Disabled
    void createNewProjectStatusIsOK() throws Exception {
        //        given
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(EMPLOYEE_IDENTIFIER1);
        employeeDTO.setFirstName("alamu");
        employeeDTO.setEmail("abc@gmail.com");

        given(employeeService.createNewEmployee(any(EmployeeDTO.class))).willReturn(employeeDTO);

       //        When
       // mockMvc.perform(post("/api/v1/new")
         //               .contentType(MediaType.APPLICATION_JSON)
           //             .content(Mapper.mapToJson(employeeDTO)))
             //   .andExpect(status().isCreated())
              //  .andExpect(jsonPath("$.email",equalTo("abc@gmail.com")))
               // .andExpect(jsonPath("$.firstName",equalTo("alamu")))
               // .andExpect(jsonPath("$.lastName",equalTo(null)));
    }





}
