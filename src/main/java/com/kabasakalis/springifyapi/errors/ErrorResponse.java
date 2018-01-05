package com.kabasakalis.springifyapi.errors;

public class ErrorResponse {


    private String exceptionName;
    private int status;
    private String error;
    private String message;
    private String path;
    private String timestamp;

    public ErrorResponse(String exceptionName, int status, String error, String message) {
        this.exceptionName = exceptionName;
        this.status = status;
        this.error = error;
        this.message = message;
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
