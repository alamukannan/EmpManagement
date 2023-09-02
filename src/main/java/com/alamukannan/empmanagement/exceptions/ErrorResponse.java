package com.alamukannan.empmanagement.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String message;
    private final String appName;
    private final LocalDateTime localDateTime;
    private List<ValidationError> errors = new ArrayList<>();

    public ErrorResponse(int status, String message, String appName, LocalDateTime localDateTime) {
        this.status = status;
        this.message = message;
        this.appName = appName;
        this.localDateTime = localDateTime;
    }

    static class ValidationError {
        private final String field;
        private final String message;

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getAppName() {
        return appName;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
    }

    public void addValidationError(String field, String message) {
        errors.add(new ValidationError(field, message));
    }
}
