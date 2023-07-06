package com.alamukannan.empmanagement.controllers;

import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.services.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final Logger log = LogManager.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping(value = "/new",consumes = "application/json",produces = "application/json")
    public ResponseEntity<EmployeeDTO> createNewEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        log.info("Requested to save new Employee");
      EmployeeDTO employeeDTO1= employeeService.createNewEmployee(employeeDTO);
      return new ResponseEntity<>(employeeDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        log.info("Requested to get all the employees");
      List<EmployeeDTO> employees =  employeeService.getAllEmployees();

      return ResponseEntity.ok(employees);
    }

}
