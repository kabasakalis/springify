package com.kabasakalis.springifyapi.errors;

import com.sun.istack.internal.Nullable;

import java.util.Date;

public class ErrorResponse {

    @Nullable
    private String exceptionName;
    @Nullable
    private int status;
    @Nullable
    private String error;
    @Nullable
    private String message;
    @Nullable
    private String path;
    @Nullable
    private String timestamp;

    public ErrorResponse(String exceptionName, int status, String error, String message, String path, String timestamp) {
        this.exceptionName = exceptionName;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


}
