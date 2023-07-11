package com.alamukannan.empmanagement.services;

import com.alamukannan.empmanagement.domain.Employee;
import com.alamukannan.empmanagement.dtos.EmployeeDTO;
import com.alamukannan.empmanagement.exceptions.EmployeeNotFoundException;
import com.alamukannan.empmanagement.repository.EmployeeRepository;
import com.alamukannan.empmanagement.utilities.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        Optional<Employee> foundEmployee = employeeRepository.findById(id);
        if (foundEmployee.isPresent()){
            Employee detachedEmployee = foundEmployee.get();
            detachedEmployee.setFirstName(employeeDTO.getFirstName());
            detachedEmployee.setEmail(employeeDTO.getEmail());
            detachedEmployee.setLastName(employeeDTO.getLastName());
            detachedEmployee.setId(id);
         Employee updatedEmployee =   employeeRepository.save(detachedEmployee);
            return Mapper.getEmployeeDTO(updatedEmployee);
        }else
            throw new EmployeeNotFoundException("Employee with given Id: "+id);
    }

    @Override
    public void deleteEmployee(Long id) {
         Optional<Employee> employee = employeeRepository.findById(id);
         if (employee.isPresent()){
             employeeRepository.delete(employee.get());
         }else{
             throw new EmployeeNotFoundException("Employee with given Id: "+id);
         }
    }


}
