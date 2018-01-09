package com.kabasakalis.springifyapi.configuration;

public class SecurityConstants {

    public static final String AUDIENCE = "Springify Users";
    public static final String ISSUER = "com.srpingify.kabasakalis";
    public static final long EXPIRATION_TIME_FROM_NOW_HOURS = 192; // 8 days
    public static final String SIGN_UP_URL = "/users/sign-up";
    public static final String LOGIN_URL = "/login";
    public static final String[] PERMIT_ALL_PATHS = new String[]{
            LOGIN_URL,
            SIGN_UP_URL
    };
}
