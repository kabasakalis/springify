package com.kabasakalis.springifyapi.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.Nullable;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.TemporalType;
import javax.validation.constraints.Null;
import java.util.Calendar;
import java.util.Date;

public class ErrorResponse {

    @Nullable
    private String exception;
    @Nullable
    private int status;
    @Nullable
    private String error;
    @Nullable
    private String message;
    @Nullable
    private String path;
    @Nullable
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

    public void setException(String exception) {
        this.exception = exception;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


}
