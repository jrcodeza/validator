package com.jrcodeza.validator.predefined;

import com.jrcodeza.validator.api.SimpleValidator;
import com.jrcodeza.validator.api.ValidationError;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class NotNullValidator implements SimpleValidator {

    @Override
    public List<ValidationError> validate(Object objectToValidate, Stack<String> pathToRoot) {
        if (objectToValidate == null) {
            return List.of(new ValidationError(pathToRoot.peek(), "Cannot be null", this.getClass()));
        }
        return Collections.emptyList();
    }

    @Override
    public boolean supportsAnnotation(Class<?> annotation) {
        return annotation.getName().equals(NotNull.class.getName());
    }
}
