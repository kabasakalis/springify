
package com.kabasakalis.springifyapi.security;

import com.kabasakalis.springifyapi.models.Role;
import com.kabasakalis.springifyapi.models.SpringifyUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class JwtAuthenticatedSpringifyUser implements Authentication {

    private final SpringifyUser springifyUser;

    public JwtAuthenticatedSpringifyUser(SpringifyUser springifyUser) {
        this.springifyUser = springifyUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
                return springifyUser.getRoles().stream().
                map(Role::getName).
                map(SimpleGrantedAuthority::new).
                collect(Collectors.toSet());
    }

    @Override
    public Object getCredentials() {
        return new LoginCredentials(springifyUser.getUsername(), springifyUser.getPassword());
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return springifyUser.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return springifyUser.getUsername();
    }
}
