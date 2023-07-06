package com.alamukannan.empmanagement;

import com.alamukannan.empmanagement.controllers.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class EmpManagementApplicationTests {

	@Autowired
	EmployeeController employeeController;
	@Test
	void contextLoads() {
		assertNotNull(employeeController);
	}

}
