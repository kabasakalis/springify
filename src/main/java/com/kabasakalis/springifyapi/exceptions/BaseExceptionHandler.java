package com.kabasakalis.springifyapi.exceptions;

import com.kabasakalis.springifyapi.models.BaseEntity;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.InternalServerErrorException;
import java.net.BindException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;


public abstract class BaseExceptionHandler extends DefaultHandlerExceptionResolver {


    protected static final Map<Class<Exception>, HttpStatus> DEFAULT_SPRING_EXCEPTION_RESPONSE_CODES =
            Arrays.stream(new Object[][]{
                    {HttpRequestMethodNotSupportedException.class, METHOD_NOT_ALLOWED},
                    {HttpMediaTypeNotSupportedException.class, UNSUPPORTED_MEDIA_TYPE},
                    {HttpMediaTypeNotAcceptableException.class, NOT_ACCEPTABLE},
                    {MissingPathVariableException.class, INTERNAL_SERVER_ERROR},
                    {MissingServletRequestParameterException.class, BAD_REQUEST},
                    {ServletRequestBindingException.class, BAD_REQUEST},
                    {ConversionNotSupportedException.class, INTERNAL_SERVER_ERROR},
                    {TypeMismatchException.class, BAD_REQUEST},
                    {HttpMessageNotReadableException.class, BAD_REQUEST},
                    {HttpMessageNotWritableException.class, INTERNAL_SERVER_ERROR},
                    {MethodArgumentNotValidException.class, BAD_REQUEST},
                    {MissingServletRequestPartException.class, BAD_REQUEST},
                    {BindException.class, BAD_REQUEST},
                    {NoHandlerFoundException.class, NOT_FOUND},
                    {Exception.class, INTERNAL_SERVER_ERROR},
            }).collect(Collectors.toMap(entry -> (Class<Exception>) entry[0], entry -> (HttpStatus) entry[1]));

    private static final ErrorResponse GENERIC_DEFAULT_ERROR_RESPONSE =
            new ErrorResponse(
                    InternalServerErrorException.class.getSimpleName(),
                    INTERNAL_SERVER_ERROR.value(),
                    INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "Internal Server Error"
            );

    private final Map<Class, ErrorResponse> errorResponseMappings = new HashMap<>();

    public BaseExceptionHandler() {
        super();
        registerMapping(
                MissingServletRequestParameterException.class,
                new ErrorResponse(
                        MissingServletRequestParameterException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        "Bad Request Custom Message"
                ));

        registerMapping(

                MethodArgumentTypeMismatchException.class,
                new ErrorResponse(
                        MethodArgumentTypeMismatchException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        "Bad Request Custom Message"
                ));

        registerMapping(
                HttpRequestMethodNotSupportedException.class,
                new ErrorResponse(
                        HttpRequestMethodNotSupportedException.class.getSimpleName(),
                        METHOD_NOT_ALLOWED.value(),
                        METHOD_NOT_ALLOWED.getReasonPhrase(),
                        "Method Not Allowed Custom Message"
                ));

        registerMapping(
                ServletRequestBindingException.class,
                new ErrorResponse(
                        ServletRequestBindingException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        "Bad Request Custom Message"
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
        }).orElse(getDefaultExceptionResponse(exception, path, timestamp));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpResponse.setStatus(errorResponse.getStatus());
        return new ResponseEntity(errorResponse, httpHeaders, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    protected void registerMapping(
            final Class<?> _class,
            ErrorResponse customErrorResponse) {
        errorResponseMappings.put(_class, customErrorResponse);
    }

    private ErrorResponse getDefaultExceptionResponse(Throwable exception,
                                                      String path,
                                                      String timestamp) {

        HttpStatus defaultStatus = getAllExceptionToResponseMappings().getOrDefault(exception.getClass(), INTERNAL_SERVER_ERROR);

        ErrorResponse defautErrorResponse = new ErrorResponse(exception.getClass().getSimpleName(),
                defaultStatus.value(),
                defaultStatus.getReasonPhrase(),
                exception.getMessage());
        defautErrorResponse.setPath(path);
        defautErrorResponse.setTimestamp(timestamp);
        return defautErrorResponse;
    }

    protected Map<Class<Exception>, HttpStatus> getAllExceptionToResponseMappings() {
        return DEFAULT_SPRING_EXCEPTION_RESPONSE_CODES;
    }

}
