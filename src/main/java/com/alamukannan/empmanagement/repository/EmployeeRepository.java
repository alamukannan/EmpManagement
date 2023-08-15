package com.alamukannan.empmanagement.repository;

import com.alamukannan.empmanagement.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
     Employee findByFirstName(String firstName);
}
