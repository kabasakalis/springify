package com.kabasakalis.springifyapi.configuration;

public class SecurityConstants {

    public static final String AUDIENCE = "Springify Users";
    public static final String ISSUER = "com.srpingify.kabasakalis";
    public static final long EXPIRATION_TIME_FROM_NOW_HOURS = 192; // 8 days
    public static final String SIGN_UP_URI = "/register";
    public static final String LOGIN_URI = "/login";
    public static final String USERS_URI = "/users/**";
    public static final String ROLES_URI = "/roles/**";
    public static final String[] PERMIT_ALL_PATHS = new String[]{
            LOGIN_URI,
            SIGN_UP_URI
    };
        public static final String[] ADMIN_PATHS = new String[]{
            ROLES_URI,
            USERS_URI
    };
}
