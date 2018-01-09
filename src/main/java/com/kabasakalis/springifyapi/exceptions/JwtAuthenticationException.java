package com.kabasakalis.springifyapi.exceptions;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class JwtAuthenticationException extends BaseException {



    protected static final HttpStatus CODE = UNAUTHORIZED;

    public JwtAuthenticationException(
            String token,
            HttpStatus code,
            String message,
            Throwable cause
    ) {
        super(code, message, cause);
        this.setCustomMessage(token);
    }


    public JwtAuthenticationException(String token) {
        super();
        this.setCustomMessage(token);
    }

    @Override
    public void setCustomMessage(String token) {
        this.customMessage = String.format(" Token: %s is invalid.", token);
    }
}
