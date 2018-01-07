package com.kabasakalis.springifyapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionHandlers extends BaseExceptionHandler {

    private static final Map<Class<Exception>, HttpStatus> DEFAULT_SPRINGIFY_EXCEPTION_RESPONSE_CODES =
            Arrays.stream(new Object[][]{
                    {EntityNotFoundException.class, NOT_FOUND},
                    {AssociationNotFoundException.class, NOT_FOUND}
            }).collect(Collectors.toMap(entry -> (Class<Exception>) entry[0], entry -> (HttpStatus) entry[1]));

    public ExceptionHandlers() { super(); }

    @Override
    protected Map<Class<Exception>, HttpStatus> getAllExceptionToResponseMappings() {
        Map<Class<Exception>, HttpStatus> all = new HashMap<>();
        all.putAll(DEFAULT_SPRING_EXCEPTION_RESPONSE_CODES);
        all.putAll(DEFAULT_SPRINGIFY_EXCEPTION_RESPONSE_CODES);
        return all;
    }

}
