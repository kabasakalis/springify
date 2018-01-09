package com.kabasakalis.springifyapi.security;

import java.io.Serializable;

public class LoginCredentials implements Serializable {
    private String username;
    private String password;

    public LoginCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public  LoginCredentials(){ super();}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(" Username: %s, Password: %s .", getUsername(), getPassword());
    }
}
