package com.kabasakalis.springifyapi.exceptions;

import com.kabasakalis.springifyapi.security.LoginCredentials;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class FailedLoginException extends BaseException {


    protected static final HttpStatus CODE = UNAUTHORIZED;

    public FailedLoginException(
            LoginCredentials credentials,
            HttpStatus code,
            String message,
            Throwable cause
    ) {
        super(code, message, cause);
        this.setCustomMessage( credentials.toString());
    }


    public FailedLoginException(LoginCredentials credentials) {
        super();
        this.setCustomMessage(credentials.toString());
    }

    @Override
    public void setCustomMessage(String credentials) {
        this.customMessage = String.format(" Invalid Credentials. %s", credentials);
    }

}
