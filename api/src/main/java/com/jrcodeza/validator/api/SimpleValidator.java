package com.jrcodeza.validator.api;

import java.util.List;
import java.util.Stack;

public interface SimpleValidator {

    List<ValidationError> validate(Object objectToValidate, Stack<String> pathToRoot);

    boolean supportsAnnotation(Class<?> annotation);

}
