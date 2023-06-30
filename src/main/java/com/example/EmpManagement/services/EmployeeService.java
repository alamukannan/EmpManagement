package com.example.EmpManagement.services;

import com.example.EmpManagement.domain.Employee;
import com.example.EmpManagement.dtos.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
}
