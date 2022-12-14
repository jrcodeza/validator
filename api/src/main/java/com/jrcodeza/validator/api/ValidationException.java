package com.jrcodeza.validator.api;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<ValidationError> validationErrors;

    public ValidationException(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public List<ValidationError> getValidationErrors() {
        return this.validationErrors;
    }
}
