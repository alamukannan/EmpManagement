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
        log.debug("Requested to get all the employees");
      List<EmployeeDTO> employees =  employeeService.getAllEmployees();

      return ResponseEntity.ok(employees);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee( @PathVariable Long id ,@Valid @RequestBody EmployeeDTO employeeDTO){
        log.debug("Requested to update employee with ID: {}",employeeDTO.getId());

        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id,employeeDTO);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id){

            employeeService.deleteEmployee(id);
            log.debug("Deleting the Employee with Id: {}",id);
            return new ResponseEntity<>("Employee with ID: "+id+" has been deleted successfully",HttpStatus.OK);

    }



}
