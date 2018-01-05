package com.kabasakalis.springifyapi.errors;

import org.springframework.web.bind.annotation.ControllerAdvice;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@ControllerAdvice
public class ExceptionHandlers extends BaseExceptionHandler {
    public ExceptionHandlers() {
        registerMapping(
                EntityNotFoundException.class,
                new ErrorResponse(
                        EntityNotFoundException.class.getSimpleName(),
                        NOT_FOUND.getStatusCode(),
                        NOT_FOUND.getReasonPhrase(),
                        "Entity Not Found"
                ));
    }
}
