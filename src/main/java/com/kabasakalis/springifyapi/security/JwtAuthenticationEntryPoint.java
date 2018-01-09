package com.kabasakalis.springifyapi.security;

import org.springframework.hateoas.MediaTypes;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
            throws IOException, ServletException {
        String message= e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        httpServletResponse.setContentType(MediaTypes.HAL_JSON_VALUE);
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }
}
