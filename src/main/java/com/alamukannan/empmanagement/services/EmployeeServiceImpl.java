package com.alamukannan.empmanagement.services;

import com.alamukannan.empmanagement.domain.Employee;
import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.repository.EmployeeRepository;
import com.alamukannan.empmanagement.utilities.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<EmployeeDTO> getAllEmployees() {
      List<Employee> employees =  employeeRepository.findAll();
      return  employees.stream().map( Mapper::getEmployeeDTO).collect(Collectors.toList());
    }
}
