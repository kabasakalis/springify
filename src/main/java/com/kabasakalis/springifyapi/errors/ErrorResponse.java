package com.kabasakalis.springifyapi.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;

public class ErrorResponse {

    private final String exception;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private Date timestamp;

    public ErrorResponse(String exception, int status, String error, String message, String path, Date timestamp) {
        this.exception = exception;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }


    public String getException() {
        return exception;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
