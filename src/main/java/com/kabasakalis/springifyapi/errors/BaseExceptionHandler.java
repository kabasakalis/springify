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
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.InternalServerErrorException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;


public abstract class BaseExceptionHandler extends DefaultHandlerExceptionResolver {
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
                        "Missing request Parameter"
                ));

        registerMapping(

                MethodArgumentTypeMismatchException.class,
                new ErrorResponse(
                        MethodArgumentTypeMismatchException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        "Argument type mismatch"
                ));

        registerMapping(
                HttpRequestMethodNotSupportedException.class,
                new ErrorResponse(
                        HttpRequestMethodNotSupportedException.class.getSimpleName(),
                        METHOD_NOT_ALLOWED.value(),
                        METHOD_NOT_ALLOWED.getReasonPhrase(),
                        "Method Not Allowed"
                ));
        registerMapping(
                ServletRequestBindingException.class,
                new ErrorResponse(
                        ServletRequestBindingException.class.getSimpleName(),
                        BAD_REQUEST.value(),
                        BAD_REQUEST.getReasonPhrase(),
                        "Missing header in request"
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
            er.setMessage(er.getMessage().concat(". ").concat(exception.getMessage()));
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

        ErrorResponse defautErrorResponse = new ErrorResponse(exception.getClass().getSimpleName(),
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage());
        defautErrorResponse.setPath(path);
        defautErrorResponse.setTimestamp(timestamp);
        return defautErrorResponse;

    }


}
