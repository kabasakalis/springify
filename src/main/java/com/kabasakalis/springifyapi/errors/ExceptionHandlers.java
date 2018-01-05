package com.kabasakalis.springifyapi.errors;


//import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Calendar;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@ControllerAdvice
//@Slf4j
public class ExceptionHandlers extends BaseExceptionHandler {
    public ExceptionHandlers() {
        registerMapping(
                EntityNotFoundException.class,
                new ErrorResponse(
                        EntityNotFoundException.class.getSimpleName(),
                        NOT_FOUND.getStatusCode(),
                        NOT_FOUND.getReasonPhrase(),
                        "Entity Not Found",
                        null,
                        null
                ));
    }
}
