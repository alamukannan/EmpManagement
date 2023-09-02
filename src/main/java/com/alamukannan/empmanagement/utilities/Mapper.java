package com.alamukannan.empmanagement.utilities;

import com.alamukannan.empmanagement.domain.Employee;
import com.alamukannan.empmanagement.dtos.EmployeeDTO;

public class Mapper {

    private Mapper() {
    }

    public static Employee getEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setFirstName(employeeDTO.getFirstName());
        return employee;
    }

    public static EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        return employeeDTO;
    }


}
