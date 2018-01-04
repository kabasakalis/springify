package com.kabasakalis.springifyapi.errors;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class BaseException extends RuntimeException {

    private static final HttpStatus CODE = NOT_FOUND;
    protected HttpStatus code;
    protected String customMessage;

    public  BaseException (
            HttpStatus code,
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code == null ? CODE : code ;
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
        this.code = code;
    }

}
