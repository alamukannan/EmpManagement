package com.alamukannan.empmanagement.controllers;

import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.services.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final Logger log = LogManager.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String index() {

        log.info("Requested the index page...");

        return "index";
    }

    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createNewEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {

        log.info("Requested to save new Employee");

        if (bindingResult.hasErrors()) {
            log.error("Problem with RequestBody while creating Employee");
            Map<String, String> errorsMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        }

        EmployeeDTO employeeDTO1 = employeeService.createNewEmployee(employeeDTO);

        return new ResponseEntity<>(employeeDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        log.info("Requested to get all the employees");

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        log.debug("Requested to update employee with ID: {}", employeeDTO.getId());

        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);

        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    @ResponseBody()
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        log.info("Deleting the Employee with Id: {}", id);

        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee with ID: " + id + " has been deleted successfully", HttpStatus.OK);

    }
}
