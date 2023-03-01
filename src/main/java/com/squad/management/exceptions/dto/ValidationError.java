package com.squad.management.exceptions.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    private final List<FieldError> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldError(fieldName, message));
    }
}