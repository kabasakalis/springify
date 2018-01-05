package com.kabasakalis.springifyapi.errors;

import com.kabasakalis.springifyapi.models.BaseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.InternalServerErrorException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;



public abstract class BaseExceptionHandler {
    private static final ErrorResponse DEFAULT_ERROR_RESPONSE =
            new ErrorResponse(
                    InternalServerErrorException.class.getSimpleName(),
                    INTERNAL_SERVER_ERROR.value(),
                    INTERNAL_SERVER_ERROR.toString(),
                    "Internal Server Error",
                    null,
                    null
            );

    private final Map<Class, ErrorResponse> errorResponseMappings = new HashMap<>();


    public BaseExceptionHandler() {
        registerMapping(
                MissingServletRequestParameterException.class,
                new ErrorResponse(
                        MissingServletRequestParameterException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        "Missing request Parameter",
                        null,
                        null
                ));

        registerMapping(

                MethodArgumentTypeMismatchException.class,
                new ErrorResponse(
                        MethodArgumentTypeMismatchException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        "Argument type mismatch",
                        null,
                        null
                ));

        registerMapping(
                HttpRequestMethodNotSupportedException.class,
                new ErrorResponse(
                        HttpRequestMethodNotSupportedException.class.getSimpleName(),
                        METHOD_NOT_ALLOWED.value(),
                        METHOD_NOT_ALLOWED.getReasonPhrase(),
                        "Argument type mismatch",
                        null,
                        null
                ));
        registerMapping(
                ServletRequestBindingException.class,
                new ErrorResponse(
                        ServletRequestBindingException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        "Missing header in request",
                        null,
                        null
                ));

    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity handleThrowable(final Throwable exception,
                                          final HttpServletRequest httpRequest,
                                          final HttpServletResponse httpResponse) {

        String timestamp = BaseEntity.DATE_FORMAT.format(new Date().getTime());
        String path = httpRequest.getRequestURI();

        Optional<ErrorResponse> customErrorResponse = Optional.ofNullable(errorResponseMappings.get(exception.getClass()));

        ErrorResponse errorResponse = customErrorResponse.map(er -> {
            er.setTimestamp(timestamp);
            er.setPath(path);
            return er;
        }).orElse(getDefaultErrorResponse(exception, httpRequest, httpResponse, timestamp, path));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpResponse.setStatus(errorResponse.getStatus());
        return new ResponseEntity(errorResponse, httpHeaders, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    protected void registerMapping(
            final Class<?> _class,
            ErrorResponse customErrorResponse) {
        errorResponseMappings.put(_class, customErrorResponse);
    }


    private ErrorResponse getDefaultErrorResponse(Throwable exception,
                                                  HttpServletRequest httpRequest,
                                                  HttpServletResponse httpResponse,
                                                  String path,
                                                  String timestamp) {
        return new ErrorResponse(exception.getClass().getSimpleName(),
                httpResponse.getStatus(),
                exception.getClass().getSimpleName(),
                exception.getMessage(),
                path,
                timestamp);

    }

}
