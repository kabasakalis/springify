package com.kabasakalis.springifyapi.errors;


//import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Calendar;

@ControllerAdvice
//@Slf4j
public class ExceptionHandlers extends BaseExceptionHandler {
    public ExceptionHandlers() {
//        super(log);
        registerMapping(
                EntityNotFoundException.class,
                new ErrorResponse(
                        EntityNotFoundException.class.getSimpleName(),
                        HttpStatus.NOT_FOUND.value(),
                        "ARTIST_NOT_FOuND",
                        "Artist Not Found",
                        null,
                        null
                ));
    }
}
