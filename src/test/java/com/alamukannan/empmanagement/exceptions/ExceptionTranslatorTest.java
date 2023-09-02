package com.alamukannan.empmanagement.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExceptionTranslatorTest {

    @Mock
    WebRequest request;
    @Mock
    Environment environment;

    @Mock
    HttpHeaders headers;
    ExceptionTranslator exceptionTranslator;



    @BeforeEach
    void init(){
        exceptionTranslator = new ExceptionTranslator(environment);
    }

    @Test
    void handleAllUncaughtException() {
        //When
        ResponseEntity<Object> response=    exceptionTranslator.handleAllUncaughtException(new Exception(),request);

        //Then
        assertNotNull(response);
        assertEquals(500,response.getStatusCodeValue());

    }

    @Test
    void handleBadRequests() {
        //Given
        Exception exception = new Exception();
        // when
        ResponseEntity<Object> response= exceptionTranslator.handleBadRequests(exception,request);
        //then
        assertNotNull(response);
        assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    void handleExceptionInternal() {
        //When

        ResponseEntity<Object> response= exceptionTranslator.handleExceptionInternal(new Exception(),new Object(),headers,HttpStatus.INTERNAL_SERVER_ERROR,request);
        //Then
        assertNotNull(response);
        assertEquals(500,response.getStatusCodeValue());
    }

}