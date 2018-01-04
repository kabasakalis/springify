package com.kabasakalis.springifyapi.errors;

import org.springframework.http.HttpStatus;

public class ExceptionMapping {
    private  String message;
    private  String code;
    private  HttpStatus status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }



    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
