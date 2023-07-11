package com.alamukannan.empmanagement.services;

import com.alamukannan.empmanagement.dtos.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);
}
