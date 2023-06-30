package com.example.EmpManagement.services;

import com.example.EmpManagement.domain.Employee;
import com.example.EmpManagement.dtos.EmployeeDTO;
import com.example.EmpManagement.repository.EmployeeRepository;
import com.example.EmpManagement.utilities.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        Employee detachedEmployee = Mapper.getEmployee(employeeDTO);

        Employee savedEmployee = employeeRepository.save(detachedEmployee);

        return Mapper.getEmployeeDTO(savedEmployee);
    }
}
