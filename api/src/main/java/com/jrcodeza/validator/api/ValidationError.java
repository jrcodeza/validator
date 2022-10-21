package com.jrcodeza.validator.api;

public record ValidationError(String fieldPath, String message, Class<?> detectedBy) { }
