package com.example.EmpManagement.controllers;

import com.example.EmpManagement.domain.Employee;
import com.example.EmpManagement.dtos.EmployeeDTO;
import com.example.EmpManagement.services.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/")
public class EmployeeController {

    private final EmployeeService employeeService;
    private Logger log = LogManager.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping("new")
    public ResponseEntity<EmployeeDTO> createNewEmployee(EmployeeDTO employeeDTO, BindingResult bindingResult){

        log.info("Requested to save new Employee");

      EmployeeDTO employeeDTO1= employeeService.createNewEmployee(employeeDTO);
      return new ResponseEntity<>(employeeDTO1, HttpStatus.CREATED);
    }

}
