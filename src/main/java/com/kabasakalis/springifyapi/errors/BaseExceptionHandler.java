package com.kabasakalis.springifyapi.errors;

import com.kabasakalis.springifyapi.errors.ErrorResponse;
import com.kabasakalis.springifyapi.errors.ExceptionMapping;
import org.springframework.http.HttpStatus;

import java.util.Calendar;


//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.InternalServerErrorException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

public abstract class BaseExceptionHandler {
    private static final ErrorResponse DEFAULT_ERROR_RESPONSE =
            new ErrorResponse(
                    InternalServerErrorException.class.getSimpleName(),
                    BAD_REQUEST.value(),
                    "INTERNAL SERVER ERROR",
                    "Internal Server Error",
                    "",
                    Calendar.getInstance().getTime()
            );

    private final Map<Class, ErrorResponse> errorResponseMappings = new HashMap<>();

    public BaseExceptionHandler() {
        registerMapping(
                MissingServletRequestParameterException.class,
                new ErrorResponse(
                        MissingServletRequestParameterException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        "MISSING_PARAMETER",
                        "Missing Parameter",
                        "",
                        Calendar.getInstance().getTime()
                ));

        registerMapping(
                MissingServletRequestParameterException.class,
                new ErrorResponse(
                        MethodArgumentTypeMismatchException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        "ARGUMENT_TYPE_MISMATCH",
                        "Argument type mismatch",
                        "",
                        Calendar.getInstance().getTime()
                ));

        registerMapping(
                MissingServletRequestParameterException.class,
                new ErrorResponse(
                        HttpRequestMethodNotSupportedException.class.getSimpleName(),
                        METHOD_NOT_ALLOWED.value(),
                        METHOD_NOT_ALLOWED.toString(),
                        "Argument type mismatch",
                        "",
                        Calendar.getInstance().getTime()
                ));
        registerMapping(
                MissingServletRequestParameterException.class,
                new ErrorResponse(
                        ServletRequestBindingException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        "MISSING_HEADER",
                        "Missing header in request",
                        "",
                        Calendar.getInstance().getTime()
                ));

    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorResponse handleThrowable(final Throwable ex, final HttpServletResponse response) {
        ErrorResponse errorResponse = errorResponseMappings.getOrDefault(ex.getClass(), DEFAULT_ERROR_RESPONSE);

        response.setStatus(errorResponse.getStatus());

//        log.error("{} ({}): {}", errorResponse.getMessage(), errorResponse.getStatus(), ex.getMessage(), ex);

//        return new ErrorResponse(mapping.code, mapping.message);
        return errorResponse;
    }

    protected void registerMapping(
            final Class<?> clazz,
            ErrorResponse errorResponse) {
        errorResponseMappings.put(clazz, errorResponse);
    }


//    protected void registerMapping(
//            final Class<?> clazz,
//            final String code,
//            final String message,
//            final HttpStatus status) {
//        errorResponseMappings.put(clazz, new ExceptionMapping(code, message, status));
//    }


}
