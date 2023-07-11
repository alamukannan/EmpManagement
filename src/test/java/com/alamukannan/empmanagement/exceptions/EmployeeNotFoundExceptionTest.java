package com.alamukannan.empmanagement.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeNotFoundExceptionTest {
    @Test
    void getMessage() {
//        Given
        String message = "hello";
//        When
        EmployeeNotFoundException response = new EmployeeNotFoundException(message);
//        Then
        assertEquals(message,response.getMessage());
    }
}