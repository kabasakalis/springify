package com.kabasakalis.springifyapi.security;

import com.kabasakalis.springifyapi.models.SpringifyUser;
import com.kabasakalis.springifyapi.exceptions.JwtAuthenticationException;
import com.kabasakalis.springifyapi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider() {
        this(null);
    }

    @Autowired
    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Optional<SpringifyUser> possibleSpringifyUser = jwtService.verify((String) authentication.getCredentials());
            return new JwtAuthenticatedSpringifyUser(possibleSpringifyUser.get());
        } catch (Exception e) {
            throw new JwtAuthenticationException("Failed to verify token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.equals(authentication);
    }
}
