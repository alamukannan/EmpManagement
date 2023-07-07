package com.alamukannan.empmanagement.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ErrorResponseTest {


    String customeDateFormat = "dd:mm:yyyy hh:ss:mm a";

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(customeDateFormat);
    LocalDateTime localDateTime = LocalDateTime.of(2023, 1,31,10,11, 1);

    ErrorResponse errorResponse;

    @BeforeEach
  void init(){

        errorResponse = new ErrorResponse(100,"test message", "EmpManagement", localDateTime);
    }

    @Test
    void testResponseMessageAndStatus(){

        assertEquals(100,errorResponse.getStatus());
        assertEquals("test message",errorResponse.getMessage());
        assertNull(errorResponse.getErrors());
        errorResponse.addValidationError("firstName","name shouldn't ne null");
        assertEquals(1,errorResponse.getErrors().size());
        assertNotNull(errorResponse.getErrors().get(0));
        errorResponse.setErrors(errorResponse.getErrors());
        assertEquals(1,errorResponse.getErrors().size());
        ErrorResponse.ValidationError error = new ErrorResponse.ValidationError("lastName","can be null");
        assertEquals("lastName",error.getField());
        assertEquals("can be null",error.getMessage());
        assertNotNull(error.getField());
        assertNotNull(error.getMessage());
        assertNotNull(errorResponse.getErrors());
        errorResponse.setErrors(List.of(error));
        assertEquals(1,errorResponse.getErrors().size());
        assertEquals("EmpManagement",errorResponse.getAppName());
        assertNotNull(errorResponse.getAppName());
        assertNotNull(errorResponse.getLocalDateTime());
        assertEquals(2023,errorResponse.getLocalDateTime().getYear());
    }


    @Test
    void addValidationError() {
        // When
        assertNull(errorResponse.getErrors());
        errorResponse.addValidationError("new","testMes");
        // Then
        assertNotNull(errorResponse.getErrors());
        assertEquals(1,errorResponse.getErrors().size());
    }
}