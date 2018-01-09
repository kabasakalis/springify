package com.kabasakalis.springifyapi.exceptions;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class BaseException extends RuntimeException {

    protected static final HttpStatus CODE = INTERNAL_SERVER_ERROR;
    protected HttpStatus code;
    protected String customMessage = "";

    public BaseException(
            HttpStatus code,
            String message,
            Throwable cause
    ) {
        super(message, cause);
        this.setCode(code);
    }


    public BaseException(HttpStatus code) {
        super();
        this.setCode(code);
    }

    public BaseException() {
        super();
        this.setCode(code);
    }


    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String message) {
        this.customMessage = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code == null ? CODE : code;
    }

    protected String getExceptionMessage() {
        return getCause() != null ? getCause().getMessage() : getMessage();
    }

    @Override
    public String getMessage() {
        return getExceptionMessage() + customMessage;
    }

}
